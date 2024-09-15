package com.TrungTinhFullStack.blog_backend_http.Factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements NotificationFac{

    private final JavaMailSender mailSender;
    private String recipientEmail;

    @Autowired
    public EmailNotification(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void sendNotification(String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("New Notification");
        mailMessage.setText(message);
        mailSender.send(mailMessage);
        System.out.println("Sent email to: " + recipientEmail);
    }
}
