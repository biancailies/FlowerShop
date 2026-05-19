package com.flowers.notificationservice.services;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.daocontracts.INotificationDAO;
import com.flowers.notificationservice.services.strategies.EmailNotificationStrategy;
import com.flowers.notificationservice.services.strategies.NotificationStrategy;
import com.flowers.notificationservice.services.strategies.SmsNotificationStrategy;
import com.flowers.notificationservice.services.strategies.WhatsappNotificationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final INotificationDAO notificationDAO;
    
    private final EmailNotificationStrategy emailStrategy;
    private final SmsNotificationStrategy smsStrategy;
    private final WhatsappNotificationStrategy whatsappStrategy;

    public NotificationService(INotificationDAO notificationDAO,
                               EmailNotificationStrategy emailStrategy,
                               SmsNotificationStrategy smsStrategy,
                               WhatsappNotificationStrategy whatsappStrategy) {
        this.notificationDAO = notificationDAO;
        this.emailStrategy = emailStrategy;
        this.smsStrategy = smsStrategy;
        this.whatsappStrategy = whatsappStrategy;
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
        NotificationStrategy strategy = null;

        if (notification.getType() != null) {
            switch (notification.getType().toUpperCase()) {
                case "EMAIL":
                    strategy = emailStrategy;
                    break;
                case "SMS":
                    strategy = smsStrategy;
                    break;
                case "WHATSAPP":
                    strategy = whatsappStrategy;
                    break;
                default:
                    System.out.println("Unknown notification type: " + notification.getType());
                    return false;
            }
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
