# Design patterns — what they are, when NOT to use them

At lead level, the question isn't "can you implement Singleton?" — it's "when would you reach for one, and what would you build instead?"

## Catalogue (the ones interviewers actually ask)

### Creational

| Pattern | One-line | When to use | When NOT to |
| --- | --- | --- | --- |
| Singleton | One instance per JVM | Stateless registries, cross-cutting tools | When testability matters — use DI instead |
| Factory Method | Subclass decides the concrete type | Variants on a family of types | When a static method suffices |
| Abstract Factory | Factory of factories | Cross-cutting product families (UI toolkits) | Rarely — overkill in modern apps |
| Builder | Fluent immutable construction | Many optional fields, validation rules | When records or a 3-arg constructor suffice |

### Structural

| Pattern | One-line | When to use | When NOT to |
| --- | --- | --- | --- |
| Adapter | Wrap one API to look like another | Bridging legacy or third-party types | When you can change the underlying API |
| Decorator | Stack behaviour without subclassing | Cross-cutting features layered on a core | When AOP / interceptors fit better |
| Facade | Simplified front to a complex subsystem | Library-level public API | When the subsystem is already small |
| Composite | Treat individuals and groups uniformly | Recursive trees (file systems, UI) | When the tree is flat |
| Proxy | Stand-in that controls access | Lazy loading, security, remoting | When you can use a Supplier or a direct check |

### Behavioural

| Pattern | One-line | When to use | When NOT to |
| --- | --- | --- | --- |
| Strategy | Pluggable algorithm | Multiple ways to do the same thing | When the choices are stable and few |
| Observer | One-to-many notification | Event-driven UIs, pub/sub | Prefer reactive streams or a real broker |
| Template Method | Skeleton with hookable steps | Algorithm with variation points | When composition is cleaner |
| Iterator | Walk a collection without exposing it | Custom collection types | Built-in `Iterable` covers most cases |
| Command | Action as an object | Undo/redo, request queues, scheduled jobs | When a method reference suffices |
| State | Per-state behaviour as objects | Complex state machines | When an enum-with-methods is enough |
| Chain of Responsibility | Pass request along handlers | Filter chains (Spring Security), middleware | When sequential calls are clear |

## Patterns the language ate

- **Iterator** — `Iterable` + enhanced for-loop.
- **Strategy** — pass a `Function<T, R>` or `Comparator<T>`.
- **Observer** — `PropertyChangeListener` is legacy; modern alternatives are `Flow.Publisher` or a real event bus.
- **Command** — a `Runnable` or `Supplier`.
- **Factory** — a `Supplier<T>` constructor reference.

## When NOT to use a pattern

- The pattern adds an abstraction the code doesn't have. Don't wrap two if-branches in a Strategy "in case we need a third later."
- You're solving a one-off problem. Patterns earn their keep across multiple consumers; for a single caller, just write the code.
- The pattern is fighting the language. Singleton in Java means re-inventing static or wiring a DI bean — the pattern itself isn't the lesson.

## Java-specific notes

- **Enum singleton** is the canonical thread-safe singleton (Bloch). Lazy initialisation is rarely needed.
- **Builder via inner class** is the textbook form; Lombok's `@Builder` and records-with-canonical-constructors often replace it.
- **Spring `@Configuration`** is essentially an Abstract Factory; you rarely hand-roll the GoF version in Spring apps.

## Canned answers

> **"What's the cleanest Singleton in Java?"**
>
> The enum singleton — `public enum X { INSTANCE; }`. Thread-safe by classloader semantics, serialisation-safe, reflection-safe, no double-checked-locking gymnastics. Use it when you genuinely need a single instance per JVM; otherwise rely on your DI container.

> **"Strategy vs Template Method?"**
>
> Strategy = the WHOLE algorithm is swappable; the caller composes the strategy at runtime. Template Method = the skeleton is fixed, only specific steps are swappable, usually via subclassing. In modern Java, Strategy is almost always preferable because you can pass a lambda; Template Method couples you to inheritance.

## References

- GoF *Design Patterns* (1994) — original.
- *Effective Java* — Items 1 (factory methods), 2 (builders), 3 (enum singleton), 17 (immutability).

## In this package

```
designpatterns/
├── README.md
├── DesignPatternsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── BuilderPattern.java
    ├── EnumSingleton.java
    └── StrategyAsLambda.java
```
