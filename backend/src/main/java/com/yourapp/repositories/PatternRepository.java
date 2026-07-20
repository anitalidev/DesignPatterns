package com.yourapp.repositories;

import com.yourapp.models.DesignPattern;

import java.util.List;

public interface PatternRepository {
    List<DesignPattern> getPatterns();
    DesignPattern getPattern(String id);
}
