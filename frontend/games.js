export function initGames(patterns, categories) {
  const patternCatMap = {};
  for (const cat of categories) {
    for (const pid of cat.patternIds) patternCatMap[pid] = cat;
  }
  const all = patterns
    .map(p => ({ ...p, category: patternCatMap[p.id] ?? null }))
    .filter(p => p.category && p.overview);

  const el = document.getElementById("tab-games");

  const ICONS = {
    sort: `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="4" y="6" width="14" height="10" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="22" y="6" width="14" height="10" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="4" y="24" width="14" height="10" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="22" y="24" width="14" height="10" rx="2" stroke="currentColor" stroke-width="2"/>
      <line x1="11" y1="16" x2="11" y2="24" stroke="currentColor" stroke-width="2"/>
      <line x1="29" y1="16" x2="29" y2="24" stroke="currentColor" stroke-width="2"/>
      <polyline points="8,20 11,24 14,20" stroke="currentColor" stroke-width="2" fill="none" stroke-linejoin="round"/>
      <polyline points="26,20 29,24 32,20" stroke="currentColor" stroke-width="2" fill="none" stroke-linejoin="round"/>
    </svg>`,
    match: `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="3" y="8" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="3" y="19" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="3" y="30" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="25" y="8" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="25" y="19" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="25" y="30" width="12" height="7" rx="2" stroke="currentColor" stroke-width="2"/>
      <path d="M15 11.5 Q20 11.5 25 22.5" stroke="currentColor" stroke-width="1.75" fill="none" stroke-dasharray="2.5 2"/>
      <path d="M15 22.5 Q20 22.5 25 11.5" stroke="currentColor" stroke-width="1.75" fill="none" stroke-dasharray="2.5 2"/>
      <path d="M15 33.5 Q20 33.5 25 33.5" stroke="currentColor" stroke-width="1.75" fill="none" stroke-dasharray="2.5 2"/>
    </svg>`,
    "name-em": `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <circle cx="20" cy="22" r="13" stroke="currentColor" stroke-width="2"/>
      <line x1="20" y1="22" x2="20" y2="13" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      <line x1="20" y1="22" x2="26" y2="18" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      <line x1="14" y1="4" x2="26" y2="4" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      <line x1="20" y1="4" x2="20" y2="9" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      <circle cx="20" cy="22" r="1.5" fill="currentColor"/>
    </svg>`,
    quiz: `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <circle cx="20" cy="20" r="15" stroke="currentColor" stroke-width="2"/>
      <path d="M15 15.5C15 12.46 17.24 10 20 10C22.76 10 25 12.46 25 15.5C25 18 23.5 19.5 21.5 20.5C20.5 21 20 21.75 20 23" stroke="currentColor" stroke-width="2" stroke-linecap="round"/>
      <circle cx="20" cy="28" r="1.5" fill="currentColor"/>
    </svg>`,
    flashcards: `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="6" y="12" width="26" height="18" rx="2.5" stroke="currentColor" stroke-width="2"/>
      <line x1="6" y1="18" x2="32" y2="18" stroke="currentColor" stroke-width="2"/>
      <rect x="10" y="8" width="26" height="18" rx="2.5" stroke="currentColor" stroke-width="1.5" stroke-dasharray="3 2"/>
      <line x1="12" y1="23" x2="26" y2="23" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
      <line x1="12" y1="27" x2="21" y2="27" stroke="currentColor" stroke-width="1.5" stroke-linecap="round"/>
    </svg>`,
    connections: `<svg viewBox="0 0 40 40" fill="none" xmlns="http://www.w3.org/2000/svg">
      <rect x="3"  y="3"  width="16" height="16" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="21" y="3"  width="16" height="16" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="3"  y="21" width="16" height="16" rx="2" stroke="currentColor" stroke-width="2"/>
      <rect x="21" y="21" width="16" height="16" rx="2" stroke="currentColor" stroke-width="2"/>
    </svg>`,
  };

  const GAMES = [
    { id: "sort",        title: "Sort It",       desc: "Place every pattern into its correct category." },
    { id: "match",       title: "Match It",      desc: "Connect each pattern name to its one-sentence description." },
    { id: "name-em",     title: "Name Them All", desc: "Pick a category and name every pattern before time runs out." },
    { id: "quiz",        title: "Quiz",          desc: "Read the description — pick the right pattern from four choices." },
    { id: "flashcards",  title: "Flashcards",    desc: "Flip through cards: name on one side, description on the other." },
    { id: "connections", title: "Connections",   desc: "Find four groups of three use cases that belong to the same design pattern." },
  ];

  function esc(s) {
    return String(s).replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;");
  }
  function shuffle(arr) {
    const a = [...arr];
    for (let i = a.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
  }
  function pick(arr, n) { return shuffle(arr).slice(0, n); }

  // ── Lobby ──────────────────────────────────────────────────────────────────

  function renderLobby() {
    sessionStorage.removeItem("currentGame");
    el.innerHTML = `
      <div class="game-lobby">
        <p class="game-lobby-intro">Test your knowledge of design patterns with these mini-games.</p>
        <div class="game-cards">
          ${GAMES.map(g => `
            <button class="game-card" data-game="${g.id}">
              <span class="game-card-icon">${ICONS[g.id]}</span>
              <span class="game-card-title">${esc(g.title)}</span>
              <span class="game-card-desc">${esc(g.desc)}</span>
            </button>`).join("")}
        </div>
      </div>`;
    el.querySelectorAll(".game-card").forEach(btn =>
      btn.addEventListener("click", () => startGame(btn.dataset.game)));
  }

  // ── Shell ──────────────────────────────────────────────────────────────────

  function startGame(gameId) {
    sessionStorage.setItem("currentGame", gameId);
    const game = GAMES.find(g => g.id === gameId);
    el.innerHTML = `
      <div class="game-arena">
        <div class="game-arena-header">
          <button class="btn-back-lobby">← Games</button>
          <span class="game-arena-title"><span class="game-arena-icon">${ICONS[game.id]}</span>${esc(game.title)}</span>
        </div>
        <div class="game-content" id="game-content"></div>
      </div>`;
    el.querySelector(".btn-back-lobby").addEventListener("click", renderLobby);
    const content = el.querySelector("#game-content");
    ({ sort: gameSortIt, match: gameMatchIt, "name-em": gameNameEm, quiz: gameQuiz, flashcards: gameFlashcards, connections: gameConnections })[gameId](content);
  }

  // ── GAME 1: Sort It ────────────────────────────────────────────────────────

  function gameSortIt(root) {
    const order = shuffle(all);
    const placed = {};  // patternId → categoryId
    let draggingId = null;
    let highlighted = false;

    function makeDraggable(el, pid) {
      el.draggable = true;
      el.addEventListener("dragstart", e => {
        draggingId = pid;
        e.dataTransfer.effectAllowed = "move";
        el.classList.add("dragging");
      });
      el.addEventListener("dragend", () => {
        draggingId = null;
        el.classList.remove("dragging");
        root.querySelectorAll(".sort-column").forEach(c => c.classList.remove("drag-over"));
      });
    }

    function makeDropZone(col, cid) {
      col.addEventListener("dragover", e => {
        if (!draggingId) return;
        e.preventDefault();
        e.dataTransfer.dropEffect = "move";
        root.querySelectorAll(".sort-column").forEach(c => c.classList.remove("drag-over"));
        col.classList.add("drag-over");
      });
      col.addEventListener("dragleave", e => {
        if (!col.contains(e.relatedTarget)) col.classList.remove("drag-over");
      });
      col.addEventListener("drop", e => {
        e.preventDefault();
        col.classList.remove("drag-over");
        if (!draggingId) return;
        placed[draggingId] = cid;
        draggingId = null;
        highlighted = false;
        render();
      });
    }

    // The pool is also a drop zone (to unplace a card)
    function makePoolDropZone(pool) {
      pool.addEventListener("dragover", e => {
        if (!draggingId) return;
        e.preventDefault();
        pool.classList.add("drag-over");
      });
      pool.addEventListener("dragleave", e => {
        if (!pool.contains(e.relatedTarget)) pool.classList.remove("drag-over");
      });
      pool.addEventListener("drop", e => {
        e.preventDefault();
        pool.classList.remove("drag-over");
        if (!draggingId) return;
        delete placed[draggingId];
        draggingId = null;
        highlighted = false;
        render();
      });
    }

    function render() {
      const unplaced = order.filter(p => !placed[p.id]);
      const placedCount = Object.keys(placed).length;
      const correctCount = all.filter(p => placed[p.id] === p.category.id).length;

      root.innerHTML = `
        <p class="game-instructions">Drag each pattern into its category. Drag back to the pool to undo.</p>
        <div class="sort-pool" id="sort-pool">
          ${unplaced.map(p =>
            `<div class="sort-card" data-pid="${p.id}">${esc(p.title)}</div>`
          ).join("") || `<span class="sort-pool-empty">All placed!</span>`}
        </div>
        <div class="sort-columns">
          ${categories.map(cat => `
            <div class="sort-column" data-cid="${cat.id}">
              <div class="sort-col-header">${esc(cat.name)}</div>
              <div class="sort-col-body">
                ${order.filter(p => placed[p.id] === cat.id).map(p =>
                  `<div class="sort-card placed${highlighted ? (p.category.id === cat.id ? " result-correct" : " result-wrong") : ""}" data-pid="${p.id}">${esc(p.title)}</div>`
                ).join("")}
              </div>
            </div>`).join("")}
        </div>
        <div class="sort-actions">
          <button class="btn btn-ghost sort-score-btn"${placedCount === 0 ? " disabled" : ""}>Check Score</button>
          <button class="btn btn-primary sort-highlight-btn"${placedCount === 0 ? " disabled" : ""}>${highlighted ? "Hide Highlights" : "Check Answers with Highlight"}</button>
          <button class="btn btn-ghost sort-again-btn"${placedCount === 0 ? " disabled" : ""}>Reset</button>
        </div>
        <div class="sort-score-display" id="sort-score-display" style="display:none">
          <span class="sort-result-score"></span>
        </div>`;

      root.querySelectorAll(".sort-card").forEach(card => {
        makeDraggable(card, card.dataset.pid);
      });
      root.querySelectorAll(".sort-column").forEach(col => {
        makeDropZone(col, col.dataset.cid);
      });
      makePoolDropZone(root.querySelector("#sort-pool"));

      root.querySelector(".sort-score-btn")?.addEventListener("click", () => {
        const display = root.querySelector("#sort-score-display");
        if (display.style.display === "none") {
          display.querySelector(".sort-result-score").textContent =
            `${correctCount} / ${placedCount} placed correctly`;
          display.style.display = "";
        } else {
          display.style.display = "none";
        }
      });

      root.querySelector(".sort-highlight-btn")?.addEventListener("click", () => {
        highlighted = !highlighted;
        render();
        if (highlighted) {
          const display = root.querySelector("#sort-score-display");
          if (display) {
            display.querySelector(".sort-result-score").textContent =
              `${correctCount} / ${placedCount} placed correctly`;
            display.style.display = "";
          }
        }
      });

      root.querySelector(".sort-again-btn")?.addEventListener("click", () => {
        Object.keys(placed).forEach(k => delete placed[k]);
        highlighted = false;
        render();
      });
    }
    render();
  }

  // ── GAME 2: Match It ───────────────────────────────────────────────────────

  function gameMatchIt(root) {
    let totalMatched = 0;

    function newRound() {
      const sample = pick(all, 6);
      const overviews = shuffle(sample.map(p => ({ id: p.id, text: p.overview })));
      const conns = {};   // patternId → overviewId
      let checked = false;
      let drawing = null; // { pid, cx, cy } — in-progress line

      function getWrap() { return root.querySelector(".match-grid-wrap"); }
      function getSvg()  { return root.querySelector("#match-svg"); }

      function edgeOf(el, side) {
        const w = getWrap().getBoundingClientRect();
        const r = el.getBoundingClientRect();
        return {
          x: (side === "right" ? r.right : r.left) - w.left,
          y: r.top + r.height / 2 - w.top,
        };
      }

      function redrawSvg(curX, curY) {
        const s = getSvg();
        if (!s) return;
        const lines = [];

        // Permanent connection lines
        for (const [pid, ovId] of Object.entries(conns)) {
          const nameEl = root.querySelector(`.match-name[data-id="${pid}"]`);
          const ovEl   = root.querySelector(`.match-ov[data-id="${ovId}"]`);
          if (!nameEl || !ovEl) continue;
          const start = edgeOf(nameEl, "right");
          const end   = edgeOf(ovEl, "left");
          const color = !checked ? "#999" : pid === ovId ? "#28a745" : "#dc3545";
          const width = checked ? 3 : 2.5;
          lines.push(`<line x1="${start.x}" y1="${start.y}" x2="${end.x}" y2="${end.y}" stroke="${color}" stroke-width="${width}" stroke-linecap="round"/>`);
        }

        // In-progress dashed line
        if (drawing && curX !== undefined) {
          lines.push(`<line x1="${drawing.cx}" y1="${drawing.cy}" x2="${curX}" y2="${curY}" stroke="#1a1a1a" stroke-width="2" stroke-linecap="round" stroke-dasharray="6 3"/>`);
        }

        s.innerHTML = lines.join("");
      }

      function renderCards() {
        const reverseConns = Object.fromEntries(Object.entries(conns).map(([k,v]) => [v, k]));
        const connCount = Object.keys(conns).length;

        root.innerHTML = `
          <p class="game-instructions">Draw a line from each name to its description.</p>
          <div class="match-grid-wrap">
            <svg class="match-svg" id="match-svg"></svg>
            <div class="match-grid">
              <div class="match-col" id="match-names">
                ${sample.map(p => {
                  const cls = ["match-name"];
                  if (conns[p.id]) cls.push("connected");
                  if (checked) cls.push(conns[p.id] === p.id ? "correct" : "wrong");
                  return `<div class="${cls.join(" ")}" data-id="${p.id}">${esc(p.title)}</div>`;
                }).join("")}
              </div>
              <div class="match-col" id="match-ovs">
                ${overviews.map(o => {
                  const cls = ["match-ov"];
                  if (reverseConns[o.id]) cls.push("connected");
                  if (checked && reverseConns[o.id]) cls.push(reverseConns[o.id] === o.id ? "correct" : "wrong");
                  return `<div class="${cls.join(" ")}" data-id="${o.id}">${esc(o.text)}</div>`;
                }).join("")}
              </div>
            </div>
          </div>
          <div class="match-actions">
            <button class="btn btn-primary" id="btn-check-conns"${connCount === 0 ? " disabled" : ""}>Check Connections</button>
${checked ? `<button class="btn btn-ghost" id="btn-next-round">Next round →</button>` : ""}
          </div>`;

        redrawSvg();

        root.querySelectorAll(".match-name").forEach(nameEl => {
          nameEl.addEventListener("mousedown", e => {
            e.preventDefault();
            const start = edgeOf(nameEl, "right");
            drawing = { pid: nameEl.dataset.id, cx: start.x, cy: start.y };
            nameEl.classList.add("active");

            function onMove(e) {
              const w = getWrap().getBoundingClientRect();
              const cx = e.clientX - w.left;
              const cy = e.clientY - w.top;
              root.querySelectorAll(".match-ov").forEach(o => o.classList.remove("drag-over"));
              document.elementFromPoint(e.clientX, e.clientY)?.closest(".match-ov")?.classList.add("drag-over");
              redrawSvg(cx, cy);
            }

            function onUp(e) {
              document.removeEventListener("mousemove", onMove);
              document.removeEventListener("mouseup", onUp);
              nameEl.classList.remove("active");
              root.querySelectorAll(".match-ov").forEach(o => o.classList.remove("drag-over"));

              const target = document.elementFromPoint(e.clientX, e.clientY)?.closest(".match-ov");
              if (target && drawing) {
                const pid = drawing.pid;
                const ovId = target.dataset.id;
                // Remove any existing reverse connection to this overview
                for (const [k, v] of Object.entries(conns)) {
                  if (v === ovId) delete conns[k];
                }
                conns[pid] = ovId;
                checked = false;
              }
              drawing = null;
              renderCards();
            }

            document.addEventListener("mousemove", onMove);
            document.addEventListener("mouseup", onUp);
          });
        });

        root.querySelector("#btn-check-conns")?.addEventListener("click", () => {
          checked = true;
          renderCards();
        });
root.querySelector("#btn-next-round")?.addEventListener("click", newRound);
      }

      renderCards();
    }
    newRound();
  }

  // ── GAME 3: Name Them All ──────────────────────────────────────────────────

  function gameNameEm(root) {
    root.innerHTML = `
      <p class="game-instructions">Choose a category, then name every pattern in it before time runs out.</p>
      <div class="name-em-picker">
        <button class="btn btn-ghost name-em-cat" data-cid="__all__">All</button>
        ${categories.map(cat => `<button class="btn btn-ghost name-em-cat" data-cid="${cat.id}">${esc(cat.name)}</button>`).join("")}
      </div>`;
    root.querySelectorAll(".name-em-cat").forEach(btn =>
      btn.addEventListener("click", () => startRound(btn.dataset.cid)));

    function startRound(catId) {
      const cat = catId === "__all__" ? { name: "All" } : categories.find(c => c.id === catId);
      const targets = catId === "__all__" ? all.slice() : all.filter(p => p.category.id === catId);
      const found = new Set();
      let elapsed = 0;
      let done = false;
      let interval = null;

      function fmtTime(s) {
        const m = Math.floor(s / 60);
        const sec = s % 60;
        return m > 0 ? `${m}m ${sec}s` : `${sec}s`;
      }

      function render() {
        root.innerHTML = `
          <div class="name-em-header">
            ${!done ? `<button class="btn btn-ghost name-em-stop">Cancel</button>` : ""}
            <span class="name-em-cat-label">${esc(cat.name)}</span>
            <span class="name-em-timer">${fmtTime(elapsed)}</span>
            <span class="name-em-score">${found.size} / ${targets.length}</span>
          </div>
          <input class="name-em-input" type="text" placeholder="Type a pattern name and press Enter…"
            autocomplete="off" ${done ? "disabled" : ""}>
          <div class="name-em-list">
            ${targets.map(p => `
              <div class="name-em-item${found.has(p.id) ? " found" : done ? " missed" : ""}">
                ${found.has(p.id) || done ? esc(p.title) : "?"}
              </div>`).join("")}
          </div>
          ${done ? `<div class="name-em-done">
            <span>${found.size === targets.length ? `You got them all in ${fmtTime(elapsed)}!` : `${found.size} / ${targets.length}`}</span>
            <button class="btn btn-ghost" id="btn-change-cat">Change category</button>
            <button class="btn btn-primary" id="btn-retry-cat">Try again</button>
          </div>` : ""}`;

        const input = root.querySelector(".name-em-input");
        if (input && !done) {
          input.focus();
          input.addEventListener("keydown", e => {
            if (e.key !== "Enter") return;
            const val = input.value.trim().toLowerCase();
            input.value = "";
            for (const p of targets) {
              const titleLower = p.title.toLowerCase();
              const titleShort = titleLower.endsWith(" pattern") ? titleLower.slice(0, -8).trimEnd() : titleLower;
              if (!found.has(p.id) && (titleLower === val || titleShort === val)) {
                found.add(p.id);
                if (found.size === targets.length) endGame();
                else render();
                root.querySelector(".name-em-input")?.focus();
                return;
              }
            }
            input.classList.add("shake");
            setTimeout(() => input.classList.remove("shake"), 400);
          });
        }
        root.querySelector(".name-em-stop")?.addEventListener("click", () => {
          clearInterval(interval);
          gameNameEm(root);
        });
        root.querySelector("#btn-change-cat")?.addEventListener("click", () => {
          clearInterval(interval);
          gameNameEm(root);
        });
        root.querySelector("#btn-retry-cat")?.addEventListener("click", () => {
          clearInterval(interval);
          startRound(catId);
        });
      }

      function endGame() {
        done = true;
        clearInterval(interval);
        render();
      }

      interval = setInterval(() => {
        elapsed++;
        const t = root.querySelector(".name-em-timer");
        if (t) t.textContent = fmtTime(elapsed);
      }, 1000);

      render();
    }
  }

  // ── GAME 4: Quiz ──────────────────────────────────────────────────────────

  function gameQuiz(root) {
    const TOTAL = 10;

    function startQuiz() {
      const questions = pick(all, TOTAL);
      let idx = 0;
      let score = 0;

      function renderQ() {
        if (idx >= TOTAL) { renderScore(); return; }
        const correct = questions[idx];
        const choices = shuffle([correct, ...pick(all.filter(p => p.id !== correct.id), 3)]);

        root.innerHTML = `
          <div class="quiz-meta">
            <div class="quiz-progress-bar"><div class="quiz-progress-fill" style="width:${(idx / TOTAL) * 100}%"></div></div>
            <span class="quiz-count">${idx + 1} / ${TOTAL}</span>
          </div>
          <div class="quiz-card">
            <p class="quiz-category-tag">${esc(correct.category.name)}</p>
            <p class="quiz-overview">${esc(correct.overview)}</p>
            <div class="quiz-choices">
              ${choices.map(c => `<button class="quiz-choice" data-id="${c.id}">${esc(c.title)}</button>`).join("")}
            </div>
          </div>`;

        root.querySelectorAll(".quiz-choice").forEach(btn => {
          btn.addEventListener("click", () => {
            root.querySelectorAll(".quiz-choice").forEach(b => b.disabled = true);
            const isRight = btn.dataset.id === correct.id;
            if (isRight) score++;
            root.querySelectorAll(".quiz-choice").forEach(b => {
              if (b.dataset.id === correct.id) b.classList.add("correct");
              else if (b === btn) b.classList.add("wrong");
            });
            setTimeout(() => { idx++; renderQ(); }, 900);
          });
        });
      }

      function renderScore() {
        const pct = Math.round((score / TOTAL) * 100);
        const msg = pct === 100 ? "Perfect score!" : pct >= 80 ? "Great job!" : pct >= 50 ? "Keep practising!" : "Keep at it!";
        root.innerHTML = `
          <div class="score-screen">
            <div class="score-ring">${score}<span>/${TOTAL}</span></div>
            <div class="score-pct">${pct}%</div>
            <div class="score-msg">${msg}</div>
            <button class="btn btn-primary" id="btn-retry-quiz">Play again</button>
          </div>`;
        root.querySelector("#btn-retry-quiz").addEventListener("click", startQuiz);
      }

      renderQ();
    }
    startQuiz();
  }

  // ── GAME 5: Flashcards ─────────────────────────────────────────────────────

  function gameFlashcards(root) {
    let catFilter = null;
    let cards = [];
    let idx = 0;
    let flipped = false;

    function getCards() {
      return shuffle(catFilter ? all.filter(p => p.category.id === catFilter) : all);
    }

    function restart() { cards = getCards(); idx = 0; flipped = false; renderCard(); }

    function renderCard() {
      if (!cards.length) {
        root.innerHTML = `<p class="game-instructions" style="text-align:center;padding:2rem">No patterns in this category yet.</p>`;
        return;
      }
      const card = cards[idx];

      root.innerHTML = `
        <div class="fc-filter-bar">
          <button class="btn btn-ghost fc-filter${!catFilter ? " active" : ""}" data-cid="">All</button>
          ${categories.map(c =>
            `<button class="btn btn-ghost fc-filter${catFilter === c.id ? " active" : ""}" data-cid="${c.id}">${esc(c.name)}</button>`
          ).join("")}
          <span class="fc-progress">${idx + 1} / ${cards.length}</span>
        </div>
        <div class="fc-card-wrap">
          <div class="fc-card${flipped ? " flipped" : ""}" id="fc-card" tabindex="0">
            <div class="fc-face fc-front">
              <span class="fc-label">Pattern</span>
              <span class="fc-main">${esc(card.title)}</span>
              <span class="fc-hint">click to reveal</span>
            </div>
            <div class="fc-face fc-back">
              <span class="fc-label">${esc(card.category.name)}</span>
              <span class="fc-main">${esc(card.overview)}</span>
              <span class="fc-hint">click to hide</span>
            </div>
          </div>
        </div>
        <div class="fc-nav">
          <button class="btn btn-ghost" id="fc-prev" ${idx === 0 ? "disabled" : ""}>← Prev</button>
          <button class="btn btn-ghost" id="fc-next" ${idx === cards.length - 1 ? "disabled" : ""}>Next →</button>
          <button class="btn btn-ghost" id="fc-shuffle" style="margin-left:auto">Shuffle ↺</button>
        </div>`;

      root.querySelector("#fc-card").addEventListener("click", () => {
        flipped = !flipped;
        root.querySelector("#fc-card").classList.toggle("flipped", flipped);
      });
      root.querySelector("#fc-prev")?.addEventListener("click", () => { idx--; flipped = false; renderCard(); });
      root.querySelector("#fc-next")?.addEventListener("click", () => { idx++; flipped = false; renderCard(); });
      root.querySelector("#fc-shuffle")?.addEventListener("click", restart);
      root.querySelectorAll(".fc-filter").forEach(btn => {
        btn.addEventListener("click", () => { catFilter = btn.dataset.cid || null; restart(); });
      });
    }

    restart();
  }

  // ── GAME 6: Connections ────────────────────────────────────────────────────

  function gameConnections(root) {
    const eligible = all.filter(p => p.useCases && p.useCases.length >= 3);
    if (eligible.length < 4) {
      root.innerHTML = `<p>Not enough patterns with use cases to play.</p>`;
      return;
    }

    function buildTileLabel(text, kind) {
      const prefix = kind === "example"
        ? `<strong>Example Use:</strong> `
        : `<strong>Use Case:</strong> `;
      return prefix + esc(text);
    }

    function newRound() {
      const chosen = pick(eligible, 4);
      const groups = chosen.map(p => {
        const ucEntries  = shuffle(p.useCases   || []).map(t => ({ text: t, kind: "usecase" }));
        const euEntries  = shuffle(p.exampleUses || []).map(t => ({ text: t, kind: "example" }));
        const combined   = shuffle([...ucEntries, ...euEntries]).slice(0, 3);
        return { patternId: p.id, title: p.title, entries: combined };
      });
      const tiles = shuffle(
        groups.flatMap(g => g.entries.map(e => ({ ...e, patternId: g.patternId })))
      );
      let selected = [];
      let solved   = [];
      let mistakes = 0;

      function showToast(msg) {
        const existing = root.querySelector(".conn-toast");
        if (existing) existing.remove();
        const t = document.createElement("div");
        t.className = "conn-toast";
        t.textContent = msg;
        root.appendChild(t);
        requestAnimationFrame(() => t.classList.add("conn-toast-show"));
        setTimeout(() => { t.classList.remove("conn-toast-show"); setTimeout(() => t.remove(), 300); }, 900);
      }

      function render() {
        const remaining = tiles.filter(t => !solved.includes(t.patternId));
        root.innerHTML = `
          <div class="conn-game-wrap">
          <p class="game-instructions">Find four groups of three — each group's use cases belong to the same design pattern.</p>
          <div class="conn-solved">
            ${groups.filter(g => solved.includes(g.patternId)).map(g => {
              const colorIdx = groups.indexOf(g);
              return `<div class="conn-group-revealed conn-color-${colorIdx}">
                <strong>${esc(g.title)}</strong>
                <ul>${g.entries.map(e => `<li>${buildTileLabel(e.text, e.kind)}</li>`).join("")}</ul>
              </div>`;
            }).join("")}
          </div>
          <div class="conn-grid">
            ${remaining.map((t, i) => {
              const colorIdx = groups.findIndex(g => g.patternId === t.patternId);
              const isSelected = selected.includes(i);
              return `<button class="conn-tile${isSelected ? " conn-selected" : ""} conn-hint-${colorIdx}" data-idx="${i}">
                ${buildTileLabel(t.text, t.kind)}
              </button>`;
            }).join("")}
          </div>
          <div class="conn-footer">
            <span class="conn-mistakes">${"○".repeat(mistakes)}${"●".repeat(Math.max(0, 4 - mistakes))}</span>
            <button class="btn btn-ghost conn-deselect"${selected.length === 0 ? " disabled" : ""}>Deselect all</button>
            <button class="btn btn-primary conn-submit"${selected.length !== 3 ? " disabled" : ""}>Submit</button>
          </div>
          </div>`;

        root.querySelectorAll(".conn-tile").forEach(btn => {
          const idx = +btn.dataset.idx;
          btn.addEventListener("click", () => {
            if (selected.includes(idx)) {
              selected = selected.filter(i => i !== idx);
            } else if (selected.length < 3) {
              selected.push(idx);
            }
            render();
          });
        });

        root.querySelector(".conn-deselect")?.addEventListener("click", () => { selected = []; render(); });

        root.querySelector(".conn-submit")?.addEventListener("click", () => {
          if (selected.length !== 3) return;
          const remaining = tiles.filter(t => !solved.includes(t.patternId));
          const selectedTiles = selected.map(i => remaining[i]);
          const pid = selectedTiles[0].patternId;
          if (selectedTiles.every(t => t.patternId === pid)) {
            solved.push(pid);
            selected = [];
            if (solved.length === 4) { renderEnd(true); } else { render(); }
          } else {
            mistakes++;
            // Figure out which patterns are in the wrong mix to give a hint
            const pids = [...new Set(selectedTiles.map(t => t.patternId))];
            const names = pids.map(id => groups.find(g => g.patternId === id)?.title).filter(Boolean);
            showToast("Not quite — try again!");
            root.querySelectorAll(".conn-tile.conn-selected").forEach(btn => {
              btn.classList.add("conn-shake");
              setTimeout(() => btn.classList.remove("conn-shake"), 500);
            });
            setTimeout(() => {
              selected = [];
              if (mistakes >= 4) { renderEnd(false); } else { render(); }
            }, 600);
          }
        });
      }

      function renderEnd(won) {
        root.innerHTML = `
          <div class="conn-game-wrap">
          ${won
            ? `<p class="conn-result-msg">Solved with ${mistakes} mistake${mistakes !== 1 ? "s" : ""}!</p>`
            : `<p class="conn-result-msg conn-result-fail">Too many mistakes — here are the answers:</p>`}
          <div class="conn-solved">
            ${groups.map((g, i) => `
              <div class="conn-group-revealed conn-color-${i}">
                <strong>${esc(g.title)}</strong>
                <ul>${g.entries.map(e => `<li>${buildTileLabel(e.text, e.kind)}</li>`).join("")}</ul>
              </div>`).join("")}
          </div>
          <div style="text-align:center;margin-top:1.25rem">
            <button class="btn btn-primary conn-again">Play again</button>
          </div>
          </div>`;
        root.querySelector(".conn-again").addEventListener("click", newRound);
      }

      render();
    }

    newRound();
  }

  const saved = sessionStorage.getItem("currentGame");
  if (saved && GAMES.find(g => g.id === saved)) {
    startGame(saved);
    document.querySelector('.tab-btn[data-tab="tab-games"]')?.click();
  } else renderLobby();
}
