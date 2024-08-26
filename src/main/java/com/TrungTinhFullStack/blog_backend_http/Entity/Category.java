package com.TrungTinhFullStack.blog_backend_http.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<Post> posts;
}
