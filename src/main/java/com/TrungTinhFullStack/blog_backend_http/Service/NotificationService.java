package com.TrungTinhFullStack.blog_backend_http.Service;

import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.observer.Observable;
import org.springframework.stereotype.Service;

public interface NotificationService {
    void notifyUserAndSave(String message, User user);
    void notifyUsersAboutNewPost(String postName, String author, Iterable<User> users);

}
