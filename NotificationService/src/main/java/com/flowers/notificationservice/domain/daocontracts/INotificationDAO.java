package com.flowers.notificationservice.domain.daocontracts;

import com.flowers.notificationservice.domain.Notification;

import java.util.List;

public interface INotificationDAO {

    List<Notification> notificationsList();

    List<Notification> notificationsByUser(int userId);

    Notification searchNotification(int id);

    boolean insert(Notification notification);

    boolean update(Notification notification);

    boolean delete(int id);
}
