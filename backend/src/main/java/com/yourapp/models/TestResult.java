package com.yourapp.models;

public class TestResult {
    private final String name;
    private final boolean passed;
    private final String message;

    public TestResult(String name, boolean passed, String message) {
        this.name = name;
        this.passed = passed;
        this.message = message;
    }

    public String getName() { return name; }
    public boolean isPassed() { return passed; }
    public String getMessage() { return message; }
}
