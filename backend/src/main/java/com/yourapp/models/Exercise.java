package com.yourapp.models;

import java.util.List;
import java.util.Map;

public class Exercise {
    private final String id;
    private final String title;
    private final String description;
    private final String exerciseDescription;
    private final Map<String, String> files;
    private final List<String> editableFiles;
    private final Map<String, String> testFiles;
    private final List<String> hints;
    private final List<String> issues;
    private final List<String> goals;
    private final String overallGoal;

    public Exercise(String id, String title, String description, String exerciseDescription, Map<String, String> files, List<String> editableFiles, Map<String, String> testFiles, List<String> hints, List<String> issues, List<String> goals, String overallGoal) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.exerciseDescription = exerciseDescription;
        this.files = files;
        this.editableFiles = editableFiles;
        this.testFiles = testFiles;
        this.hints = hints;
        this.issues = issues;
        this.goals = goals;
        this.overallGoal = overallGoal;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getExerciseDescription() { return exerciseDescription; }
    public Map<String, String> getFiles() { return files; }
    public List<String> getEditableFiles() { return editableFiles; }
    public Map<String, String> getTestFiles() { return testFiles; }
    public List<String> getHints() { return hints; }
    public List<String> getIssues() { return issues; }
    public List<String> getGoals() { return goals; }
    public String getOverallGoal() { return overallGoal; }
}
