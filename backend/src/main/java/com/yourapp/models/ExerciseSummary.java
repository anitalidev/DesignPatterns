package com.yourapp.models;

public class ExerciseSummary {
    private final String id;
    private final String title;

    public ExerciseSummary(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
}
