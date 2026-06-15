# Concurrency — threads, executors, futures, virtual threads

The biggest swing area at lead level. They will ask you to design a thread-safe component, walk through CompletableFuture, and explain virtual threads.

## Thread basics

- A thread is a JVM-level stream of execution; pre-Java 21, 1:1 with an OS thread.
- States: `NEW → RUNNABLE → BLOCKED / WAITING / TIMED_WAITING → TERMINATED`.
- `Thread.start()` runs the thread; `Thread.run()` just invokes the method on the current thread. Easy trap.

## Runnable vs Callable vs Supplier

| Type | Returns | Can throw checked? |
| --- | --- | --- |
| `Runnable` | void | No |
| `Callable<V>` | V | Yes |
| `Supplier<V>` (functional) | V | No |

`ExecutorService.submit(Runnable)` returns `Future<?>`; `submit(Callable)` returns `Future<V>`.

## ExecutorService — the right way to run threads

```java
ExecutorService pool = Executors.newFixedThreadPool(8);
Future<Integer> f = pool.submit(() -> heavyWork());
Integer result = f.get(30, TimeUnit.SECONDS);   // blocks
pool.shutdown();                                 // initiate orderly shutdown
pool.awaitTermination(60, TimeUnit.SECONDS);
```

Pool types:
- `newFixedThreadPool(n)` — bounded; backed by `LinkedBlockingQueue` (unbounded — risk!).
- `newCachedThreadPool()` — grows unboundedly; reuses idle. Risk under load.
- `newSingleThreadExecutor()` — sequential.
- `Executors.newWorkStealingPool()` — fork/join based.

For real services, build with `ThreadPoolExecutor(...)` directly so you can bound the queue, set a rejection policy, name the threads, and set a custom thread factory.

## CompletableFuture — async composition

```java
CompletableFuture<User> userF      = CompletableFuture.supplyAsync(() -> userSvc.get(id), pool);
CompletableFuture<Account> acctF   = CompletableFuture.supplyAsync(() -> acctSvc.get(id), pool);

CompletableFuture<Dashboard> all = userF.thenCombine(acctF, Dashboard::new)
    .exceptionally(ex -> Dashboard.empty());

Dashboard d = all.get(5, TimeUnit.SECONDS);
```

Key methods:
- `supplyAsync` / `runAsync` — fire off.
- `thenApply` / `thenAccept` / `thenRun` — transform.
- `thenCompose` — chain another async (like flatMap).
- `thenCombine` — join two.
- `allOf`, `anyOf` — wait for multiple.
- `exceptionally` / `handle` — error handling.
- Always pass an explicit `Executor` — default `ForkJoinPool.commonPool()` is shared with parallel streams.

## synchronized vs volatile vs atomics

| Use | When |
| --- | --- |
| `synchronized` | Compound operations (read-modify-write) on shared state |
| `volatile` | Single-variable visibility (flag), no compound ops |
| `AtomicXxx` | Lock-free CAS for single fields (counters, references) |
| `ReentrantLock` | Need `tryLock`, fairness, multiple condition vars |
| `ReadWriteLock` | Lots of reads, few writes |
| `StampedLock` | Optimistic reads on hot paths (Java 8+) |

```java
volatile boolean shutdown = false;          // OK — just a visibility flag
synchronized (lock) { count = count + 1; }  // OK — read-modify-write
AtomicInteger counter = new AtomicInteger(); // BETTER — counter.incrementAndGet();
```

## Virtual threads (Java 21+)

```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> handleRequest(req));
}
```

- Mounted onto carrier threads from a small platform-thread pool.
- Cheap to create (~1KB stack vs ~1MB for platform).
- Designed for **blocking I/O code**, NOT CPU-bound work.
- Pinned to a carrier while inside `synchronized` blocks or native methods — prefer `ReentrantLock` for hot paths.
- `Thread.startVirtualThread(...)` or via the executor above.

## Common pitfalls

- **Deadlock** — two threads each holding a lock the other needs. Mitigate: consistent lock ordering, `tryLock` with timeout.
- **Race condition** — read-modify-write without synchronisation. Often invisible until prod load.
- **`InterruptedException` swallowed** — call `Thread.currentThread().interrupt()` to re-set.
- **Unbounded queues in `newFixedThreadPool`** — OOM under back-pressure.
- **`commonPool()` for blocking work** — starves parallel streams across the JVM.

## Canned answers

> **"How does CompletableFuture differ from Future?"**
>
> `Future` is just a handle: poll or block on `get()`. `CompletableFuture` lets you compose pipelines (`thenApply`, `thenCompose`, `thenCombine`) and handle errors (`exceptionally`, `handle`) without blocking. The mental model is closer to JS Promises than to Java's older blocking futures.

> **"When to use virtual threads vs platform threads?"**
>
> Virtual for blocking I/O at scale — handling 10k concurrent HTTP calls without 10k OS threads. Platform threads for CPU-bound work where you want the OS scheduler. Watch out for pinning: `synchronized` blocks pin a virtual thread to its carrier, defeating the cheapness — use `ReentrantLock` on hot paths.

## References

- *Java Concurrency in Practice* — Goetz et al.
- *Effective Java* — Items 78–84.

## In this package

```
concurrency/
├── README.md
├── ConcurrencyGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── CompletableFutureRecipes.java
    ├── VirtualThreadsDemo.java
    └── SynchronizedVsAtomic.java
```
