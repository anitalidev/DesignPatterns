import { EditorState } from "@codemirror/state";
import { EditorView, keymap, lineNumbers, highlightActiveLine, highlightActiveLineGutter } from "@codemirror/view";
import { defaultKeymap, indentWithTab, history, historyKeymap } from "@codemirror/commands";
import { java } from "@codemirror/lang-java";
import { oneDark } from "@codemirror/theme-one-dark";
import { indentOnInput, bracketMatching, foldGutter } from "@codemirror/language";

// Each pattern page must define:
//   window.STARTER_CODE  — editable bad Java code
//   window.USAGE_CODE    — read-only usage example
//   window.TEST_CODE     — Java TestRunner class appended to user code for execution

let editorView = null;
let codeState  = null;
let usageState = null;
let activeEditor = "code";

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

document.addEventListener("DOMContentLoaded", () => {
  // ── page tabs (Learn / Editor) ────────────────────────────────────────
  function activateTab(tabId) {
    const btn = document.querySelector(`.tab-btn[data-tab="${tabId}"]`);
    if (!btn) return;
    document.querySelectorAll(".tab-btn").forEach(b => b.classList.remove("active"));
    document.querySelectorAll(".tab-panel").forEach(p => p.classList.remove("active"));
    btn.classList.add("active");
    document.getElementById(tabId).classList.add("active");
  }

  const hash = location.hash.slice(1);
  if (hash && document.getElementById(hash)) activateTab(hash);

  document.querySelectorAll(".tab-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      const target = btn.dataset.tab;
      activateTab(target);
      history.replaceState(null, "", "#" + target);
    });
  });

  // ── CodeMirror setup ──────────────────────────────────────────────────
  const mount = document.getElementById("editor-mount");
  if (!mount) return;

  const startCode = (window.STARTER_CODE || "").trim();
  const usageCode = (window.USAGE_CODE   || "// No usage example provided.").trim();

  codeState  = EditorState.create({ doc: startCode, extensions: buildExtensions(false) });
  usageState = EditorState.create({ doc: usageCode, extensions: buildExtensions(true)  });

  editorView = new EditorView({ state: codeState, parent: mount });

  // ── code sub-tabs (Code / Usage) ──────────────────────────────────────
  document.querySelectorAll(".code-tab-btn").forEach(btn => {
    btn.addEventListener("click", () => {
      const target = btn.dataset.editor;
      document.querySelectorAll(".code-tab-btn").forEach(b => b.classList.remove("active"));
      btn.classList.add("active");

      if (target === "code" && activeEditor !== "code") {
        usageState = editorView.state;
        editorView.setState(codeState);
        activeEditor = "code";
      } else if (target === "usage" && activeEditor !== "usage") {
        codeState = editorView.state;
        editorView.setState(usageState);
        activeEditor = "usage";
      }
    });
  });

  // ── Reset ─────────────────────────────────────────────────────────────
  document.getElementById("btn-reset")?.addEventListener("click", () => {
    codeState = EditorState.create({ doc: startCode, extensions: buildExtensions(false) });
    if (activeEditor === "code") editorView.setState(codeState);
    clearResults();
  });

  // ── Run Tests ─────────────────────────────────────────────────────────
  document.getElementById("btn-run")?.addEventListener("click", runUserTests);
});

function clearResults() {
  const el = document.getElementById("test-results");
  if (el) el.innerHTML = '<span class="placeholder">Run tests to see results.</span>';
}

async function runUserTests() {
  const resultsEl = document.getElementById("test-results");
  const runBtn    = document.getElementById("btn-run");
  if (!editorView || !resultsEl) return;

  const userCode = (activeEditor === "code"
    ? editorView.state
    : codeState).doc.toString();

  const testCode = (window.TEST_CODE || "").trim();
  const fullCode = userCode + "\n\n" + testCode;

  // Show loading state
  runBtn.disabled = true;
  runBtn.textContent = "Running…";
  resultsEl.innerHTML = '<span class="placeholder">Sending to Java runtime…</span>';

  let output = "";
  try {
    const res = await fetch("/api/run-java", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ code: fullCode }),
    });
    const data = await res.json();
    output = (data.output || "").trim();
  } catch (err) {
    resultsEl.innerHTML = `<div class="test-err">Network error: ${escHtml(String(err))}</div>`;
    runBtn.disabled = false;
    runBtn.textContent = "Run Tests";
    return;
  }

  runBtn.disabled = false;
  runBtn.textContent = "Run Tests";

  // Check for compile error (no PASS:/FAIL: lines present)
  const lines = output.split("\n");
  const resultLines = lines.filter(l => l.startsWith("PASS:") || l.startsWith("FAIL:"));

  if (resultLines.length === 0) {
    resultsEl.innerHTML =
      `<div class="test-row fail"><span class="test-icon"></span><span class="test-name">Compile error</span></div>` +
      `<div class="test-err">${escHtml(output)}</div>`;
    return;
  }

  const rows = resultLines.map(line => {
    const pass = line.startsWith("PASS:");
    const rest = line.slice(5).trim();
    const [name, ...errParts] = rest.split(" | ");
    const err = errParts.join(" | ");
    return `<div class="test-row ${pass ? "pass" : "fail"}">
      <span class="test-icon"></span>
      <span class="test-name">${escHtml(name.trim())}</span>
    </div>${err ? `<div class="test-err">${escHtml(err)}</div>` : ""}`;
  }).join("");

  const summaryLine = lines.find(l => /^\d+\/\d+ passed/.test(l)) || "";
  const [p, t] = summaryLine.match(/\d+/g) || [0, resultLines.length];
  const allPass = Number(p) === Number(t);
  const summary = summaryLine
    ? `<div class="summary ${allPass ? "all-pass" : "has-fail"}">${escHtml(summaryLine)}</div>`
    : "";

  resultsEl.innerHTML = rows + summary;
}

function escHtml(str) {
  return str.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
}
