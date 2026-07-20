package com.yourapp.repositories;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.yourapp.models.Exercise;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FilesystemExerciseRepository implements ExerciseRepository {

    private static final String EXERCISES_ROOT = "src/main/resources/exercises";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Override
    public Exercise getExercise(String id) {
        // id is e.g. "observer-1"; search category → pattern → exercise
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .flatMap(categoryDir -> {
                        try {
                            return Files.list(categoryDir).filter(Files::isDirectory);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to list category dir: " + categoryDir, e);
                        }
                    })
                    .map(patternDir -> patternDir.resolve(id))
                    .filter(Files::isDirectory)
                    .findFirst()
                    .map(this::load)
                    .orElseThrow(() -> new NoSuchElementException("Exercise not found: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Failed to find exercise: " + id, e);
        }
    }

    @Override
    public List<Exercise> getExercises() {
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .flatMap(categoryDir -> {
                        try {
                            return Files.list(categoryDir).filter(Files::isDirectory);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to list category dir: " + categoryDir, e);
                        }
                    })
                    .flatMap(patternDir -> {
                        try {
                            return Files.list(patternDir).filter(Files::isDirectory);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to list pattern dir: " + patternDir, e);
                        }
                    })
                    .map(this::load)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list exercises", e);
        }
    }

    // variantDir is e.g. exercises/observer/observer-1
    private Exercise load(Path variantDir) {
        try {
            Path patternDir = variantDir.getParent();

            JsonNode meta = JSON.readTree(variantDir.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            String title = meta.get("title").asText();

            String description = Files.readString(patternDir.resolve("description.md"));

            Map<String, String> files = new LinkedHashMap<>();
            try (var entries = Files.list(variantDir.resolve("exercise"))) {
                entries.filter(p -> p.toString().endsWith(".java"))
                       .sorted()
                       .forEach(p -> {
                           try {
                               files.put(p.getFileName().toString(), Files.readString(p));
                           } catch (IOException e) {
                               throw new RuntimeException("Failed to read " + p, e);
                           }
                       });
            }

            return new Exercise(id, title, description, files);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load exercise at " + variantDir, e);
        }
    }
}
