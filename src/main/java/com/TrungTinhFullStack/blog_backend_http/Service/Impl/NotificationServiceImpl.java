package com.TrungTinhFullStack.blog_backend_http.Service.Impl;

import com.TrungTinhFullStack.blog_backend_http.Entity.Notification;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Repository.NotificationRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.NotificationService;
import com.TrungTinhFullStack.blog_backend_http.observer.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;



    // Phương thức thông báo cho tất cả người dùng về bài viết mới
    public void notifyUsersAboutNewPost(String postName, String author, Iterable<User> users) {
        String message = "Người dùng " + author + " đã tạo mới bài viết tên '" + postName + "'";

        // Duyệt qua từng người dùng và lưu thông báo
        for (User user : users) {
            notifyUserAndSave(message, user);
        }
    }

    @Override
    public void notifyUserAndSave(String message, User user) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(new Date());
        notification.setUser(user);
        notification.setRead(false); // Mặc định chưa đọc

        // Lưu thông báo vào database
        notificationRepository.save(notification);
    }
}
