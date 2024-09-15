package com.TrungTinhFullStack.blog_backend_http.Entity;

import com.TrungTinhFullStack.blog_backend_http.Service.Jwt.UserDetailsService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String email;

    private String img;

    // Trường để lưu OTP và thời gian hết hạn OTP
    private String otp;
    private LocalDateTime otpExpiry;

    @Column(nullable = false)
    private boolean enabled;

    public User() {

    }

    public User(Long id, String username, String password, String email, String img) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.img = img;
    }
    public User(Long id, String email, String img, String password, String username, boolean enabled) {
        this.id = id;
        this.email = email;
        this.img = img;
        this.password = password;
        this.username = username;
        this.enabled = enabled;
    }

    public User(Long id, String username, String password, String email, String img, String otp, LocalDateTime otpExpiry, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.img = img;
        this.otp = otp;
        this.otpExpiry = otpExpiry;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // Trả về danh sách trống nếu không có phân vai trò
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
