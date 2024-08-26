package com.TrungTinhFullStack.blog_backend_http.Repository;

import com.TrungTinhFullStack.blog_backend_http.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByPostId(Long postId);
    List<Comment> findByPostedBy_Id(Long userId);
}
