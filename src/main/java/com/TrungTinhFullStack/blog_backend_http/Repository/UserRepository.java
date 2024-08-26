package com.TrungTinhFullStack.blog_backend_http.Repository;

import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String hashedPassword);
}
