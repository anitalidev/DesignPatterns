package com.yourapp.models;

import java.util.List;
import java.util.Map;

public class Exercise {
    private final String id;
    private final String title;
    private final String description;
    private final Map<String, String> files;
    private final Map<String, String> usageFiles;
    private final Map<String, String> testFiles;
    private final List<String> hints;
    private final List<String> issues;
    private final List<String> goals;

    public Exercise(String id, String title, String description, Map<String, String> files, Map<String, String> usageFiles, Map<String, String> testFiles, List<String> hints, List<String> issues, List<String> goals) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.files = files;
        this.usageFiles = usageFiles;
        this.testFiles = testFiles;
        this.hints = hints;
        this.issues = issues;
        this.goals = goals;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Map<String, String> getFiles() { return files; }
    public Map<String, String> getUsageFiles() { return usageFiles; }
    public Map<String, String> getTestFiles() { return testFiles; }
    public List<String> getHints() { return hints; }
    public List<String> getIssues() { return issues; }
    public List<String> getGoals() { return goals; }
}
