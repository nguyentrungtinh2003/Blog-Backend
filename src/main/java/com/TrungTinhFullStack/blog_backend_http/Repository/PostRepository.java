package com.TrungTinhFullStack.blog_backend_http.Repository;

import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByName(String name);

    @Query(value = "SELECT * FROM post ORDER BY id DESC LIMIT 3", nativeQuery = true)
    List<Post> findLast3Posts();

    @Query("SELECT p FROM Post p WHERE p.name LIKE %:name%")
    List<Post> findByNameContaining(@Param("name") String name);

    List<Post> findByPostedById(Long userId);
}
