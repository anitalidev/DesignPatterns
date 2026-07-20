package com.yourapp.repositories;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import com.yourapp.models.PatternCategory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class FilesystemCategoryRepository implements CategoryRepository {

    private static final String CATEGORIES_ROOT = "src/main/resources/exercises";
    private static final ObjectMapper JSON = new ObjectMapper();

    // Fixed order: Creational, Structural, Behavioural
    private static final List<String> ORDER = List.of("creational", "structural", "behavioural");

    @Override
    public List<PatternCategory> getCategories() {
        try {
            List<Path> dirs = Files.list(Paths.get(CATEGORIES_ROOT))
                    .filter(Files::isDirectory)
                    .collect(Collectors.toList());

            dirs.sort(Comparator.comparingInt(p -> {
                int idx = ORDER.indexOf(p.getFileName().toString());
                return idx == -1 ? Integer.MAX_VALUE : idx;
            }));

            return dirs.stream().map(this::load).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Failed to list categories", e);
        }
    }

    @Override
    public PatternCategory getCategory(String id) {
        Path dir = Paths.get(CATEGORIES_ROOT, id);
        if (!Files.isDirectory(dir)) {
            throw new NoSuchElementException("Category not found: " + id);
        }
        return load(dir);
    }

    private PatternCategory load(Path categoryDir) {
        try {
            JsonNode meta = JSON.readTree(categoryDir.resolve("metadata.json").toFile());
            String id = meta.get("id").asText();
            String name = meta.get("name").asText();
            String description = Files.readString(categoryDir.resolve("description.md"));

            List<String> patternIds = StreamSupport
                    .stream(meta.get("patternIds").spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.toList());

            return new PatternCategory(id, name, description, patternIds);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load category at " + categoryDir, e);
        }
    }
}
