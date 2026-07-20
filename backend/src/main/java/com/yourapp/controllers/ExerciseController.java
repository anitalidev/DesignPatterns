package com.yourapp.controllers;

import com.yourapp.models.Exercise;
import com.yourapp.services.ExerciseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/{id}")
    public Exercise getExercise(@PathVariable String id) {
        return exerciseService.getExercise(id);
    }
}
