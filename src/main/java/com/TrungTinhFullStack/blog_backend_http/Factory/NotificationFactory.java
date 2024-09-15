package com.TrungTinhFullStack.blog_backend_http.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailNotification emailNotification;

    public NotificationFac createNotification(String type, String recipientEmail) {
        if ("EMAIL".equalsIgnoreCase(type)) {
            emailNotification.setRecipientEmail(recipientEmail);
            return emailNotification;
        } else if ("SMS".equalsIgnoreCase(type)) {
            return new SMSNotification();
        } else if ("PUSH".equalsIgnoreCase(type)) {
            return new PushNotification();
        }
        return null;
    }
}
