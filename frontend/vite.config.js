import { resolve } from "path";
import { defineConfig } from "vite";
import { exec } from "child_process";
import { writeFileSync, mkdirSync, rmSync } from "fs";
import { tmpdir } from "os";
import { join } from "path";

function javaRunnerPlugin() {
  return {
    name: "java-runner",
    configureServer(server) {
      server.middlewares.use("/api/run-java", (req, res) => {
        if (req.method !== "POST") { res.statusCode = 405; res.end(); return; }

        let body = "";
        req.on("data", chunk => (body += chunk));
        req.on("end", () => {
          let code;
          try { code = JSON.parse(body).code; } catch {
            res.statusCode = 400; res.end("bad json"); return;
          }

          const dir = join(tmpdir(), "dp-" + Date.now());
          mkdirSync(dir, { recursive: true });
          const file = join(dir, "Main.java");
          writeFileSync(file, code, "utf8");

          exec(
            `javac "${file}" -d "${dir}" 2>&1 && java -cp "${dir}" TestRunner 2>&1`,
            { timeout: 15000 },
            (err, stdout, stderr) => {
              res.setHeader("Content-Type", "application/json");
              res.end(JSON.stringify({ output: stdout || stderr || "" }));
              try { rmSync(dir, { recursive: true, force: true }); } catch {}
            }
          );
        });
      });
    },
  };
}

export default defineConfig({
  plugins: [javaRunnerPlugin()],
  server: {
    proxy: {
      "/api": "http://localhost:8080",
    },
  },
  build: {
    rollupOptions: {
      input: {
        main:     resolve(__dirname, "index.html"),
        exercise: resolve(__dirname, "exercise.html"),
        pattern:  resolve(__dirname, "pattern.html"),
      },
    },
  },
});
