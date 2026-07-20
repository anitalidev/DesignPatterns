package com.yourapp.runner;

import com.yourapp.models.CompilationResult;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class CompilerService {

    public CompilationResult compile(Path workspaceDir) throws IOException, InterruptedException {
        List<String> cmd = new ArrayList<>();
        cmd.add("javac");
        cmd.add("-encoding");
        cmd.add("UTF-8");
        // compile all .java files in the workspace
        cmd.add("*.java");

        ProcessBuilder pb = new ProcessBuilder("sh", "-c", "javac -encoding UTF-8 -sourcepath \"\" *.java")
                .directory(workspaceDir.toFile())
                .redirectErrorStream(true);

        Process proc = pb.start();
        String output = new String(proc.getInputStream().readAllBytes());
        int exit = proc.waitFor();

        return new CompilationResult(exit == 0, output);
    }
}
