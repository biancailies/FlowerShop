package com.flowers.notificationservice.services.strategies;

import com.flowers.notificationservice.domain.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {

    private final JavaMailSender mailSender;
    private final String fromAddress;
    private final String defaultRecipient;
    private final String subject;

    public EmailNotificationStrategy(
            JavaMailSender mailSender,
            @Value("${notification.mail.from:}") String fromAddress,
            @Value("${notification.mail.default-recipient:}") String defaultRecipient,
            @Value("${notification.mail.subject:BloomChain notification}") String subject) {
        this.mailSender = mailSender;
        this.fromAddress = fromAddress;
        this.defaultRecipient = defaultRecipient;
        this.subject = subject;
    }

    @Override
    public String getType() {
        return "EMAIL";
    }

    @Override
    public boolean send(Notification notification) {
        String recipient = resolveRecipient(notification);
        if (!StringUtils.hasText(recipient)) {
            System.err.println("Email notification skipped: no recipient email configured for user " + notification.getUserId());
            return false;
        }

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            if (StringUtils.hasText(fromAddress)) {
                mailMessage.setFrom(fromAddress);
            }
            mailMessage.setTo(recipient);
            mailMessage.setSubject(subject);
            mailMessage.setText(notification.getMessage());

            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to send email notification to " + recipient + ": " + e.getMessage());
            return false;
        }
    }

    private String resolveRecipient(Notification notification) {
        if (StringUtils.hasText(notification.getRecipientEmail())) {
            return notification.getRecipientEmail();
        }
        return defaultRecipient;
    }
}
