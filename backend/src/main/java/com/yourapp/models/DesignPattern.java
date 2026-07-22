package com.yourapp.models;

import java.util.List;
import java.util.Map;

public class DesignPattern {
    private final String id;
    private final int order;
    private final String title;
    private final String overview;
    private final String description;
    private final List<String> useCases;
    private final List<String> exampleUses;
    // key: exercise title, value: exercise id
    private final Map<String, String> exercises;

    public DesignPattern(String id, int order, String title, String overview, String description, List<String> useCases, List<String> exampleUses, Map<String, String> exercises) {
        this.id = id;
        this.order = order;
        this.title = title;
        this.overview = overview;
        this.description = description;
        this.useCases = useCases;
        this.exampleUses = exampleUses;
        this.exercises = exercises;
    }

    public String getId() { return id; }
    public int getOrder() { return order; }
    public String getTitle() { return title; }
    public String getOverview() { return overview; }
    public String getDescription() { return description; }
    public List<String> getUseCases() { return useCases; }
    public List<String> getExampleUses() { return exampleUses; }
    public Map<String, String> getExercises() { return exercises; }
}
