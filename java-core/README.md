# Java Core

Concept refreshers for the parts of the JDK that lead-level interviewers probe most.

Unlike the DSA module, these topics are about **deep understanding** — you'll be asked "why does HashMap convert to a red-black tree at 8?" or "what happens to memory when you swap CompletableFuture for virtual threads?" rather than to solve a coding problem on the fly.

## Topics

| # | Topic | Why it's on every lead interview |
| --- | --- | --- |
| 1 | [basics](src/main/java/javacore/basics/) | OOP pillars, `equals`/`hashCode` contract, access modifiers — fundamentals that surface in design reviews. |
| 2 | [collections](src/main/java/javacore/collections/) | HashMap/ArrayList internals, `Comparable` vs `Comparator`, thread-safe collections. The most-asked area. |
| 3 | [generics](src/main/java/javacore/generics/) | Bounded types, wildcards (PECS), type erasure pitfalls. |
| 4 | [exceptions](src/main/java/javacore/exceptions/) | Checked vs unchecked, try-with-resources, custom exception design. |
| 5 | [streams](src/main/java/javacore/streams/) | Stream API, lambdas, collectors, parallel pitfalls. |
| 6 | [optionals](src/main/java/javacore/optionals/) | When to use, anti-patterns (as fields/params), `orElse` vs `orElseGet`. |
| 7 | [strings](src/main/java/javacore/strings/) | Immutability, String pool, StringBuilder vs StringBuffer, intern. |
| 8 | [concurrency](src/main/java/javacore/concurrency/) | Threads, ExecutorService, CompletableFuture, synchronized/volatile/atomics, ReentrantLock, virtual threads. |
| 9 | [jvm](src/main/java/javacore/jvm/) | Memory model (heap/stack/metaspace), GC basics, class loading, JIT. |
| 10 | [modernfeatures](src/main/java/javacore/modernfeatures/) | Records, sealed classes, pattern matching, switch expressions, text blocks, `var`. |
| 11 | [designpatterns](src/main/java/javacore/designpatterns/) | Singleton, Factory, Builder, Strategy, Observer, Adapter, Decorator — and when NOT to use them. |

## How each topic is organised

```
<topic>/
├── README.md                    # concept notes, talking points, pitfalls, canned answers
├── <Topic>Guide.java            # worked guide with runnable main()
├── exercises/
│   ├── <Topic>Exercises.java    # try yourself
│   └── solutions/
│       └── <Topic>Solutions.java
└── examples/                    # focused, single-concept demos (e.g. HashMapInternals.java)
```

## Run

```bash
# from repo root
mvn -pl java-core compile
mvn -pl java-core exec:java -Dexec.mainClass=javacore.collections.CollectionsGuide

# or raw javac
javac --release 26 -d /tmp/jc $(find java-core -name '*.java')
java -cp /tmp/jc javacore.collections.CollectionsGuide
```
