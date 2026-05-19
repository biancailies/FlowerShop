package com.flowers.usermanagementservice.services;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.daocontracts.IUserDAO;
import com.flowers.usermanagementservice.services.dto.LoginResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
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
        return userDAO.update(user);
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
