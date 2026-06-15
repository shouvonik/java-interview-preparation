# Exceptions — checked vs unchecked, resources, custom types

## The hierarchy

```
Throwable
├── Error           ← JVM-internal, don't catch (OutOfMemoryError, StackOverflowError)
└── Exception
    ├── RuntimeException     ← UNCHECKED
    │     (NullPointerException, IllegalArgumentException, ClassCastException, ...)
    └── (everything else)    ← CHECKED
          (IOException, SQLException, InterruptedException, ...)
```

## Checked vs unchecked — when to use which

- **Checked** — recoverable conditions the caller should handle (network down, file not found). Forces the caller to acknowledge.
- **Unchecked** — programming errors (null arg, index out of bounds) or unrecoverable situations. Caller usually can't reasonably fix at runtime.

Modern Java code leans **unchecked** for most application-level exceptions because checked exceptions don't compose well with lambdas/streams.

## try-with-resources

```java
try (var reader = Files.newBufferedReader(path)) {
    return reader.readLine();
}
// reader.close() called even on exception; suppressed exceptions stored on the thrown one.
```

Use for anything implementing `AutoCloseable` — connections, streams, locks (`ReentrantLock` via `lock()`/`unlock()` is NOT AutoCloseable; wrap manually).

## Multi-catch and rethrow

```java
try { ... }
catch (IOException | SQLException e) {       // Java 7+
    log.error("io or db", e);
    throw e;                                  // rethrow same checked type
}
```

## Custom exceptions

Three rules:
1. Inherit `RuntimeException` (unchecked) unless the caller is truly expected to handle.
2. Provide a `(String, Throwable)` constructor so wrapping preserves the cause.
3. Don't expose internal fields the caller can't use; expose just enough to make the message useful.

```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }
    public UserNotFoundException(String userId, Throwable cause) {
        super("User not found: " + userId, cause);
    }
}
```

## Best practices

- **Never catch `Throwable`** — you'll swallow `Error`s like `OutOfMemoryError`.
- **Never catch and ignore** without a comment explaining why.
- **Don't use exceptions for control flow** — throw is expensive (stack trace fill).
- **Throw early, catch late** — only catch where you can actually handle.
- **Wrap, don't lose** — when wrapping, pass the cause: `throw new ServiceException("failed to load X", e);`.
- **Don't catch `InterruptedException` and discard the flag** — call `Thread.currentThread().interrupt()` to re-set it.

## Common pitfalls

- **Empty catch blocks** — silently swallow errors; the worst kind of bug.
- **`catch (Exception e)`** as a catch-all — usually wrong; narrow to specific exceptions.
- **Logging then rethrowing** — same exception logged twice as it propagates up.
- **Throwing checked exceptions from `Stream.map(...)`** — they don't compile; wrap as runtime or use a custom functional interface.

## Canned answers

> **"When would you make an exception checked vs unchecked?"**
>
> Checked when the caller has a reasonable recovery path AND it's part of the API contract (file not found, network timeout). Unchecked for programming bugs (null arg, wrong state) or service-layer wrapping. Most application code leans unchecked because lambdas don't compose with checked exceptions.

> **"What does try-with-resources do under the hood?"**
>
> The compiler rewrites it as a try/finally that calls `close()` in finally. If the body throws AND close() throws, the close exception is added as a SUPPRESSED exception on the body's (accessible via `Throwable.getSuppressed()`).

## In this package

```
exceptions/
├── README.md
├── ExceptionsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── CustomExceptionDesign.java
    └── TryWithResourcesSuppressed.java
```
