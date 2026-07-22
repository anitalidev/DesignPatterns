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
    private static final List<String> CATEGORY_ORDER = List.of("creational", "structural", "behavioural");
    private static final ObjectMapper JSON = new ObjectMapper();

    @Override
    public List<DesignPattern> getPatterns() {
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .sorted(Comparator.comparingInt(p -> {
                        int i = CATEGORY_ORDER.indexOf(p.getFileName().toString());
                        return i < 0 ? Integer.MAX_VALUE : i;
                    }))
                    .flatMap(categoryDir -> {
                        try {
                            return Files.list(categoryDir)
                                    .filter(Files::isDirectory)
                                    .filter(p -> p.resolve("metadata.json").toFile().exists())
                                    .map(this::load)
                                    .sorted(Comparator.comparingInt(DesignPattern::getOrder));
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to list patterns in " + categoryDir, e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list categories", e);
        }
    }

    @Override
    public DesignPattern getPattern(String id) {
        try {
            return Files.list(Paths.get(EXERCISES_ROOT))
                    .filter(Files::isDirectory)
                    .map(categoryDir -> categoryDir.resolve(id))
                    .filter(Files::isDirectory)
                    .findFirst()
                    .map(this::load)
                    .orElseThrow(() -> new NoSuchElementException("Pattern not found: " + id));
        } catch (IOException e) {
            throw new RuntimeException("Failed to find pattern: " + id, e);
        }
    }

    private DesignPattern load(Path patternDir) {
        try {
            JsonNode meta = JSON.readTree(patternDir.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            int order = meta.has("order") ? meta.get("order").asInt() : 0;
            String title = meta.get("title").asText();
            String overview = meta.get("overview").asText();

            String description = Files.readString(patternDir.resolve("description.md"));

            List<String> useCases = new ArrayList<>();
            JsonNode useCasesNode = meta.get("useCases");
            if (useCasesNode != null && useCasesNode.isArray()) {
                for (JsonNode uc : useCasesNode) {
                    useCases.add(uc.asText());
                }
            }

            List<String> exampleUses = new ArrayList<>();
            JsonNode exampleUsesNode = meta.get("exampleUses");
            if (exampleUsesNode != null && exampleUsesNode.isArray()) {
                for (JsonNode eu : exampleUsesNode) {
                    exampleUses.add(eu.asText());
                }
            }

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

            return new DesignPattern(id, order, title, overview, description, useCases, exampleUses, exercises);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load pattern at " + patternDir, e);
        }
    }
}
