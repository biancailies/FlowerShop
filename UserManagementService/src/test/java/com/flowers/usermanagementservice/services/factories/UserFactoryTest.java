package com.flowers.usermanagementservice.services.factories;

import com.flowers.usermanagementservice.domain.Admin;
import com.flowers.usermanagementservice.domain.Client;
import com.flowers.usermanagementservice.domain.Employee;
import com.flowers.usermanagementservice.domain.Manager;
import com.flowers.usermanagementservice.domain.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class UserFactoryTest {

    private final UserFactoryRegistry registry = new UserFactoryRegistry(List.of(
            new AdminUserFactory(),
            new ManagerUserFactory(),
            new EmployeeUserFactory(),
            new ClientUserFactory()
    ));

    @Test
    void createsConcreteUsersFromType() {
        assertInstanceOf(Admin.class, registry.createUser(new User("admin", "pass", "ADMIN", null)));
        assertInstanceOf(Manager.class, registry.createUser(new User("manager", "pass", "MANAGER", 3)));
        assertInstanceOf(Employee.class, registry.createUser(new User("employee", "pass", "EMPLOYEE", 3)));
        assertInstanceOf(Client.class, registry.createUser(new User("client", "pass", "CLIENT", null)));
    }

    @Test
    void defaultsMissingTypeToClient() {
        User user = registry.createUser(new User("client", "pass", null, null));

        assertInstanceOf(Client.class, user);
        assertEquals("CLIENT", user.getType());
    }

    @Test
    void concreteFactoriesCreateTheirOwnUserTypes() {
        assertInstanceOf(Admin.class, new AdminUserFactory().createUser(new User("admin", "pass", "ADMIN", null)));
        assertInstanceOf(Manager.class, new ManagerUserFactory().createUser(new User("manager", "pass", "MANAGER", 3)));
        assertInstanceOf(Employee.class, new EmployeeUserFactory().createUser(new User("employee", "pass", "EMPLOYEE", 3)));
        assertInstanceOf(Client.class, new ClientUserFactory().createUser(new User("client", "pass", "CLIENT", null)));
    }
}
