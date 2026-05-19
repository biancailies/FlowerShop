package com.flowers.usermanagementservice.domain.daocontracts;

import com.flowers.usermanagementservice.domain.User;

import java.util.List;

public interface IUserDAO {

    List<User> usersList();

    List<User> usersByType(String type);

    User searchUser(int id);

    User searchUserByUsername(String username);

    boolean insert(User user);

    boolean update(User user);

    boolean delete(int id);
}
