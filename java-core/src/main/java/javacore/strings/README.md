# Strings — immutability, pool, builders

## Why String is immutable

- **Security** — once you've validated a path or URL, no one can mutate it under you.
- **Thread safety** — shareable without synchronisation.
- **Caching** — `hashCode()` is cached after first call.
- **String pool** — interned strings can be shared safely.

## The String pool

```java
String a = "hello";              // pool entry created
String b = "hello";              // same pool entry; a == b
String c = new String("hello");  // NEW Object, NOT pooled; a != c
String d = c.intern();           // returns the pool entry; a == d
```

The pool lives in the heap (Java 7+, was PermGen pre-7). All compile-time string literals get interned automatically. Manually interning lots of strings hurts more than helps — JIT and de-duplication usually do better.

## Concatenation

`a + b` is rewritten by the compiler:
- Pre-Java 9: `new StringBuilder().append(a).append(b).toString()`.
- Java 9+: `invokedynamic` to `StringConcatFactory` — faster, more compact.

In a loop, use `StringBuilder` explicitly to avoid creating a new builder per iteration. The compiler can't always hoist it out.

```java
// BAD — quadratic
String result = "";
for (var s : items) result += s;

// GOOD — linear
StringBuilder sb = new StringBuilder();
for (var s : items) sb.append(s);
String result = sb.toString();
```

## StringBuilder vs StringBuffer

- **StringBuilder** — NOT thread-safe. Use 99% of the time.
- **StringBuffer** — synchronised, thread-safe. Use only when multiple threads write to the same buffer (rare).

The synchronisation cost is small but unnecessary in single-threaded code.

## Common methods

```java
"hello".length()
"hello".charAt(1)              // 'e'
"hello".substring(1, 4)        // "ell"
"hello".indexOf("ll")          // 2
"  hi  ".strip()               // "hi" — Unicode-aware (Java 11+); prefer over .trim()
"hello".split("l")             // [he, , o]
"hello".replace('l', 'L')      // "heLLo"
String.format("Hello %s", x)
String.join(", ", list)
"abc".chars().sum()            // IntStream of code points
```

## Text blocks (Java 15+)

```java
String json = """
    {
      "name": "Alice",
      "age": 30
    }
    """;
```

- Indentation is stripped relative to the closing `"""`.
- Trailing whitespace stripped per line.
- Inside, `"` is literal — no escaping needed.

## Common pitfalls

- **`==` for string equality** — compares references. Always use `.equals()`. (== works for literals because of the pool, but is brittle.)
- **`s.replaceAll(".", "x")`** — `.` is a regex! Use `replace(".", "x")` for literal.
- **Wasting allocations** with `+` in loops.
- **`intern()` everything** — pollutes the pool; usually slower than just letting strings be.

## Canned answers

> **"Why is String immutable?"**
>
> Four reasons: security (no one mutates a validated URL out from under you), thread safety (free to share), hashcode caching (one-time compute), and string pool (interning relies on immutability). The cost is allocation overhead on every "modification" — that's what `StringBuilder` solves.

> **"`s1 == s2` vs `s1.equals(s2)`?"**
>
> `==` compares references; for string literals that share a pool entry it may LOOK like it works, but `new String("x") == "x"` is false. Always use `.equals()` for string comparison. `==` only when comparing nulls or interned tokens (rare).

## In this package

```
strings/
├── README.md
├── StringsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── StringPoolDemo.java
    └── ConcatBenchmark.java
```
