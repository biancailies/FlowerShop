package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    @Override
    public boolean send(Notification notification) {
        System.out.println("EMAIL notification sent to user " + notification.getUserId() + ": " + notification.getMessage());
        return true;
    }
}
