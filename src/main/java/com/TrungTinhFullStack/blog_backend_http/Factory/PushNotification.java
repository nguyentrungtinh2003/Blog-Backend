package com.TrungTinhFullStack.blog_backend_http.Factory;

import org.springframework.stereotype.Component;

@Component
public class PushNotification implements NotificationFac{

    @Override
    public void sendNotification(String message) {
        // Giả lập gửi push notification
        System.out.println("Sent push notification: " + message);
    }
}
