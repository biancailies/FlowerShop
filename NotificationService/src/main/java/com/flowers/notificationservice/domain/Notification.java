package com.flowers.notificationservice.domain;

public class Notification {

    private NotificationId id;
    private int userId;
    private String message;
    private String type;
    private String recipientEmail;
    private String recipientDiscordChannelId;

    public Notification() {
    }

    public Notification(NotificationId id, int userId, String message, String type) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.type = type;
    }

    public Notification(int userId, String message, String type) {
        this.id = new NotificationId(0);
        this.userId = userId;
        this.message = message;
        this.type = type;
    }

    public NotificationId getId() {
        return id;
    }

    public void setId(NotificationId id) {
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

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                ", recipientEmail='" + recipientEmail + '\'' +
                ", recipientDiscordChannelId='" + recipientDiscordChannelId + '\'' +
                '}';
    }
}
