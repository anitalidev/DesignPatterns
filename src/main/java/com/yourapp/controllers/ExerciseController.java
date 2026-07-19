package com.yourapp.controllers;

import com.yourapp.models.Exercise;
import com.yourapp.models.ExerciseSummary;
import com.yourapp.services.ExerciseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping
    public List<ExerciseSummary> getExercises() {
        return exerciseService.getExercises();
    }

    @GetMapping("/{id}")
    public Exercise getExercise(@PathVariable String id) {
        return exerciseService.getExercise(id);
    }
}
