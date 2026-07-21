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
  };

  const GAMES = [
    { id: "sort",       title: "Sort It",       desc: "Place every pattern into its correct category." },
    { id: "match",      title: "Match It",      desc: "Connect each pattern name to its one-sentence description." },
    { id: "name-em",    title: "Name Them All", desc: "Pick a category and name every pattern before time runs out." },
    { id: "quiz",       title: "Quiz",          desc: "Read the description — pick the right pattern from four choices." },
    { id: "flashcards", title: "Flashcards",    desc: "Flip through cards: name on one side, description on the other." },
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
    ({ sort: gameSortIt, match: gameMatchIt, "name-em": gameNameEm, quiz: gameQuiz, flashcards: gameFlashcards })[gameId](content);
  }

  // ── GAME 1: Sort It ────────────────────────────────────────────────────────

  function gameSortIt(root) {
    const order = shuffle(all);
    const placed = {};   // patternId → categoryId
    let selected = null;

    function render() {
      const unplaced = order.filter(p => !placed[p.id]);
      const allPlaced = Object.keys(placed).length === all.length;

      root.innerHTML = `
        <p class="game-instructions">Click a pattern to select it, then click a category column to place it there.
          Click a placed card to return it to the pool.</p>
        <div class="sort-pool" id="sort-pool">
          ${unplaced.map(p =>
            `<button class="sort-card${selected === p.id ? " selected" : ""}" data-pid="${p.id}">${esc(p.title)}</button>`
          ).join("") || `<span class="sort-pool-empty">All placed — now check your answers!</span>`}
        </div>
        <div class="sort-columns">
          ${categories.map(cat => `
            <div class="sort-column" data-cid="${cat.id}">
              <div class="sort-col-header">${esc(cat.name)}</div>
              <div class="sort-col-body">
                ${all.filter(p => placed[p.id] === cat.id).map(p =>
                  `<button class="sort-card placed" data-pid="${p.id}">${esc(p.title)}</button>`
                ).join("")}
              </div>
            </div>`).join("")}
        </div>
        ${allPlaced ? `<button class="btn btn-primary sort-check-btn">Check answers</button>` : ""}`;

      root.querySelectorAll(".sort-card:not(.placed)").forEach(btn => {
        btn.addEventListener("click", e => {
          e.stopPropagation();
          selected = selected === btn.dataset.pid ? null : btn.dataset.pid;
          render();
        });
      });

      root.querySelectorAll(".sort-card.placed").forEach(btn => {
        btn.addEventListener("click", e => {
          e.stopPropagation();
          delete placed[btn.dataset.pid];
          if (selected === btn.dataset.pid) selected = null;
          render();
        });
      });

      root.querySelectorAll(".sort-column").forEach(col => {
        col.addEventListener("click", () => {
          if (!selected) return;
          placed[selected] = col.dataset.cid;
          selected = null;
          render();
        });
      });

      root.querySelector(".sort-check-btn")?.addEventListener("click", () => {
        const correct = all.filter(p => placed[p.id] === p.category.id).length;
        // colour cards
        root.querySelectorAll(".sort-card.placed").forEach(btn => {
          const p = all.find(x => x.id === btn.dataset.pid);
          btn.classList.add(placed[p.id] === p.category.id ? "result-correct" : "result-wrong");
        });
        root.querySelector(".sort-check-btn").replaceWith((() => {
          const div = document.createElement("div");
          div.className = "sort-result-row";
          div.innerHTML = `<span class="sort-result-score">${correct} / ${all.length} correct</span>
            <button class="btn btn-ghost">Play again</button>`;
          div.querySelector("button").addEventListener("click", () => {
            Object.keys(placed).forEach(k => delete placed[k]);
            selected = null;
            render();
          });
          return div;
        })());
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
      const matched = new Set();
      let selectedId = null;
      let wrongPair = null;
      let wrongTimeout = null;

      function render() {
        root.innerHTML = `
          <p class="game-instructions">Click a name, then click its matching description. Matched: ${totalMatched}</p>
          <div class="match-grid">
            <div class="match-col">
              ${sample.map(p => `
                <button class="match-name ${matched.has(p.id) ? "matched" : ""} ${selectedId === p.id ? "selected" : ""} ${wrongPair === p.id + ":name" ? "wrong" : ""}"
                  data-id="${p.id}">${esc(p.title)}</button>`).join("")}
            </div>
            <div class="match-col">
              ${overviews.map(o => `
                <button class="match-ov ${matched.has(o.id) ? "matched" : ""} ${wrongPair === o.id + ":ov" ? "wrong" : ""}"
                  data-id="${o.id}">${esc(o.text)}</button>`).join("")}
            </div>
          </div>
          ${matched.size === sample.length
            ? `<div class="match-round-done"><span>Round complete!</span><button class="btn btn-primary" id="btn-next-round">Next round →</button></div>`
            : ""}`;

        root.querySelectorAll(".match-name:not(.matched)").forEach(btn => {
          btn.addEventListener("click", () => { selectedId = btn.dataset.id; render(); });
        });
        root.querySelectorAll(".match-ov:not(.matched)").forEach(btn => {
          btn.addEventListener("click", () => {
            if (!selectedId) return;
            clearTimeout(wrongTimeout);
            if (selectedId === btn.dataset.id) {
              matched.add(selectedId);
              totalMatched++;
              selectedId = null;
              wrongPair = null;
            } else {
              wrongPair = selectedId + ":name";
              const prevSel = selectedId;
              selectedId = null;
              wrongTimeout = setTimeout(() => { wrongPair = null; render(); }, 700);
            }
            render();
          });
        });
        root.querySelector("#btn-next-round")?.addEventListener("click", newRound);
      }
      render();
    }
    newRound();
  }

  // ── GAME 3: Name Them All ──────────────────────────────────────────────────

  function gameNameEm(root) {
    root.innerHTML = `
      <p class="game-instructions">Choose a category, then name every pattern in it before time runs out.</p>
      <div class="name-em-picker">
        ${categories.map(cat => `<button class="btn btn-ghost name-em-cat" data-cid="${cat.id}">${esc(cat.name)}</button>`).join("")}
      </div>`;
    root.querySelectorAll(".name-em-cat").forEach(btn =>
      btn.addEventListener("click", () => startRound(btn.dataset.cid)));

    function startRound(catId) {
      const cat = categories.find(c => c.id === catId);
      const targets = all.filter(p => p.category.id === catId);
      const found = new Set();
      let timeLeft = 90;
      let done = false;
      let interval = null;

      function render() {
        root.innerHTML = `
          <div class="name-em-header">
            <span class="name-em-cat-label">${esc(cat.name)}</span>
            <span class="name-em-timer${timeLeft <= 15 ? " urgent" : ""}">${timeLeft}s</span>
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
            <span>${found.size === targets.length ? "You got them all!" : `${found.size} / ${targets.length}`}</span>
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
              if (!found.has(p.id) && p.title.toLowerCase() === val) {
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
        timeLeft--;
        const t = root.querySelector(".name-em-timer");
        if (t) { t.textContent = `${timeLeft}s`; if (timeLeft <= 15) t.classList.add("urgent"); }
        if (timeLeft <= 0) endGame();
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

  renderLobby();
}
