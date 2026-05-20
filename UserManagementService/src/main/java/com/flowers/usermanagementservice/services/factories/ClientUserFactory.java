package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Client;
import com.flowers.usermanagementservice.domain.User;
import org.springframework.stereotype.Component;

@Component
public class ClientUserFactory extends UserFactory {

    @Override
    public String getType() {
        return "CLIENT";
    }

    @Override
    protected User createConcreteUser(User source) {
        return new Client(source.getUsername(), source.getPassword());
    }
}
