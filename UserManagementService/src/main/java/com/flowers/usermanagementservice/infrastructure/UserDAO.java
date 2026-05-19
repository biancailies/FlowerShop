package com.flowers.usermanagementservice.infrastructure;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.daocontracts.IUserDAO;
import com.flowers.usermanagementservice.infrastructure.repositories.UserJpaRepository;
import com.flowers.usermanagementservice.infrastructure.tableentities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDAO implements IUserDAO {

    private final UserJpaRepository userJpaRepository;

    public UserDAO(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public List<User> usersList() {
        return userJpaRepository.findAll()
                .stream()
                .map(UserEntity::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> usersByType(String type) {
        return userJpaRepository.findByTypeIgnoreCase(type)
                .stream()
                .map(UserEntity::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public User searchUser(int id) {
        Optional<UserEntity> entity = userJpaRepository.findById(id);
        return entity.map(UserEntity::toUser).orElse(null);
    }

    @Override
    public User searchUserByUsername(String username) {
        Optional<UserEntity> entity = userJpaRepository.findByUsername(username);
        return entity.map(UserEntity::toUser).orElse(null);
    }

    @Override
    public boolean insert(User user) {
        try {
            UserEntity entity = new UserEntity(user);
            userJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            if (user.getId() == null || !userJpaRepository.existsById(user.getId().getUserId())) {
                return false;
            }
            UserEntity entity = new UserEntity(user);
            userJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!userJpaRepository.existsById(id)) {
                return false;
            }
            userJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
