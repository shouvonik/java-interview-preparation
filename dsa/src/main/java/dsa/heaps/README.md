# Heaps *(scaffolded — code coming next)*

Heaps (priority queues) give you **O(log n) push / pop** with the smallest (or largest) element always at the root. In Java, `PriorityQueue` is a min-heap by default; flip with `Comparator.reverseOrder()`.

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| Top K elements | 215, 347, 692 |
| K-way merge (k sorted lists) | 23 |
| Running median (two heaps) | 295 |
| Scheduling — meeting rooms II | 253 |
| Reorganize string (greedy + heap) | 767 |
| Dijkstra (already covered under graphs/) | 743 |

## Idioms

```java
// Min-heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

// Heap of custom objects, by score desc
PriorityQueue<Entry> pq = new PriorityQueue<>(
    Comparator.comparingInt(Entry::score).reversed());

// Top-K trick: keep a min-heap of size K. After K elements, push then pop.
// Heap retains the K largest.
PriorityQueue<Integer> topK = new PriorityQueue<>();
for (int x : nums) {
    topK.offer(x);
    if (topK.size() > k) topK.poll();
}
```

## Pitfalls

- `PriorityQueue.iterator()` does NOT iterate in sorted order — it iterates in heap order.
- `remove(Object)` is O(n) (linear search). If you need fast remove-by-value, use a `TreeSet` instead.
- Don't mutate fields used by the comparator after offering — the heap will silently break.

## Curated LeetCode

| # | Problem | Difficulty |
| --- | --- | --- |
| 215 | [Kth Largest Element in an Array](https://leetcode.com/problems/kth-largest-element-in-an-array/) | Medium |
| 347 | [Top K Frequent Elements](https://leetcode.com/problems/top-k-frequent-elements/) | Medium |
| 692 | [Top K Frequent Words](https://leetcode.com/problems/top-k-frequent-words/) | Medium |
| 23 | [Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) | Hard |
| 295 | [Find Median from Data Stream](https://leetcode.com/problems/find-median-from-data-stream/) | Hard |
| 253 | [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) | Medium |
| 767 | [Reorganize String](https://leetcode.com/problems/reorganize-string/) | Medium |
| 621 | [Task Scheduler](https://leetcode.com/problems/task-scheduler/) | Medium |
| 973 | [K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/) | Medium |
