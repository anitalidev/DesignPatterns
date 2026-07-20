package com.yourapp.services;

import com.yourapp.models.Exercise;
import com.yourapp.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

@Service
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Exercise getExercise(String id) {
        return exerciseRepository.getExercise(id);
    }
}
