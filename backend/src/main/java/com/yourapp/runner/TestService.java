package com.yourapp.runner;

import com.yourapp.models.TestResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class TestService {

    private static final int TIMEOUT_SECONDS = 10;

    public List<TestResult> run(Path workspaceDir) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("java", "TestRunner")
                .directory(workspaceDir.toFile())
                .redirectErrorStream(true);

        Process proc = pb.start();

        // Read stdout concurrently to avoid blocking if the pipe fills
        Future<String> outputFuture = Executors.newSingleThreadExecutor().submit(
                () -> new String(proc.getInputStream().readAllBytes()));

        boolean finished = proc.waitFor(TIMEOUT_SECONDS, TimeUnit.SECONDS);
        if (!finished) {
            proc.destroyForcibly();
            return List.of(new TestResult("Test execution timed out", false,
                    "Timed out after " + TIMEOUT_SECONDS + " seconds"));
        }

        String output;
        try {
            output = outputFuture.get(5, TimeUnit.SECONDS);
        } catch (ExecutionException | TimeoutException e) {
            output = "";
        }

        return parseOutput(output);
    }

    private List<TestResult> parseOutput(String output) {
        List<TestResult> results = new ArrayList<>();
        for (String line : output.lines().toList()) {
            if (line.startsWith("PASS:")) {
                results.add(new TestResult(line.substring(5).trim(), true, null));
            } else if (line.startsWith("FAIL:")) {
                String rest = line.substring(5).trim();
                int sep = rest.indexOf(" | ");
                if (sep >= 0) {
                    results.add(new TestResult(rest.substring(0, sep), false, rest.substring(sep + 3)));
                } else {
                    results.add(new TestResult(rest, false, null));
                }
            }
        }
        return results;
    }
}
