package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Entity.Category;
import com.TrungTinhFullStack.blog_backend_http.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping()
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,@RequestBody Category category){
        return categoryService.updateCategory(id,category);
    }

    @DeleteMapping("/{id}")
    public Category deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
}
