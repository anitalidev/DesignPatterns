# Singleton Pattern

The Singleton pattern restricts a class to a **single instance**. Any request for that
class returns the same object every time.

## When to use it

- Shared configuration or settings objects.
- Connection pools, loggers, or caches where duplicate instances waste resources or cause conflicts.
- Anything that should be a single point of truth for the lifetime of the application.

## Key idea

Move the constructor logic behind a static `getInstance()` method. On the first call,
create and store the instance. On every subsequent call, return the stored one.

## What's wrong with the starter code?

`AppConfig` can be instantiated freely with `new AppConfig()`. Each call creates a fresh
object, so two parts of the app can hold different configs — changes made in one place
silently don't affect the other.

## Goal

Refactor `AppConfig` so that `AppConfig.getInstance()` always returns the same object.
Make the constructor `private` so callers cannot use `new AppConfig()` directly.

The tests should still pass after your refactoring.
