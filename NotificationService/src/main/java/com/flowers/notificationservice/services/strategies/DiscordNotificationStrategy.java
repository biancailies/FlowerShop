package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

@Component
public class DiscordNotificationStrategy implements NotificationStrategy {

    private final DiscordClient discordClient;
    private final String botToken;
    private final String defaultChannelId;

    public DiscordNotificationStrategy(
            DiscordClient discordClient,
            @Value("${notification.discord.bot-token:}") String botToken,
            @Value("${notification.discord.default-channel-id:}") String defaultChannelId) {
        this.discordClient = discordClient;
        this.botToken = botToken;
        this.defaultChannelId = defaultChannelId;
    }

    @Override
    public String getType() {
        return "DISCORD";
    }

    @Override
    public boolean send(Notification notification) {
        if (!StringUtils.hasText(botToken)) {
            System.err.println("Discord notification skipped: DISCORD_BOT_TOKEN is not configured.");
            return false;
        }

        String channelId = resolveChannelId(notification);
        if (!StringUtils.hasText(channelId)) {
            System.err.println("Discord notification skipped: no Discord channel ID configured.");
            return false;
        }

        try {
            discordClient.sendMessage(botToken, channelId, notification.getMessage());
            return true;
        } catch (RestClientException e) {
            System.err.println("Failed to send Discord notification: " + e.getMessage());
            return false;
        }
    }

    private String resolveChannelId(Notification notification) {
        if (StringUtils.hasText(notification.getRecipientDiscordChannelId())) {
            return notification.getRecipientDiscordChannelId();
        }
        return defaultChannelId;
    }
}
