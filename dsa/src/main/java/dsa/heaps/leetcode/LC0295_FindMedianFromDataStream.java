package dsa.heaps.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LeetCode 295 — Find Median from Data Stream
 * Difficulty: Hard   Tags: Two Pointers, Design, Sorting, Heap, Data Stream
 * URL: https://leetcode.com/problems/find-median-from-data-stream/
 *
 * <h2>Problem</h2>
 * Design a data structure that supports adding integers from a data stream and
 * finding the median of all elements added so far.
 *
 * <h2>Examples</h2>
 * <pre>
 *   addNum(1); addNum(2); findMedian()  -> 1.5
 *   addNum(3);            findMedian()  -> 2
 * </pre>
 *
 * <h2>Approach — two heaps</h2>
 * <ul>
 *   <li>{@code lower} (max-heap): the smaller half.</li>
 *   <li>{@code upper} (min-heap): the larger half.</li>
 *   <li>Maintain {@code |lower| - |upper| ∈ {0, 1}}: lower has the same size as
 *       upper, or exactly one more.</li>
 * </ul>
 * On add: push to {@code lower}, then move {@code lower.top()} to {@code upper}.
 * If {@code |upper| > |lower|}, move {@code upper.top()} back. This keeps
 * the invariant.
 * <p>
 * Median: if sizes are equal, average the two tops; else return {@code lower.top()}.
 *
 * <h2>Why this gives O(log n) add and O(1) median</h2>
 * Heap push and pop are O(log n). Median is just two heap tops — O(1).
 *
 * <h2>Alternative — TreeMap multiset</h2>
 * Useful if you also need percentiles, range queries, or removals. Same
 * O(log n) but more bookkeeping.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0295_FindMedianFromDataStream
 * </pre>
 */
public class LC0295_FindMedianFromDataStream {

    public static class MedianFinder {
        private final PriorityQueue<Integer> lower = new PriorityQueue<>(Comparator.reverseOrder()); // max
        private final PriorityQueue<Integer> upper = new PriorityQueue<>();                          // min

        public void addNum(int num) {
            lower.offer(num);
            upper.offer(lower.poll());
            if (upper.size() > lower.size()) lower.offer(upper.poll());
        }

        public double findMedian() {
            if (lower.size() > upper.size()) return lower.peek();
            return (lower.peek() + upper.peek()) / 2.0;
        }
    }

    public static void main(String[] args) {
        MedianFinder m = new MedianFinder();
        m.addNum(1); m.addNum(2);
        System.out.println(m.findMedian()); // 1.5
        m.addNum(3);
        System.out.println(m.findMedian()); // 2.0
    }
}
