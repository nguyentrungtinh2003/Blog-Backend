package com.TrungTinhFullStack.blog_backend_http.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 5000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User postedBy;

    private String img;

    private Date date;

    private int likeCount;

    private int viewCount;

    private List<String> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id",nullable = false)
    @JsonBackReference
    private Category category;

}
