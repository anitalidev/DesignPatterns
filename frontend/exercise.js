import { EditorState } from "@codemirror/state";
import { EditorView, keymap, lineNumbers, highlightActiveLine, highlightActiveLineGutter } from "@codemirror/view";
import { defaultKeymap, indentWithTab, history, historyKeymap } from "@codemirror/commands";
import { java } from "@codemirror/lang-java";
import { oneDark } from "@codemirror/theme-one-dark";
import { indentOnInput, bracketMatching, foldGutter } from "@codemirror/language";
import { marked } from "marked";

const params = new URLSearchParams(location.search);
const id = params.get("id");

if (!id) {
  document.getElementById("exercise-description").innerHTML =
    '<p style="color:red">No exercise id in URL. Try <code>?id=observer-1</code></p>';
}

let editorView = null;
let starterCode = "";
let editableFilename = "";

function buildExtensions(readonly = false) {
  return [
    lineNumbers(),
    highlightActiveLine(),
    highlightActiveLineGutter(),
    history(),
    foldGutter(),
    indentOnInput(),
    bracketMatching(),
    java(),
    oneDark,
    keymap.of([...defaultKeymap, ...historyKeymap, indentWithTab]),
    EditorView.lineWrapping,
    EditorState.readOnly.of(readonly),
  ];
}

function initEditor(code) {
  starterCode = code;
  const mount = document.getElementById("editor-mount");
  const state = EditorState.create({ doc: code, extensions: buildExtensions(false) });
  editorView = new EditorView({ state, parent: mount });
  requestAnimationFrame(() => {
    const lineH = mount.querySelector(".cm-line")?.offsetHeight ?? 20;
    editorView.dom.style.height = (21 * lineH + 8) + "px";
  });
}

function renderList(listEl, items, className) {
  listEl.innerHTML = items.map(t => `<li class="${className}">${escHtml(t)}</li>`).join("");
}

async function loadExercise() {
  const res = await fetch(`/api/exercises/${id}`);
  if (!res.ok) {
    document.getElementById("exercise-description").innerHTML =
      `<p style="color:red">Exercise "${id}" not found.</p>`;
    return;
  }

  const exercise = await res.json();

  document.title = `${exercise.title} — Design Patterns`;
  document.getElementById("nav-title").textContent = exercise.title;
  document.getElementById("exercise-title").textContent = exercise.title;
  document.getElementById("exercise-description").innerHTML = marked.parse(exercise.description);

  if (exercise.exerciseDescription) {
    const el = document.getElementById("exercise-description-ex");
    el.textContent = exercise.exerciseDescription;
    el.classList.remove("hidden");
  }

  if (exercise.issues?.length) {
    renderList(document.getElementById("issues-list"), exercise.issues, "issue-item");
    document.getElementById("exercise-issues").classList.remove("hidden");
  }

  if (exercise.overallGoal) {
    document.getElementById("overall-goal-text").textContent = exercise.overallGoal;
    document.getElementById("exercise-overall-goal").classList.remove("hidden");
  }

  if (exercise.goals?.length) {
    renderList(document.getElementById("goals-list"), exercise.goals, "goal-item");
    document.getElementById("exercise-goals").classList.remove("hidden");
  }

  const firstEditable = (exercise.editableFiles ?? [])[0];
  editableFilename = firstEditable ?? "";
  initEditor(exercise.files[firstEditable] ?? "");
}

document.addEventListener("DOMContentLoaded", () => {
  if (id) loadExercise();

  document.querySelectorAll(".tab-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
      document.querySelectorAll(".tab-panel").forEach(p => p.classList.remove("active"));
      btn.classList.add("active");
      document.getElementById(btn.dataset.tab).classList.add("active");
    });
  });

  document.getElementById("btn-reset")?.addEventListener("click", () => {
    if (!editorView) return;
    editorView.setState(EditorState.create({ doc: starterCode, extensions: buildExtensions(false) }));
    document.getElementById("test-results").innerHTML =
      '<span class="placeholder">Run tests to see results.</span>';
  });

  document.getElementById("btn-run")?.addEventListener("click", runTests);
});

async function runTests() {
  const resultsEl = document.getElementById("test-results");
  const runBtn = document.getElementById("btn-run");
  if (!editorView) return;

  const code = editorView.state.doc.toString();

  runBtn.disabled = true;
  runBtn.textContent = "Running…";
  resultsEl.innerHTML = '<span class="placeholder">Running…</span>';

  try {
    const res = await fetch("/api/run", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ exerciseId: id, files: { [editableFilename]: code } }),
    });
    const data = await res.json();
    renderResults(data);
  } catch (err) {
    resultsEl.innerHTML = `<div class="test-err">Network error: ${escHtml(String(err))}</div>`;
  } finally {
    runBtn.disabled = false;
    runBtn.textContent = "Run Tests";
  }
}

function renderResults(data) {
  const resultsEl = document.getElementById("test-results");

  if (!data.compiled) {
    resultsEl.innerHTML =
      `<div class="test-row fail"><span class="test-icon"></span><span class="test-name">Compile error</span></div>` +
      `<div class="test-err">${escHtml(data.compilerOutput)}</div>`;
    return;
  }

  const rows = data.tests.map(t =>
    `<div class="test-row ${t.passed ? "pass" : "fail"}">
      <span class="test-icon"></span>
      <span class="test-name">${escHtml(t.name)}</span>
    </div>${t.message ? `<div class="test-err">${escHtml(t.message)}</div>` : ""}`
  ).join("");

  const passed = data.tests.filter(t => t.passed).length;
  const total = data.tests.length;
  const allPass = passed === total;
  const summary = `<div class="summary ${allPass ? "all-pass" : "has-fail"}">${passed}/${total} passed</div>`;

  resultsEl.innerHTML = rows + summary;
}

function escHtml(str) {
  return str.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
}
