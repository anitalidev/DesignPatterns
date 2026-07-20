# Factory Method Pattern

The Factory Method pattern centralises object creation behind a single function or method.
Callers ask for an object by type; the factory decides which concrete class to instantiate
and return.

## When to use it

- When the exact type to create is determined at runtime.
- When you want to decouple callers from concrete classes so swapping implementations is easy.
- When many places in the code repeat the same `if/else` or `switch` to pick a class.

## Key idea

Replace scattered `new ConcreteClass()` calls with a single `ShapeFactory.create(type)`
(or similar). All the branching lives in one place; callers just ask for what they need.

## What's wrong with the starter code?

Every caller that needs a shape must contain its own `if/else` block. Adding a new shape
type means hunting down and updating every one of those blocks.

## Goal

Create a `ShapeFactory` with a static `create(type, args...)` method that returns the
correct shape object. Each shape should have an `area()` method. Remove (or leave unused)
the scattered branching in the starter code.

The tests should still pass after your refactoring.
