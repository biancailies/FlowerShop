package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Admin;
import com.flowers.usermanagementservice.domain.Client;
import com.flowers.usermanagementservice.domain.Employee;
import com.flowers.usermanagementservice.domain.Manager;
import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.UserId;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    public User createUser(User source) {
        String type = source.getType() == null ? "CLIENT" : source.getType().toUpperCase();
        User created = switch (type) {
            case "ADMIN" -> new Admin(source.getUsername(), source.getPassword());
            case "MANAGER" -> new Manager(source.getUsername(), source.getPassword(), source.getFlowerShopId());
            case "EMPLOYEE" -> new Employee(source.getUsername(), source.getPassword(), source.getFlowerShopId());
            case "CLIENT" -> new Client(source.getUsername(), source.getPassword());
            default -> throw new IllegalArgumentException("Unknown user type: " + source.getType());
        };

        if (source.getId() != null) {
            created.setId(new UserId(source.getId().getUserId()));
        }
        return created;
    }
}
