package com.TrungTinhFullStack.blog_backend_http.Controller;

import com.TrungTinhFullStack.blog_backend_http.Entity.Category;
import com.TrungTinhFullStack.blog_backend_http.Repository.CategoryRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping()
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            return new ResponseEntity<>("Danh mục đã tồn tại !", HttpStatus.BAD_REQUEST);
        }
        categoryRepository.save(category);
        return new ResponseEntity<>("Thêm danh mục thành công !", HttpStatus.CREATED);
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
