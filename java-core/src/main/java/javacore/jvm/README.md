# JVM — memory, GC, class loading, JIT

You'll be asked things like "what does `-Xmx` actually do?" and "what's the difference between G1 and ZGC?". This is signal that you've operated real services.

## Memory areas (per JVM, not per thread)

| Area | Holds | Per-thread? |
| --- | --- | --- |
| Heap | Objects, instance fields | No |
| Metaspace | Class metadata (`Class<?>`, method bytecode) | No |
| Stack | Frames: locals, partial results, return address | YES — one per thread |
| Program Counter | Current bytecode instruction | YES |
| Code cache | JIT-compiled native code | No |

The **heap** is split internally:
- **Young generation** — Eden + 2 Survivor spaces (S0, S1). New objects start in Eden.
- **Old generation** — long-lived objects, promoted from Young.

`-Xmx4g -Xms2g` sets max and initial heap. `-Xss512k` sets per-thread stack.

## GC algorithms — pick by trade-off

| GC | Pause | Throughput | Use when |
| --- | --- | --- | --- |
| Serial | Long | High | Single CPU, small heap (< 100MB) |
| Parallel (Throughput) | Long | Highest | Batch jobs, throughput > latency |
| G1 (default since 9) | Moderate | High | Server apps, heaps 4–32GB |
| ZGC | < 10ms even on TB heaps | Slightly lower | Latency-critical, large heaps |
| Shenandoah | < 10ms | Slightly lower | Like ZGC; OpenJDK fork |

Mnemonic: throughput vs latency. Choose Parallel for batch, G1 default for services, ZGC when sub-10ms tail matters.

## Class loading

Three-phase: load → link (verify, prepare, resolve) → initialise. Standard hierarchy:

```
Bootstrap (rt.jar core classes)
  ↑ parent
Platform (java.* extensions)
  ↑ parent
System / Application (your classpath)
  ↑ parent
Custom (web frameworks, OSGi)
```

Parent-delegation model: child asks parent first; only loads itself if parent fails. Frameworks like Tomcat invert this for per-webapp isolation.

## JIT — how Java gets fast

- JVM starts in interpreted mode.
- HotSpot detects "hot" methods (default ~10k invocations) and compiles them to native code.
- Two tiers: C1 (fast compile, less optimised) and C2 (slow compile, heavily optimised). Tiered compilation runs both, escalating hot methods to C2.
- Inlining is the biggest single win — small, monomorphic methods get inlined into callers.
- Speculation: based on observed types/conditions; deoptimises and falls back to interpreter if assumption breaks.

## Common JVM flags

```
-Xms2g -Xmx2g                       # heap size (equal = no resize)
-Xss512k                             # stack per thread
-XX:+UseG1GC                         # GC algorithm
-XX:MaxGCPauseMillis=200             # G1 pause goal
-XX:+HeapDumpOnOutOfMemoryError      # dump on OOM
-XX:HeapDumpPath=/var/log/dump.hprof
-XX:+PrintGCDetails                  # pre-Java 9 GC logs
-Xlog:gc*=info                       # Java 9+ unified logging
```

## Memory leaks in managed languages

Java has GC, so "memory leak" usually means **unintentional retention** — a reference that prevents GC:
- Static collections growing without bound.
- Listeners not unregistered.
- ThreadLocal not cleared (especially with thread pools).
- ClassLoader leaks (old WAR redeploys retaining classes).

Tools: `jcmd <pid> GC.heap_dump`, then heap analyser (Eclipse MAT, VisualVM, IntelliJ Profiler).

## Common pitfalls

- **Setting `-Xmx` too low** — GC thrashes; throughput collapses.
- **Setting `-Xmx` too high** — long Full GC pauses, OS swap pressure.
- **Mixing GC flags** — combinations like UseParallelGC + G1 just don't make sense.
- **Profiling without warm-up** — first-iteration numbers reflect interpretation, not JIT'd code. Use JMH for serious benchmarks.

## Canned answers

> **"What's the difference between G1 and ZGC?"**
>
> G1 is a generational, region-based, mostly-concurrent collector — STW pauses scale roughly with heap size, target ~200ms by default. ZGC is concurrent for the entire collection cycle using load barriers and coloured pointers — pauses stay under 10ms even on terabyte heaps. Trade-off: ZGC trades a bit of CPU and total throughput for tail latency.

> **"How does JIT compilation work?"**
>
> JVM starts interpreting bytecode. HotSpot watches method invocation counts; once a method crosses a threshold (~10k), it's compiled to native by C1 (fast, lightly optimised). Hot methods get re-compiled by C2 (heavy optimisation, inlining, escape analysis, speculative devirtualisation). If a speculation breaks at runtime, the code is deoptimised and re-interpreted until it gets recompiled.

## In this package

```
jvm/
├── README.md
├── JvmGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── ObjectFootprint.java         # measure with -verbose:gc + Instrumentation
    └── EscapeAnalysisDemo.java
```
