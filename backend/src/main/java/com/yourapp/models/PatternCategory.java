package com.yourapp.models;

import java.util.List;

public class PatternCategory {
    private final String id;
    private final String name;
    private final String description;
    private final List<String> patternIds;

    public PatternCategory(String id, String name, String description, List<String> patternIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.patternIds = patternIds;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<String> getPatternIds() { return patternIds; }
}
