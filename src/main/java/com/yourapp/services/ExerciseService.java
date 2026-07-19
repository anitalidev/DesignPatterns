package com.yourapp.services;

import com.yourapp.models.Exercise;
import com.yourapp.models.ExerciseSummary;
import com.yourapp.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<ExerciseSummary> getExercises() {
        return exerciseRepository.getExercises().stream()
                .map(e -> new ExerciseSummary(e.getId(), e.getTitle()))
                .collect(Collectors.toList());
    }

    public Exercise getExercise(String id) {
        return exerciseRepository.getExercise(id);
    }
}
