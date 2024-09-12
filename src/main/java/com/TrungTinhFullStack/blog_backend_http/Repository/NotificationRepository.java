package com.TrungTinhFullStack.blog_backend_http.Repository;

import com.TrungTinhFullStack.blog_backend_http.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Cần import List

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
    String deleteByUserId(Long userId);
}
