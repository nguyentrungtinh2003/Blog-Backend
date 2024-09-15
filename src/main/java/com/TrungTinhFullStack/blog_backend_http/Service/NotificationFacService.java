package com.TrungTinhFullStack.blog_backend_http.Service;

import com.TrungTinhFullStack.blog_backend_http.Factory.NotificationFac;
import com.TrungTinhFullStack.blog_backend_http.Factory.NotificationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationFacService {

    private final NotificationFactory notificationFactory;

    @Autowired
    public NotificationFacService(NotificationFactory notificationFactory) {
        this.notificationFactory = notificationFactory;
    }

    public void sendNotification(String type, String message, String recipientEmail) {
        NotificationFac notification = notificationFactory.createNotification(type, recipientEmail);
        if (notification != null) {
            notification.sendNotification(message);
        } else {
            System.out.println("Invalid notification type");
        }
    }
}
