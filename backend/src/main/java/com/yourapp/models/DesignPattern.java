package com.yourapp.models;

import java.util.List;
import java.util.Map;

public class DesignPattern {
    private final String id;
    private final String title;
    private final String overview;
    private final String description;
    private final List<String> useCases;
    // key: exercise title, value: exercise id
    private final Map<String, String> exercises;

    public DesignPattern(String id, String title, String overview, String description, List<String> useCases, Map<String, String> exercises) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.description = description;
        this.useCases = useCases;
        this.exercises = exercises;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getDescription() { return description; }
    public List<String> getUseCases() { return useCases; }
    public Map<String, String> getExercises() { return exercises; }
}
