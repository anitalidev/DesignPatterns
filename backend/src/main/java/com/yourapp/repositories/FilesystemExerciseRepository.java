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
        Path root = Paths.get(EXERCISES_ROOT, id);
        if (!Files.isDirectory(root)) {
            throw new NoSuchElementException("Exercise not found: " + id);
        }
        return load(root);
    }

    @Override
    public List<Exercise> getExercises() {
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .map(this::load)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list exercises", e);
        }
    }

    private Exercise load(Path root) {
        try {
            JsonNode meta = JSON.readTree(root.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            String title = meta.get("title").asText();

            String description = Files.readString(root.resolve("description.md"));

            Map<String, String> files = new LinkedHashMap<>();
            try (var entries = Files.list(root.resolve("exercise"))) {
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
            throw new RuntimeException("Failed to load exercise at " + root, e);
        }
    }
}
