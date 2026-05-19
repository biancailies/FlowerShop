package com.flowers.notificationservice.domain;

public class NotificationId {

    private int notificationId;

    public NotificationId() {
    }

    public NotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationId that = (NotificationId) o;
        return notificationId == that.notificationId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(notificationId);
    }

    @Override
    public String toString() {
        return "NotificationId{" + "notificationId=" + notificationId + '}';
    }
}
