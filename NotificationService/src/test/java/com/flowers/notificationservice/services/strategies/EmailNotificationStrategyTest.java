package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

class EmailNotificationStrategyTest {

    @Test
    void sendUsesRecipientEmailWhenPresent() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailNotificationStrategy strategy = new EmailNotificationStrategy(
                mailSender,
                "from@example.com",
                "fallback@example.com",
                "Subject"
        );
        Notification notification = new Notification(1, "Welcome", "EMAIL");
        notification.setRecipientEmail("to@example.com");

        assertTrue(strategy.send(notification));

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage message = captor.getValue();

        assertEquals("from@example.com", message.getFrom());
        assertEquals("to@example.com", message.getTo()[0]);
        assertEquals("Subject", message.getSubject());
        assertEquals("Welcome", message.getText());
    }

    @Test
    void sendFailsWhenNoRecipientExists() {
        JavaMailSender mailSender = mock(JavaMailSender.class);
        EmailNotificationStrategy strategy = new EmailNotificationStrategy(mailSender, "", "", "Subject");

        assertFalse(strategy.send(new Notification(1, "Welcome", "EMAIL")));
        verifyNoInteractions(mailSender);
    }
}
