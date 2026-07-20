package com.yourapp.repositories;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.yourapp.models.DesignPattern;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class FilesystemPatternRepository implements PatternRepository {

    private static final String EXERCISES_ROOT = "src/main/resources/exercises";
    private static final ObjectMapper JSON = new ObjectMapper();

    @Override
    public List<DesignPattern> getPatterns() {
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .map(this::load)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list patterns", e);
        }
    }

    @Override
    public DesignPattern getPattern(String id) {
        Path dir = Paths.get(EXERCISES_ROOT, id);
        if (!Files.isDirectory(dir)) {
            throw new NoSuchElementException("Pattern not found: " + id);
        }
        return load(dir);
    }

    private DesignPattern load(Path patternDir) {
        try {
            JsonNode meta = JSON.readTree(patternDir.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            String title = meta.get("title").asText();
            String overview = meta.get("overview").asText();

            String description = Files.readString(patternDir.resolve("description.md"));

            // Build exercises map: exercise title -> exercise id
            Map<String, String> exercises = new LinkedHashMap<>();
            JsonNode exerciseIds = meta.get("exerciseIds");
            if (exerciseIds != null && exerciseIds.isArray()) {
                for (JsonNode idNode : exerciseIds) {
                    String exerciseId = idNode.asText();
                    Path exerciseDir = patternDir.resolve(exerciseId);
                    if (Files.isDirectory(exerciseDir)) {
                        JsonNode exerciseMeta = JSON.readTree(exerciseDir.resolve("metadata.json").toFile());
                        String exerciseTitle = exerciseMeta.get("title").asText();
                        exercises.put(exerciseTitle, exerciseId);
                    }
                }
            }

            return new DesignPattern(id, title, overview, description, exercises);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load pattern at " + patternDir, e);
        }
    }
}
