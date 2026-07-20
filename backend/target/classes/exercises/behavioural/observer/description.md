# Observer Pattern

The Observer pattern lets a **subject** maintain a list of **observers** and notify them
whenever its state changes — without the subject needing to know anything specific about
its observers.

## When to use it

- Event systems — UI elements reacting to data changes.
- Pub/sub messaging where senders don't know receivers.
- Any situation where multiple objects need to stay in sync with one source of truth.

## Key idea

The subject exposes `subscribe(fn)` and `unsubscribe(fn)`. When state changes, it calls
`notify()`, which loops over every registered observer and invokes it.

## What's wrong with the starter code?

`Store` holds direct references to `Logger` and `UI` and calls their methods explicitly.
Adding a third dependent means editing `Store` — the classes are tightly coupled.

## Goal

Refactor `Store` to support `subscribe(fn)` / `unsubscribe(fn)` and call all registered
observers when `setState()` is invoked. Remove the hard-coded references to `Logger` and `UI`.

The tests should still pass after your refactoring.
