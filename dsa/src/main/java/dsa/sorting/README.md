# Sorting

You will almost never write a sort from scratch in production — `Arrays.sort` and `Collections.sort` are well-tuned (TimSort for objects, Dual-Pivot QuickSort for primitives). But interviewers still ask you to **implement** merge sort and quicksort to see if you understand divide-and-conquer, in-place partitioning, and stability.

## Patterns covered in `Sorting.java`

| Pattern | Time | Space | Stable | Notes |
| --- | --- | --- | --- | --- |
| Merge sort | O(n log n) | O(n) | Yes | Predictable, stable, used by JDK for objects |
| Quick sort | avg O(n log n), worst O(n²) | O(log n) stack | No | In-place, cache-friendly |
| Quickselect | avg O(n), worst O(n²) | O(log n) | No | Kth smallest without full sort |
| Dutch national flag | O(n) | O(1) | No | 3-way partition: <, =, > |
| Multi-key Comparator | O(n log n) | O(log n) | Stable if base is | Compose tie-breakers |

## Real-life framing

- **Merge sort** → external sort over log files larger than memory (the merge step is what survives).
- **Quickselect** → "top-K request latencies" without sorting the whole stream.
- **Dutch flag** → bucket events by priority (high/normal/low) in one pass.
- **Multi-key Comparator** → leaderboard sorted by score desc, then by time asc, then by name asc.

## When to roll your own vs `Arrays.sort`

Use `Arrays.sort` / `Collections.sort` for almost everything. Roll your own only when:
- You need a non-comparison sort (counting/radix/bucket) for known-small integer ranges.
- You need quickselect to avoid a full sort.
- You're answering an interview question that explicitly asks for it.

## Stability matters when

Sorting by a secondary key after a primary key. If `sort` is stable, you can sort by the *less significant* key first, then by the *more significant* — and tied items stay in the earlier order. Java's `Collections.sort` is stable; `Arrays.sort` on primitives is not.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 912 | [Sort an Array](https://leetcode.com/problems/sort-an-array/) | Medium | Implement merge or quick |
| 215 | [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) | Medium | Quickselect or heap |
| 75 | [Sort Colors](https://leetcode.com/problems/sort-colors/) | Medium | Dutch flag |
| 56 | [Merge Intervals](https://leetcode.com/problems/merge-intervals/) | Medium | Sort + sweep |
| 451 | [Sort Characters By Frequency](https://leetcode.com/problems/sort-characters-by-frequency/) | Medium | Bucket sort |
| 179 | [Largest Number](https://leetcode.com/problems/largest-number/) | Medium | Custom comparator |
| 252 | [Meeting Rooms](https://leetcode.com/problems/meeting-rooms/) | Easy | Sort + scan |
| 253 | [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) | Medium | Sort + heap |
| 315 | [Count of Smaller Numbers After Self](https://leetcode.com/problems/count-of-smaller-numbers-after-self/) | Hard | Merge sort + count |

## Run

```bash
cd dsa/src/main/java
javac dsa/sorting/Sorting.java
java dsa.sorting.Sorting
```
