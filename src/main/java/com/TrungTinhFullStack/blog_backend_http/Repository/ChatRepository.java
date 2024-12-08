package com.TrungTinhFullStack.blog_backend_http.Repository;

import com.TrungTinhFullStack.blog_backend_http.Entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat,Long> {
}
