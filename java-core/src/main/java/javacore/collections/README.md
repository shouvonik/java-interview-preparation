# Collections — internals + idioms

The single most-tested area at lead level. They want to hear you know the **internals**, not just the APIs.

## The hierarchy

```
Iterable
└── Collection
    ├── List           → ArrayList, LinkedList, Vector(legacy), CopyOnWriteArrayList
    ├── Set            → HashSet, LinkedHashSet, TreeSet, CopyOnWriteArraySet
    └── Queue / Deque  → ArrayDeque, LinkedList, PriorityQueue, ConcurrentLinkedDeque

Map  (separate hierarchy; doesn't extend Collection)
├── HashMap, LinkedHashMap, TreeMap
├── Hashtable (legacy), Properties
└── ConcurrentHashMap, ConcurrentSkipListMap
```

## HashMap internals (Java 8+)

- Array of buckets; index = `hash(key) & (capacity - 1)`.
- Collisions chain in a linked list. **When a bucket exceeds 8 entries AND total capacity ≥ 64, that bucket is converted to a red-black tree** for O(log n) lookup instead of O(n). Below 6 entries it converts back.
- Default capacity 16, load factor 0.75; resizes (doubles) when size > capacity × load factor.
- `null` keys allowed (one slot at bucket 0); `null` values allowed.
- NOT thread-safe — concurrent modification can corrupt the chain or cause infinite loops on resize.

## ArrayList vs LinkedList

| Op | ArrayList | LinkedList |
| --- | --- | --- |
| `get(i)` | O(1) | O(n) |
| `add(end)` | amortised O(1) | O(1) |
| `add(middle)` | O(n) (shift) | O(n) walk to index |
| `remove(end)` | O(1) | O(1) |
| Cache friendliness | Yes (contiguous) | No (pointer chasing) |
| Memory per element | ~4-8 bytes | ~24 bytes + node |

In practice `ArrayList` (or `ArrayDeque` for queue/stack) almost always wins. `LinkedList` is rarely the right answer.

## Comparable vs Comparator

- `Comparable<T>` — natural ordering, `int compareTo(T)`. Implement when the type has ONE canonical order.
- `Comparator<T>` — external ordering. Use `Comparator.comparing(...)`, `.thenComparing(...)`, `.reversed()` for fluent chaining.

## Thread-safe collections

| Need | Use |
| --- | --- |
| Concurrent map | `ConcurrentHashMap` (segment-based locking, no global lock) |
| Concurrent sorted map | `ConcurrentSkipListMap` |
| Concurrent list with mostly reads | `CopyOnWriteArrayList` (whole array cloned on write) |
| Concurrent queue | `ConcurrentLinkedQueue`, `LinkedBlockingQueue` |
| One-off thread-safe wrapper | `Collections.synchronizedXxx` (coarse lock; iterate inside `synchronized(view)`) |

## Common pitfalls

- **HashMap with mutable keys** — mutating fields used in `equals`/`hashCode` after insert means the map can't find the entry anymore.
- **Iterating + modifying** without `Iterator.remove()` → `ConcurrentModificationException`.
- **`HashMap` under concurrent writes** — race conditions can produce data loss or (pre-Java 8) infinite resize loops.
- **`Collections.synchronizedList`** isn't enough — iteration must be inside a `synchronized` block on the view.
- **`stream().toList()`** returns an **unmodifiable** list (Java 16+); `Collectors.toList()` returns a mutable one.

## Canned answers

> **"What happens internally when you put into a HashMap?"**
>
> Compute hash (mixed via `(h = key.hashCode()) ^ (h >>> 16)` for better dispersion), find bucket via `(n - 1) & hash`, walk the chain comparing with `equals`. If absent, append; if the bucket has > 8 entries AND capacity ≥ 64, convert to a TreeNode bin. Resize when size > 12 (capacity 16 × 0.75) — capacity doubles, every entry is rehashed.

> **"When would you reach for `ConcurrentHashMap` vs `Collections.synchronizedMap`?"**
>
> Almost always `ConcurrentHashMap` — segmented locking means concurrent reads and writes to different buckets don't block each other. `Collections.synchronizedMap` wraps each op in a single coarse lock; iteration still needs an external `synchronized` block.

## In this package

```
collections/
├── README.md
├── CollectionsGuide.java
├── exercises/, exercises/solutions/
└── examples/
    ├── HashMapInternals.java     # bucket counts, treeification trace
    ├── ArrayListVsLinkedList.java # cache-friendliness microbench
    └── ComparatorChaining.java
```
