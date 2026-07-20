package com.yourapp.repositories;

import com.yourapp.models.PatternCategory;

import java.util.List;

public interface CategoryRepository {
    List<PatternCategory> getCategories();
    PatternCategory getCategory(String id);
}
