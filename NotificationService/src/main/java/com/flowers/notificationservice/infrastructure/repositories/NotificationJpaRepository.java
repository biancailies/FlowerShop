package com.flowers.notificationservice.infrastructure.repositories;

import com.flowers.notificationservice.infrastructure.tableentities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Integer> {

    List<NotificationEntity> findByUserId(Integer userId);

    List<NotificationEntity> findByTypeIgnoreCase(String type);
}
