package com.yourapp.runner;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Map;

@Component
public class WorkspaceManager {

    public Path create(Map<String, String> exerciseFiles, Map<String, String> userFiles, Map<String, String> testFiles) throws IOException {
        Path dir = Files.createTempDirectory("dp-run-");
        writeFiles(dir, exerciseFiles);  // all files (read-only + editable starters)
        writeFiles(dir, userFiles);      // override editable ones with user's version
        writeFiles(dir, testFiles);
        return dir;
    }

    public void cleanup(Path dir) {
        try {
            Files.walk(dir)
                 .sorted(Comparator.reverseOrder())
                 .forEach(p -> { try { Files.delete(p); } catch (IOException ignored) {} });
        } catch (IOException ignored) {}
    }

    private void writeFiles(Path dir, Map<String, String> files) throws IOException {
        for (var entry : files.entrySet()) {
            Files.writeString(dir.resolve(entry.getKey()), entry.getValue());
        }
    }
}
