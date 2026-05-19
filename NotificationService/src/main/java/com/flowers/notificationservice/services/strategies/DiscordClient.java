package com.flowers.notificationservice.services.strategies;

public interface DiscordClient {

    void sendMessage(String botToken, String channelId, String message);
}
