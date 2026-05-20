package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.UserId;

public abstract class UserFactory {

    public abstract String getType();

    public final User createUser(User source) {
        User created = createConcreteUser(source);

        if (source.getId() != null) {
            created.setId(new UserId(source.getId().getUserId()));
        }
        return created;
    }

    protected abstract User createConcreteUser(User source);
}
