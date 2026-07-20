package com.yourapp.controllers;

import com.yourapp.models.PatternCategory;
import com.yourapp.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public List<PatternCategory> getCategories() {
        return categoryRepository.getCategories();
    }

    @GetMapping("/{id}")
    public PatternCategory getCategory(@PathVariable String id) {
        return categoryRepository.getCategory(id);
    }
}
