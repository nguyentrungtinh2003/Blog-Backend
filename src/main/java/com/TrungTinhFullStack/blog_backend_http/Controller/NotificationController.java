package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Entity.Notification;
import com.TrungTinhFullStack.blog_backend_http.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    @PostMapping("/{id}/mark-read")
    public void markAsRead(@PathVariable Long id) {
        Notification notification = notificationRepository.findById(id).orElse(null);
        if (notification != null) {
            notification.setRead(true);
            notificationRepository.save(notification);
        }
    }
}
