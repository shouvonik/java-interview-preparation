# Optionals — when to use, how to use, when not to

`Optional<T>` was designed to be a **return type** for methods that might not have a value, removing the "is this null?" ambiguity.

## What Optional is FOR

- Method return types where absence is a valid outcome: `Optional<User> findById(String id)`.
- Chaining nullable transformations: `findUser(id).map(User::getEmail).filter(...)`.
- Replacing null checks at API boundaries.

## What Optional is NOT for

- **Fields** — wastes memory, breaks serialisation. Use null + a clear contract.
- **Method parameters** — caller can already choose to pass null or call an overload.
- **Collections of Optional** — use empty collections instead.
- **Stuffing inside generics** — `Optional<List<T>>` is almost always wrong; return an empty list.

## Building an Optional

```java
Optional.of(value);        // throws NPE if null
Optional.ofNullable(value); // empty if null — the safe default
Optional.empty();
```

## Reading it back

```java
opt.isPresent()                // boolean — prefer ifPresent / orElse to direct .get()
opt.get()                      // throws NoSuchElementException if empty — avoid except in tests
opt.orElse(defaultVal)         // EAGER evaluation of the default
opt.orElseGet(() -> compute()) // LAZY — only computes if empty
opt.orElseThrow()              // Java 10+
opt.orElseThrow(() -> new MyException())
opt.ifPresent(v -> ...)
opt.ifPresentOrElse(v -> ..., () -> ...)  // Java 9+
```

## `orElse` vs `orElseGet` — the lead-level trap

```java
opt.orElse(loadFromDb());       // loadFromDb() runs EVERY TIME, even if opt has a value
opt.orElseGet(this::loadFromDb); // runs only if opt is empty
```

If the default is a side-effecting or expensive call, use `orElseGet`. If it's a literal or cheap constant, `orElse` is fine.

## Chaining

```java
Optional<String> ipFromHeader = findHeader("X-Forwarded-For")
    .map(this::firstToken)
    .filter(this::isValidIp);
```

- `map` — Function<T, R>, wraps the result back into Optional.
- `flatMap` — Function<T, Optional<R>>, prevents `Optional<Optional<R>>`.
- `filter` — Predicate<T>, returns empty if predicate fails.

## Common pitfalls

- **`get()` without `isPresent()` check** — NoSuchElementException at runtime.
- **`orElse` with an expensive call** — see above.
- **Returning `null` from a method declared `Optional<T>`** — defeats the entire purpose; always return `Optional.empty()`.
- **`Optional.of(maybeNull)`** — throws NPE; use `ofNullable`.

## Canned answers

> **"When should I use Optional?"**
>
> As a return type when absence is a legitimate outcome. Not as a field, not as a parameter, not inside a collection. The goal is making the API contract explicit at the boundary, not threading wrappers throughout the codebase.

> **"orElse vs orElseGet?"**
>
> `orElse(x)` evaluates `x` regardless of whether the Optional is present — fine for constants, dangerous for expensive or side-effecting calls. `orElseGet(supplier)` only invokes the supplier if the Optional is empty. For DB lookups, factory methods, or anything with cost, use `orElseGet`.

## References

- *Effective Java* — Item 55 (Return Optionals judiciously).

## In this package

```
optionals/
├── README.md
├── OptionalsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── OrElseVsOrElseGet.java
    └── ChainingDemo.java
```
