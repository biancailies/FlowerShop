package com.flowers.notificationservice.services;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.daocontracts.INotificationDAO;
import com.flowers.notificationservice.services.strategies.NotificationStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class NotificationServiceTest {

    @Test
    void sendNotificationUsesMatchingStrategyAndPersistsAfterSuccess() {
        INotificationDAO notificationDAO = mock(INotificationDAO.class);
        NotificationStrategy emailStrategy = new FixedResultStrategy("EMAIL", true);
        NotificationService service = new NotificationService(notificationDAO, List.of(emailStrategy));
        Notification notification = new Notification(7, "Hello", "EMAIL");

        when(notificationDAO.insert(notification)).thenReturn(true);

        assertTrue(service.sendNotification(notification));
        verify(notificationDAO).insert(notification);
    }

    @Test
    void sendNotificationRejectsUnknownTypeWithoutPersisting() {
        INotificationDAO notificationDAO = mock(INotificationDAO.class);
        NotificationService service = new NotificationService(notificationDAO, List.of(new FixedResultStrategy("EMAIL", true)));
        Notification notification = new Notification(7, "Hello", "UNKNOWN");

        assertFalse(service.sendNotification(notification));
        verify(notificationDAO, never()).insert(notification);
    }

    @Test
    void sendNotificationDoesNotPersistWhenStrategyFails() {
        INotificationDAO notificationDAO = mock(INotificationDAO.class);
        NotificationService service = new NotificationService(notificationDAO, List.of(new FixedResultStrategy("DISCORD", false)));
        Notification notification = new Notification(7, "Hello", "DISCORD");

        assertFalse(service.sendNotification(notification));
        verify(notificationDAO, never()).insert(notification);
    }

    private static class FixedResultStrategy implements NotificationStrategy {
        private final String type;
        private final boolean result;

        private FixedResultStrategy(String type, boolean result) {
            this.type = type;
            this.result = result;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public boolean send(Notification notification) {
            return result;
        }
    }
}
