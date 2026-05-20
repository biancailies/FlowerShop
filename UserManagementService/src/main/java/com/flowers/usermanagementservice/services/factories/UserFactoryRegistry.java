package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserFactoryRegistry {

    private final Map<String, UserFactory> factories;

    public UserFactoryRegistry(List<UserFactory> factories) {
        this.factories = factories.stream()
                .collect(Collectors.toMap(
                        factory -> factory.getType().toUpperCase(),
                        Function.identity()
                ));
    }

    public User createUser(User source) {
        String type = source.getType() == null ? "CLIENT" : source.getType().toUpperCase();
        UserFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unknown user type: " + source.getType());
        }
        return factory.createUser(source);
    }
}
