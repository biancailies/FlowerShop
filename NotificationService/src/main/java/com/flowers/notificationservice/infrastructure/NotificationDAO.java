package com.flowers.notificationservice.infrastructure;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.daocontracts.INotificationDAO;
import com.flowers.notificationservice.infrastructure.repositories.NotificationJpaRepository;
import com.flowers.notificationservice.infrastructure.tableentities.NotificationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NotificationDAO implements INotificationDAO {

    private final NotificationJpaRepository notificationJpaRepository;

    public NotificationDAO(NotificationJpaRepository notificationJpaRepository) {
        this.notificationJpaRepository = notificationJpaRepository;
    }

    @Override
    public List<Notification> notificationsList() {
        return notificationJpaRepository.findAll()
                .stream()
                .map(NotificationEntity::toNotification)
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> notificationsByUser(int userId) {
        return notificationJpaRepository.findByUserId(userId)
                .stream()
                .map(NotificationEntity::toNotification)
                .collect(Collectors.toList());
    }

    @Override
    public Notification searchNotification(int id) {
        Optional<NotificationEntity> entity = notificationJpaRepository.findById(id);
        return entity.map(NotificationEntity::toNotification).orElse(null);
    }

    @Override
    public boolean insert(Notification notification) {
        try {
            NotificationEntity entity = new NotificationEntity(notification);
            notificationJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Notification notification) {
        try {
            if (notification.getId() == null || !notificationJpaRepository.existsById(notification.getId().getNotificationId())) {
                return false;
            }
            NotificationEntity entity = new NotificationEntity(notification);
            notificationJpaRepository.save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            if (!notificationJpaRepository.existsById(id)) {
                return false;
            }
            notificationJpaRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
