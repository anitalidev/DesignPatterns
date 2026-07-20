package com.yourapp.services;

import com.yourapp.models.*;
import com.yourapp.repositories.ExerciseRepository;
import com.yourapp.runner.CompilerService;
import com.yourapp.runner.TestService;
import com.yourapp.runner.WorkspaceManager;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class RunnerService {

    private final ExerciseRepository exerciseRepository;
    private final WorkspaceManager workspaceManager;
    private final CompilerService compilerService;
    private final TestService testService;

    public RunnerService(ExerciseRepository exerciseRepository,
                         WorkspaceManager workspaceManager,
                         CompilerService compilerService,
                         TestService testService) {
        this.exerciseRepository = exerciseRepository;
        this.workspaceManager = workspaceManager;
        this.compilerService = compilerService;
        this.testService = testService;
    }

    public RunResponse run(RunRequest request) {
        Exercise exercise = exerciseRepository.getExercise(request.getExerciseId());
        Path workspace = null;
        try {
            workspace = workspaceManager.create(
                    request.getFiles(),
                    exercise.getUsageFiles(),
                    exercise.getTestFiles());

            CompilationResult compilation = compilerService.compile(workspace);
            if (!compilation.isSuccess()) {
                return new RunResponse(false, compilation.getOutput(), List.of());
            }

            List<TestResult> tests = testService.run(workspace);
            return new RunResponse(true, null, tests);
        } catch (Exception e) {
            return new RunResponse(false, e.getMessage(), List.of());
        } finally {
            if (workspace != null) workspaceManager.cleanup(workspace);
        }
    }
}
