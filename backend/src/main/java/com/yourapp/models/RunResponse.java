package com.yourapp.models;

import java.util.List;

public class RunResponse {
    private final boolean compiled;
    private final String compilerOutput;
    private final List<TestResult> tests;

    public RunResponse(boolean compiled, String compilerOutput, List<TestResult> tests) {
        this.compiled = compiled;
        this.compilerOutput = compilerOutput;
        this.tests = tests;
    }

    public boolean isCompiled() { return compiled; }
    public String getCompilerOutput() { return compilerOutput; }
    public List<TestResult> getTests() { return tests; }
}
