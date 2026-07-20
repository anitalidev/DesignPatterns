package com.yourapp.models;

import java.util.Map;

public class RunRequest {
    private String exerciseId;
    private Map<String, String> files;

    public String getExerciseId() { return exerciseId; }
    public void setExerciseId(String exerciseId) { this.exerciseId = exerciseId; }

    public Map<String, String> getFiles() { return files; }
    public void setFiles(Map<String, String> files) { this.files = files; }
}
