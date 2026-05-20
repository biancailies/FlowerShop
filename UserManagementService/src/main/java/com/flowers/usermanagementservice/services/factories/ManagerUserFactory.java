package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Manager;
import com.flowers.usermanagementservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ManagerUserFactory extends UserFactory {

    @Override
    public String getType() {
        return "MANAGER";
    }

    @Override
    protected User createConcreteUser(User source) {
        return new Manager(source.getUsername(), source.getPassword(), source.getFlowerShopId());
    }
}
