# Generics — bounded types, wildcards, erasure

Generics are about **compile-time** safety; the runtime knows nothing about them. Everything weird about Java generics comes from this.

## Type parameters

```java
public class Container<T> { T value; }                 // class-level
public static <T> List<T> singletonList(T t) { ... }   // method-level
public interface Comparable<T> { int compareTo(T o); } // interface
```

## Bounded types

`<T extends Number>` → T must be Number or a subclass. Multiple bounds with `&`:

```java
public static <T extends Number & Comparable<T>> T max(List<T> list) { ... }
```

The class bound must come first; up to one class bound, multiple interface bounds.

## Wildcards — PECS

**Producer Extends, Consumer Super.**

```java
List<? extends Number> producers; // can READ Number; cannot put anything in
List<? super Integer> consumers;  // can PUT Integer in; reads come back as Object
```

| You want to... | Use |
| --- | --- |
| Read items from a collection | `List<? extends T>` |
| Add items to a collection | `List<? super T>` |
| Both | `List<T>` (invariant) |

```java
public static <T> void copy(List<? super T> dest, List<? extends T> src) {
    for (T t : src) dest.add(t);
}
```

## Type erasure

At runtime, `List<String>` and `List<Integer>` are both just `List`. The compiler:
- Verifies generic constraints at the call site.
- Inserts casts as needed in compiled bytecode.
- Erases the type parameters in the class file (with bridge methods for inheritance).

### Consequences

- **No generic arrays**: `new T[10]` doesn't compile. `(T[]) new Object[10]` does, but with unchecked warning.
- **No `instanceof T`**: only raw `instanceof List`.
- **No method overloading by type parameter**: `void foo(List<String>)` and `void foo(List<Integer>)` clash — same erased signature.
- **No `T.class`**: the type token must be passed in (`Class<T> clazz`).

## Common pitfalls

- **Raw types** (`List` instead of `List<String>`) — kills generics safety and emits unchecked warnings; only for legacy API interop.
- **`@SuppressWarnings("unchecked")` on whole methods** — narrow to the smallest scope.
- **Returning a `List<? extends T>`** — caller can't add to it; usually you want `List<T>` from a method return.

## Canned answers

> **"Explain PECS."**
>
> Producer Extends, Consumer Super. If a collection is a SOURCE of items (you read from it), declare it `List<? extends T>` — covariance. If it's a SINK (you write to it), declare it `List<? super T>` — contravariance. The mnemonic comes from Joshua Bloch's *Effective Java*.

> **"Why can't you create a generic array `new T[10]`?"**
>
> Type erasure: the JVM doesn't know T at runtime, so it can't fail-fast on writes that violate the type. Arrays are reified (carry their element type at runtime) — mixing them with erasure would silently let bad writes through.

## References

- *Effective Java* — Items 26–33 (entire generics chapter).

## In this package

```
generics/
├── README.md
├── GenericsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── PECSDemo.java
    └── ErasureGotchas.java
```
