# Modern features — Java 17 → 26 highlights

A working knowledge of post-Java-8 features signals you've been on a current codebase. Interviewers ask "have you used records / sealed / pattern matching?" expecting yes.

## Records (Java 14 preview, 16 stable)

```java
public record Point(int x, int y) {
    public Point {                                 // compact constructor
        if (x < 0 || y < 0) throw new IllegalArgumentException();
    }
}
```

What you get for free:
- `final` fields, no setters.
- Canonical constructor matching the components.
- `equals` / `hashCode` / `toString` over the components.
- Accessors named after the components (`p.x()`, not `p.getX()`).

What you DON'T get:
- Inheritance — records implicitly extend `java.lang.Record` and can't extend anything else.
- Mutability — they're shallowly immutable.

**Use for:** DTOs, value objects, tuples, intermediate results in streams.

## Sealed classes (17)

```java
public sealed interface Shape permits Circle, Square, Triangle {}
public record Circle(double r) implements Shape {}
public record Square(double side) implements Shape {}
public record Triangle(double base, double height) implements Shape {}
```

- The set of subtypes is FIXED at compile time.
- Enables exhaustive switch over the hierarchy — compiler knows you covered all permits.

## Pattern matching for `instanceof` (16)

```java
if (shape instanceof Circle c) {
    return Math.PI * c.r() * c.r();
}
```

No explicit cast. The variable is scoped to where the check is true.

## Switch expressions + patterns (14 / 21)

```java
double area = switch (shape) {
    case Circle c   -> Math.PI * c.r() * c.r();
    case Square s   -> s.side() * s.side();
    case Triangle t -> 0.5 * t.base() * t.height();
};
// no default needed because Shape is sealed -> exhaustive
```

Pattern matching for switch (21) also supports record patterns:

```java
double area = switch (shape) {
    case Circle(double r)            -> Math.PI * r * r;
    case Square(double s)            -> s * s;
    case Triangle(double b, double h) -> 0.5 * b * h;
};
```

## Text blocks (15)

```java
String json = """
    {
      "name": "Alice"
    }
    """;
```

Strips indentation relative to the closing `"""`.

## `var` — local-variable type inference (10)

```java
var list = new ArrayList<String>();     // inferred as ArrayList<String>
var stream = list.stream().filter(...); // inferred
for (var entry : map.entrySet()) { ... }
```

Use `var` when:
- The right-hand side makes the type obvious.
- The verbose explicit type adds no clarity (`Map<String, List<UserPreference>> ...`).

Don't use `var` when the type clarifies intent and the RHS doesn't (`var x = service.process(...)` — what type is x?).

## Virtual threads (21) — see [concurrency/](../concurrency/README.md)

## Other 21+ niceties

- **Sequenced collections** — `SequencedCollection`, `SequencedSet`, `SequencedMap` interfaces unify the "first/last" API across lists, deques, and ordered maps.
- **Unnamed patterns and variables** (21 preview, 22 stable) — `case Circle(_, _) -> ...` and `var _ = ...`.
- **Stream.gatherers** (22 preview, 24 stable) — custom intermediate ops.
- **Scoped values** (21 preview) — better alternative to ThreadLocal under virtual threads.

## Canned answers

> **"When do you reach for a record?"**
>
> Any immutable value object that's mostly data: DTOs from JSON, query result rows, coordinates, intermediate tuples in stream pipelines. Anywhere you'd otherwise write a class with private final fields, a constructor, accessors, equals, hashCode, and toString — that's a record.

> **"What does sealed buy you?"**
>
> Exhaustive switch + closed hierarchy. The compiler can verify that every variant is handled, and you can't sneak a fourth Shape into the codebase without updating every switch. Sealed + records + switch-with-patterns is the closest Java has to algebraic data types.

## In this package

```
modernfeatures/
├── README.md
├── ModernFeaturesGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── RecordsAndSealed.java
    └── SwitchPatterns.java
```
