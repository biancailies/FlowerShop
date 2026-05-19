package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class DiscordNotificationStrategyTest {

    @Test
    void sendDelegatesToDiscordClientWithConfiguredChannel() {
        DiscordClient discordClient = mock(DiscordClient.class);
        DiscordNotificationStrategy strategy = new DiscordNotificationStrategy(
                discordClient,
                "bot-token",
                "channel-1"
        );

        assertTrue(strategy.send(new Notification(1, "Discord message", "DISCORD")));

        verify(discordClient).sendMessage("bot-token", "channel-1", "Discord message");
    }

    @Test
    void sendFailsWhenTokenIsMissing() {
        DiscordClient discordClient = mock(DiscordClient.class);
        DiscordNotificationStrategy strategy = new DiscordNotificationStrategy(discordClient, "", "channel-1");

        assertFalse(strategy.send(new Notification(1, "Discord message", "DISCORD")));
        verifyNoInteractions(discordClient);
    }
}
