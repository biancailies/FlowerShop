package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Admin;
import com.flowers.usermanagementservice.domain.Client;
import com.flowers.usermanagementservice.domain.Employee;
import com.flowers.usermanagementservice.domain.Manager;
import com.flowers.usermanagementservice.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UserFactoryTest {

    private final UserFactory factory = new UserFactory();

    @Test
    void createsConcreteUsersFromType() {
        assertInstanceOf(Admin.class, factory.createUser(new User("admin", "pass", "ADMIN", null)));
        assertInstanceOf(Manager.class, factory.createUser(new User("manager", "pass", "MANAGER", 3)));
        assertInstanceOf(Employee.class, factory.createUser(new User("employee", "pass", "EMPLOYEE", 3)));
        assertInstanceOf(Client.class, factory.createUser(new User("client", "pass", "CLIENT", null)));
    }

    @Test
    void defaultsMissingTypeToClient() {
        User user = factory.createUser(new User("client", "pass", null, null));

        assertInstanceOf(Client.class, user);
        assertEquals("CLIENT", user.getType());
    }
}
