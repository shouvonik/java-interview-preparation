# Searching

Binary search is the highest-ROI algorithm in interviews. The classic "find x in a sorted array" is just the warm-up — the pattern that actually shows up is **binary search on the answer**: "find the smallest value v in [lo, hi] such that `feasible(v)` is true."

## Patterns covered in `Searching.java`

| Pattern | When to use | Complexity |
| --- | --- | --- |
| Classic binary search (closed range) | Find a value in a sorted array | O(log n) |
| Lower bound / upper bound | First index ≥ target / first > target | O(log n) |
| Search in rotated sorted array | Sorted then rotated, no duplicates | O(log n) |
| Binary search on the answer | "Smallest v such that f(v) is true" | O(log(range) × cost(f)) |

## Real-life framing

- **Lower bound** → finding the first log entry on or after a given timestamp.
- **Rotated sorted** → server logs spliced across a midnight boundary.
- **Koko eating bananas** → SRE picking the smallest k req/sec a service can sustain while keeping queue depth bounded.

## Templates — memorise these

**Closed range `[lo, hi]` for exact match:**
```java
int lo = 0, hi = n - 1;
while (lo <= hi) {
    int mid = lo + (hi - lo) / 2;          // overflow-safe
    if (arr[mid] == target) return mid;
    if (arr[mid] < target) lo = mid + 1;
    else                   hi = mid - 1;
}
return -1;
```

**Half-open `[lo, hi)` for lower-bound:**
```java
int lo = 0, hi = n;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (arr[mid] < target) lo = mid + 1;
    else                   hi = mid;       // shrink toward smaller candidate
}
return lo;
```

**Binary search on the answer:**
```java
int lo = minAnswer, hi = maxAnswer;
while (lo < hi) {
    int mid = lo + (hi - lo) / 2;
    if (feasible(mid)) hi = mid;           // mid works, smaller might too
    else               lo = mid + 1;       // mid fails, must be larger
}
return lo;                                 // smallest feasible value
```

## Pitfalls

- `(lo + hi) / 2` overflows on huge inputs. Always `lo + (hi - lo) / 2`.
- Off-by-one between `<=` and `<` — pick a template and stick with it.
- For "find any occurrence" classic works. For "first/last occurrence" you need lower/upper bound.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 704 | [Binary Search](https://leetcode.com/problems/binary-search/) | Easy | Classic |
| 35 | [Search Insert Position](https://leetcode.com/problems/search-insert-position/) | Easy | Lower bound |
| 278 | [First Bad Version](https://leetcode.com/problems/first-bad-version/) | Easy | BS on the answer |
| 33 | [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/) | Medium | Rotated |
| 153 | [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) | Medium | Rotated |
| 875 | [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/) | Medium | BS on the answer |
| 1011 | [Capacity to Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) | Medium | BS on the answer |
| 410 | [Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/) | Hard | BS on the answer |
| 4 | [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) | Hard | Partition BS |

## Run

```bash
cd dsa/src/main/java
javac dsa/searching/Searching.java
java dsa.searching.Searching
```
