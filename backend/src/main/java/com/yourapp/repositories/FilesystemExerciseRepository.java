package com.yourapp.repositories;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.yourapp.models.Exercise;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
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

    // variantDir is e.g. exercises/creational/factory/factory-1
    private Exercise load(Path variantDir) {
        try {
            Path patternDir = variantDir.getParent();

            JsonNode meta = JSON.readTree(variantDir.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            String title = meta.get("title").asText();

            String description = Files.readString(patternDir.resolve("description.md"));

            String exerciseDescription = meta.has("description") ? meta.get("description").asText() : null;

            Map<String, String> files = readJavaFiles(variantDir.resolve("exercise"));
            List<String> editableFiles = readStringList(meta, "editableFiles");
            Map<String, String> testFiles = readJavaFiles(variantDir.resolve("tests"));

            List<String> hints = readStringList(meta, "hints");
            List<String> issues = readStringList(meta, "issues");
            List<String> goals = readStringList(meta, "goals");

            return new Exercise(id, title, description, exerciseDescription, files, editableFiles, testFiles, hints, issues, goals);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load exercise at " + variantDir, e);
        }
    }

    private List<String> readStringList(JsonNode node, String field) {
        JsonNode arr = node.get(field);
        if (arr == null || !arr.isArray()) return List.of();
        List<String> result = new ArrayList<>();
        arr.forEach(el -> result.add(el.asText()));
        return result;
    }

    private Map<String, String> readJavaFiles(Path dir) throws IOException {
        Map<String, String> result = new LinkedHashMap<>();
        if (!Files.isDirectory(dir)) return result;
        try (var entries = Files.list(dir)) {
            entries.filter(p -> p.toString().endsWith(".java"))
                   .sorted()
                   .forEach(p -> {
                       try {
                           result.put(p.getFileName().toString(), Files.readString(p));
                       } catch (IOException e) {
                           throw new RuntimeException("Failed to read " + p, e);
                       }
                   });
        }
        return result;
    }
}
