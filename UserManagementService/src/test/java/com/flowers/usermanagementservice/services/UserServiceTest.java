package com.flowers.usermanagementservice.services;

import com.flowers.usermanagementservice.domain.User;
import com.flowers.usermanagementservice.domain.UserId;
import com.flowers.usermanagementservice.domain.daocontracts.IUserDAO;
import com.flowers.usermanagementservice.services.factories.AdminUserFactory;
import com.flowers.usermanagementservice.services.factories.ClientUserFactory;
import com.flowers.usermanagementservice.services.factories.EmployeeUserFactory;
import com.flowers.usermanagementservice.services.factories.ManagerUserFactory;
import com.flowers.usermanagementservice.services.factories.UserFactoryRegistry;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.RestOperations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    private UserFactoryRegistry userFactoryRegistry() {
        return new UserFactoryRegistry(List.of(
                new AdminUserFactory(),
                new ManagerUserFactory(),
                new EmployeeUserFactory(),
                new ClientUserFactory()
        ));
    }

    @Test
    void createUserUsesFactoryBeforePersisting() {
        IUserDAO userDAO = mock(IUserDAO.class);
        RestOperations restTemplate = mock(RestOperations.class);
        UserService service = new UserService(userDAO, restTemplate, userFactoryRegistry());
        User incoming = new User("admin@example.com", "pass", "ADMIN", null);

        when(userDAO.insert(any(User.class))).thenReturn(true);

        assertTrue(service.createUser(incoming));

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDAO).insert(captor.capture());
        assertEquals("ADMIN", captor.getValue().getType());
    }

    @Test
    void updateUserSendsEmailAndDiscordNotifications() {
        IUserDAO userDAO = mock(IUserDAO.class);
        RestOperations restTemplate = mock(RestOperations.class);
        UserService service = new UserService(userDAO, restTemplate, userFactoryRegistry());
        User user = new User(new UserId(5), "person@example.com", "pass", "CLIENT", null);

        when(userDAO.update(user)).thenReturn(true);
        when(userDAO.searchUser(5)).thenReturn(user);

        assertTrue(service.updateUser(user));

        ArgumentCaptor<Object> captor = ArgumentCaptor.forClass(Object.class);
        verify(restTemplate, times(2)).postForEntity(
                eq("http://localhost:8085/api/notifications/send"),
                captor.capture(),
                eq(String.class)
        );

        assertEquals("EMAIL", ((Map<?, ?>) captor.getAllValues().get(0)).get("type"));
        assertEquals("DISCORD", ((Map<?, ?>) captor.getAllValues().get(1)).get("type"));
    }
}
