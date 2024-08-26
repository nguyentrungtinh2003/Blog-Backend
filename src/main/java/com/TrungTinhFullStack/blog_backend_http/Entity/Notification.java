package com.TrungTinhFullStack.blog_backend_http.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Người dùng nhận được thông báo

    private boolean isRead; // Trạng thái đã đọc hay chưa
}
