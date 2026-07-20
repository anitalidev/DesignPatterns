package com.yourapp.controllers;

import com.yourapp.models.DesignPattern;
import com.yourapp.repositories.PatternRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patterns")
public class PatternController {

    private final PatternRepository patternRepository;

    public PatternController(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

    @GetMapping
    public List<DesignPattern> getPatterns() {
        return patternRepository.getPatterns();
    }

    @GetMapping("/{id}")
    public DesignPattern getPattern(@PathVariable String id) {
        return patternRepository.getPattern(id);
    }
}
