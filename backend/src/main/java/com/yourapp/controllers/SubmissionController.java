package com.yourapp.controllers;

import com.yourapp.models.RunRequest;
import com.yourapp.models.RunResponse;
import com.yourapp.services.RunnerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SubmissionController {

    private final RunnerService runnerService;

    public SubmissionController(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @PostMapping("/run")
    public RunResponse run(@RequestBody RunRequest request) {
        return runnerService.run(request);
    }
}
