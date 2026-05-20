package com.flowers.notificationservice.controller;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.NotificationId;
import com.flowers.notificationservice.services.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> list = notificationService.getNotifications();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable int id) {
        Notification notification = notificationService.getNotificationById(id);
        if (notification == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotificationsByUser(@PathVariable int userId) {
        List<Notification> list = notificationService.getNotificationsByUser(userId);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Notification notification) {
        if (notification.getId() == null) {
            notification.setId(new NotificationId(0));
        }
        boolean result = notificationService.createNotification(notification);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Notification created successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create notification.");
    }

    @PutMapping
    public ResponseEntity<String> updateNotification(@RequestBody Notification notification) {
        if (notification.getId() == null) {
            return ResponseEntity.badRequest().body("Notification ID is required for update.");
        }
        boolean result = notificationService.updateNotification(notification);
        if (result) {
            return ResponseEntity.ok("Notification updated successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found or update failed.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable int id) {
        boolean result = notificationService.deleteNotification(id);
        if (result) {
            return ResponseEntity.ok("Notification deleted successfully.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notification not found or delete failed.");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification) {
        if (notification.getId() == null) {
            notification.setId(new NotificationId(0));
        }
        boolean result = notificationService.sendNotification(notification);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Notification sent and saved successfully.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to send notification.");
    }
}
