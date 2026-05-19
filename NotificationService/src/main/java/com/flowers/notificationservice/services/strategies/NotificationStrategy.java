package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;

public interface NotificationStrategy {

    boolean send(Notification notification);
}
