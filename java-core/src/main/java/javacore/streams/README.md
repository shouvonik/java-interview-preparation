# Streams — Stream API, lambdas, collectors

Streams are about **expressing what you want, not how**. Lazy, composable, often terminal-driven.

## Stream vs Collection

- A **Collection** holds elements.
- A **Stream** describes a pipeline of computations over elements; nothing happens until a terminal operation runs.

## Intermediate vs terminal

| Intermediate (lazy) | Terminal (eager) |
| --- | --- |
| `filter`, `map`, `flatMap`, `distinct`, `sorted`, `peek`, `limit`, `skip` | `forEach`, `toList`/`toArray`/`collect`, `reduce`, `count`, `min`/`max`, `findFirst`, `anyMatch` |

Pipelines fuse: `list.stream().filter(p).map(f).count()` walks the source ONCE.

## Functional interfaces

| Interface | Signature | Use |
| --- | --- | --- |
| `Function<T, R>` | `R apply(T)` | transform |
| `Predicate<T>` | `boolean test(T)` | filter |
| `Consumer<T>` | `void accept(T)` | side-effect |
| `Supplier<T>` | `T get()` | lazy provider |
| `BiFunction<T, U, R>` | `R apply(T, U)` | combine two |
| `UnaryOperator<T>` | `T apply(T)` | T-to-T |

Primitive specialisations: `IntFunction`, `ToIntFunction`, etc. — avoid boxing.

## Method references

```java
list.stream().map(String::toUpperCase);             // instance method
list.stream().map(Integer::parseInt);               // static method
list.stream().map(this::transform);                 // bound to instance
list.stream().map(MyClass::new);                    // constructor ref
```

## Collectors — the toolkit

```java
.collect(Collectors.toList())                       // mutable list (or .toList() for unmodifiable, 16+)
.collect(Collectors.toMap(User::id, Function.identity()))
.collect(Collectors.groupingBy(User::department))
.collect(Collectors.partitioningBy(User::isActive))
.collect(Collectors.joining(", ", "[", "]"))
.collect(Collectors.summingInt(User::age))
```

Downstream collectors compose: `groupingBy(User::dept, counting())`.

## Parallel streams — when (almost) never

`parallelStream()` looks easy but trips lead-level traps:
- Uses the **common ForkJoinPool** — shared with the rest of the JVM. One blocking call starves everyone.
- Splitting cost is high for non-array sources (LinkedList, Stream.iterate).
- Order-dependent ops (`forEachOrdered`, `findFirst`) lose most of the parallel benefit.
- Race conditions if your accumulator isn't thread-safe.

Default to sequential. Use parallel only for CPU-bound work over an array of ≥ ~10k elements.

## Common pitfalls

- **`Collectors.toMap` throws on duplicate keys** — pass a merge function `(a, b) -> a` to keep first.
- **Side effects in `map`/`filter`** — pipelines may be re-evaluated; keep these pure.
- **Reusing a Stream** — IllegalStateException; streams are single-use.
- **Boxing in numeric reductions** — use `mapToInt`, `mapToLong` for primitives.
- **`peek` for side effects in a production pipeline** — it may be skipped by JIT if the result is unused (Java 9+).

## Canned answers

> **"What's the difference between `map` and `flatMap`?"**
>
> `map` is one-to-one: `Stream<T>` → `Stream<R>`. `flatMap` is one-to-many followed by concatenation: each element maps to its own stream, and those streams are flattened into a single output stream. Use `flatMap` when each element produces a collection — e.g. expanding orders into line items.

> **"When would you use parallel streams?"**
>
> Rarely. They use the common ForkJoinPool — one blocking I/O call inside a parallel stream starves the whole JVM. Even for CPU-bound work, the source needs to split cheaply (arrays good; linked lists bad) and the work per element needs to outweigh the splitting overhead. For a typical service, prefer sequential streams and explicit `ExecutorService` if you really need parallelism.

## In this package

```
streams/
├── README.md
├── StreamsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── CollectorsCatalog.java
    └── ParallelStreamPitfalls.java
```
