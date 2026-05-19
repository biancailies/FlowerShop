package com.flowers.notificationservice.infrastructure.tableentities;

import com.flowers.notificationservice.domain.Notification;
import com.flowers.notificationservice.domain.NotificationId;
import jakarta.persistence.*;

@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "recipient_email", length = 255)
    private String recipientEmail;

    @Column(name = "recipient_discord_channel_id", length = 100)
    private String recipientDiscordChannelId;

    public NotificationEntity() {
    }

    public NotificationEntity(Notification domainNotification) {
        if (domainNotification.getId() != null && domainNotification.getId().getNotificationId() > 0) {
            this.id = domainNotification.getId().getNotificationId();
        } else {
            this.id = null;
        }
        this.userId = domainNotification.getUserId();
        this.message = domainNotification.getMessage();
        this.type = domainNotification.getType();
        this.recipientEmail = domainNotification.getRecipientEmail();
        this.recipientDiscordChannelId = domainNotification.getRecipientDiscordChannelId();
    }

    public Notification toNotification() {
        Notification notification = new Notification(
                new NotificationId(this.id),
                this.userId,
                this.message,
                this.type
        );
        notification.setRecipientEmail(this.recipientEmail);
        notification.setRecipientDiscordChannelId(this.recipientDiscordChannelId);
        return notification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getRecipientDiscordChannelId() {
        return recipientDiscordChannelId;
    }

    public void setRecipientDiscordChannelId(String recipientDiscordChannelId) {
        this.recipientDiscordChannelId = recipientDiscordChannelId;
    }
}
