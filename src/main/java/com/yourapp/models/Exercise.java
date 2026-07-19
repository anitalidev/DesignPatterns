package com.yourapp.models;

import java.util.Map;

public class Exercise {
    private final String id;
    private final String title;
    private final String description;
    private final Map<String, String> files;

    public Exercise(String id, String title, String description, Map<String, String> files) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.files = files;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Map<String, String> getFiles() { return files; }
}
