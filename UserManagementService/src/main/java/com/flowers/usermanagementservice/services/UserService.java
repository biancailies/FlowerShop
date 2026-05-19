package com.flowers.usermanagementservice.services;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.daocontracts.IUserDAO;
import com.flowers.usermanagementservice.services.dto.LoginResponse;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    private final IUserDAO userDAO;
    private final RestTemplate restTemplate;
    private final String NOTIFICATION_URL = "http://localhost:8085/api/notifications/send";

    public UserService(IUserDAO userDAO, RestTemplate restTemplate) {
        this.userDAO = userDAO;
        this.restTemplate = restTemplate;
    }

    public List<User> getUsers() {
        return userDAO.usersList();
    }

    public List<User> getUsersByType(String type) {
        return userDAO.usersByType(type);
    }

    public User getUserById(int id) {
        return userDAO.searchUser(id);
    }

    public User getUserByUsername(String username) {
        return userDAO.searchUserByUsername(username);
    }

    public boolean createUser(User user) {
        return userDAO.insert(user);
    }

    public boolean updateUser(User user) {
        boolean result = userDAO.update(user);
        if (result) {
            sendNotification(user.getId().getUserId(), "EMAIL");
            sendNotification(user.getId().getUserId(), "SMS");
        }
        return result;
    }

    private void sendNotification(int userId, String type) {
        try {
            Map<String, Object> request = new HashMap<>();
            request.put("userId", userId);
            request.put("message", "Authentication information was updated for your BloomChain account.");
            request.put("type", type);
            restTemplate.postForEntity(NOTIFICATION_URL, request, String.class);
        } catch (Exception e) {
            System.err.println("Failed to send " + type + " notification: " + e.getMessage());
        }
    }

    public boolean deleteUser(int id) {
        return userDAO.delete(id);
    }

    /**
     * Login simplu: caută user după username și compară parola.
     * Nu folosește Spring Security / JWT — păstrăm proiectul simplu, ca în lab.
     */
    public LoginResponse login(String username, String password) {
        User user = userDAO.searchUserByUsername(username);

        if (user == null) {
            return new LoginResponse(false, "User not found.");
        }

        if (!user.getPassword().equals(password)) {
            return new LoginResponse(false, "Invalid password.");
        }

        return new LoginResponse(
                user.getId().getUserId(),
                user.getUsername(),
                user.getType(),
                user.getFlowerShopId(),
                true,
                "Login successful."
        );
    }
}
