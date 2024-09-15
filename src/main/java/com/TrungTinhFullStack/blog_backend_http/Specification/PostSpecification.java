package com.TrungTinhFullStack.blog_backend_http.Specification;

import com.TrungTinhFullStack.blog_backend_http.Entity.Post;
import com.TrungTinhFullStack.blog_backend_http.Entity.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate; // Correct import for JPA Predicate
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {

    public static Specification<Post> filterByCriteria(String author, String title, String content) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Join with the "User" entity to filter by "username"
            if (author != null && !author.isEmpty()) {
                Join<Post, User> userJoin = root.join("postedBy"); // Join with User entity
                predicates.add(criteriaBuilder.like(userJoin.get("username"), "%" + author + "%"));
            }

            // Filter by title
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + title + "%"));
            }

            // Filter by content
            if (content != null && !content.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("content"), "%" + content + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
