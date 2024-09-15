package com.TrungTinhFullStack.blog_backend_http.Factory;

import org.springframework.stereotype.Component;

@Component
public class SMSNotification implements NotificationFac{

    @Override
    public void sendNotification(String message) {
        // Thay vì tích hợp với dịch vụ SMS trả phí, chúng ta chỉ ghi log giả lập
        System.out.println("Sent SMS: " + message);
    }
}
