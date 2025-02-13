package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Entity.Chat;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import com.TrungTinhFullStack.blog_backend_http.Repository.ChatRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserService userService;

    @MessageMapping("/sendMessage")
   @SendTo("/topic/messages")
    public Chat sendMessage(Chat chat) {
        if(chat.getUser().getId() == null) {
            throw new RuntimeException("User not found");
        }
        User user = userService.getUserById(chat.getUser().getId());
        chat.setUser(user);
        chat.setTimestamp(java.time.LocalDateTime.now());
        return chatRepository.save(chat); // Lưu tin nhắn vào DB
    }

    // REST API: Lấy lịch sử tin nhắn
    @GetMapping
    public List<Chat> getMessages() {
        return chatRepository.findAll();
    }
}
