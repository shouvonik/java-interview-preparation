# Hashing

Hash maps and sets are the most-used data structure in interviews — they turn O(n²) brute force into O(n) for an enormous range of problems. Knowing the *idioms* (counting, dedup by composite key, group-by, prefix-sum + hash) matters more than knowing the internals.

That said, lead-level interviews do ask about internals:
- HashMap uses an array of buckets; collisions form linked lists, converted to red-black trees once a bucket exceeds 8 entries (Java 8+).
- Load factor default 0.75 — resize doubles capacity.
- `hashCode()` and `equals()` contract: equal objects must produce equal hashes; both must agree on the same fields.

## Patterns covered in `Hashing.java`

| Pattern | When to use | Complexity |
| --- | --- | --- |
| Dedup with composite key | "Unique tuples of (a, b, c)" | O(n) avg |
| Group by computed key | Anagrams, file-content dedup | O(n × k) where k is key cost |
| Hash set for O(1) lookup | Longest consecutive sequence, two-sum | O(n) avg |
| LRU via LinkedHashMap | Cache eviction | O(1) get/put |

## Real-life framing

- **Composite key** → API call dedup keyed by (userId, requestId, hourBucket).
- **Group by anagram** → file-deduplication tool grouping files by content hash.
- **Longest consecutive** → session-replay engine finding the longest run of consecutive event IDs.
- **LRU** → an in-process cache in front of a slow external service (the classic interview question).

## Pitfalls

- **Mutable keys** — never put an object into a HashMap and then mutate fields that affect its hash. The map will lose track of it.
- **`equals`/`hashCode` mismatch** — if you override one, override the other. IDEs generate both. Records do this for you automatically.
- **Null keys** — HashMap allows one null key; ConcurrentHashMap does not.
- **Iteration order** — HashMap is not ordered; LinkedHashMap preserves insertion (or access) order; TreeMap is sorted.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Map of seen values |
| 217 | [Contains Duplicate](https://leetcode.com/problems/contains-duplicate/) | Easy | Set |
| 242 | [Valid Anagram](https://leetcode.com/problems/valid-anagram/) | Easy | Counter |
| 49 | [Group Anagrams](https://leetcode.com/problems/group-anagrams/) | Medium | Group-by sorted key |
| 128 | [Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/) | Medium | Set + run start |
| 146 | [LRU Cache](https://leetcode.com/problems/lru-cache/) | Medium | LinkedHashMap |
| 460 | [LFU Cache](https://leetcode.com/problems/lfu-cache/) | Hard | Map + bucket lists |
| 36 | [Valid Sudoku](https://leetcode.com/problems/valid-sudoku/) | Medium | Composite keys per row/col/box |
| 380 | [Insert Delete GetRandom O(1)](https://leetcode.com/problems/insert-delete-getrandom-o1/) | Medium | Map + ArrayList |

## Run

```bash
cd dsa/src/main/java
javac dsa/hashing/Hashing.java
java dsa.hashing.Hashing
```
