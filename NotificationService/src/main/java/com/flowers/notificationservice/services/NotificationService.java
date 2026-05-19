package com.flowers.notificationservice.services;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.daocontracts.INotificationDAO;
import com.flowers.notificationservice.services.strategies.NotificationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final INotificationDAO notificationDAO;
    private final Map<String, NotificationStrategy> strategies;

    public NotificationService(INotificationDAO notificationDAO,
                               List<NotificationStrategy> strategies) {
        this.notificationDAO = notificationDAO;
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getType().toUpperCase(),
                        Function.identity()
                ));
    }

    public List<Notification> getNotifications() {
        return notificationDAO.notificationsList();
    }

    public List<Notification> getNotificationsByUser(int userId) {
        return notificationDAO.notificationsByUser(userId);
    }

    public Notification getNotificationById(int id) {
        return notificationDAO.searchNotification(id);
    }

    public boolean createNotification(Notification notification) {
        return notificationDAO.insert(notification);
    }

    public boolean updateNotification(Notification notification) {
        return notificationDAO.update(notification);
    }

    public boolean deleteNotification(int id) {
        return notificationDAO.delete(id);
    }

    /**
     * Trimite notificarea folosind șablonul Strategy în funcție de type,
     * apoi salvează notificarea în baza de date.
     */
    public boolean sendNotification(Notification notification) {
        if (notification.getType() == null) {
            System.out.println("Notification type is required.");
            return false;
        }

        NotificationStrategy strategy = strategies.get(notification.getType().toUpperCase());
        if (strategy == null) {
            System.out.println("Unknown notification type: " + notification.getType());
            return false;
        }

        if (strategy != null) {
            boolean sent = strategy.send(notification);
            if (sent) {
                // După trimitere cu succes, salvăm în baza de date
                return notificationDAO.insert(notification);
            }
        }

        return false;
    }
}
