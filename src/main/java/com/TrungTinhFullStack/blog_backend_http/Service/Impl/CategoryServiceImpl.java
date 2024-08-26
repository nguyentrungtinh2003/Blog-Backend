package com.TrungTinhFullStack.blog_backend_http.Service.Impl;

import com.TrungTinhFullStack.blog_backend_http.Entity.Category;
import com.TrungTinhFullStack.blog_backend_http.Repository.CategoryRepository;
import com.TrungTinhFullStack.blog_backend_http.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category createCategory(Category category) {
        Category category1 = categoryRepository.save(category);
        return category1;
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Category category1 = categoryRepository.findById(id).orElse(null);
        category1.setName(category.getName());
        categoryRepository.save(category1);
        return category1;
    }

    @Override
    public Category deleteCategory(Long id) {
        Category category1 = categoryRepository.findById(id).orElse(null);
        categoryRepository.delete(category1);
        return category1;
    }
}
