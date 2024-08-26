package com.TrungTinhFullStack.blog_backend_http.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String img;

    public User() {

    }

    public User(Long id, String username, String password, String email, String img) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.img = img;
    }
}
