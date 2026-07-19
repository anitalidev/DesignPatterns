package com.yourapp.repositories;

import com.yourapp.models.Exercise;

import java.util.List;

public interface ExerciseRepository {
    Exercise getExercise(String id);
    List<Exercise> getExercises();
}
