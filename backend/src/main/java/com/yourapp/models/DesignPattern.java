package com.yourapp.models;

import java.util.Map;

public class DesignPattern {
    private final String id;
    private final String title;
    private final String overview;
    private final String description;
    // key: exercise title, value: exercise id
    private final Map<String, String> exercises;

    public DesignPattern(String id, String title, String overview, String description, Map<String, String> exercises) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.description = description;
        this.exercises = exercises;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getDescription() { return description; }
    public Map<String, String> getExercises() { return exercises; }
}
