package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Admin;
import com.flowers.usermanagementservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public class AdminUserFactory extends UserFactory {

    @Override
    public String getType() {
        return "ADMIN";
    }

    @Override
    protected User createConcreteUser(User source) {
        return new Admin(source.getUsername(), source.getPassword());
    }
}
