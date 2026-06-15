package dsa.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Heaps (Priority Queues) — patterns for coding interviews.
 *
 * Run: java dsa.heaps.Heaps
 *
 * In Java, PriorityQueue is a MIN-heap by default; flip with Comparator.reverseOrder().
 * Top-K trick: a min-heap of size K retains the K largest elements seen so far.
 */
public class Heaps {

    // ---------------------------------------------------------------------
    // 1) Min vs max heap.
    // ---------------------------------------------------------------------
    static int min(int[] nums) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int n : nums) minHeap.offer(n);
        return minHeap.peek();
    }

    static int max(int[] nums) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        for (int n : nums) maxHeap.offer(n);
        return maxHeap.peek();
    }

    // ---------------------------------------------------------------------
    // 2) Top-K — min-heap of size K.
    // ---------------------------------------------------------------------
    // For each value, push; if size exceeds K, poll the smallest. End state =
    // the K largest values seen, with the K-th largest at the root.
    static int kthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int n : nums) {
            heap.offer(n);
            if (heap.size() > k) heap.poll();
        }
        return heap.peek();
    }

    // ---------------------------------------------------------------------
    // 3) Custom comparator — leaderboard top-K by score desc, then time asc.
    // ---------------------------------------------------------------------
    public record Entry(String name, int score, int timeMs) {}

    static Entry topPlayer(java.util.List<Entry> entries) {
        PriorityQueue<Entry> pq = new PriorityQueue<>(
            Comparator.comparingInt(Entry::score).reversed()
                .thenComparingInt(Entry::timeMs));
        pq.addAll(entries);
        return pq.peek();
    }

    // ---------------------------------------------------------------------
    // 4) Two heaps — running median.
    // ---------------------------------------------------------------------
    public static class MedianFinder {
        private final PriorityQueue<Integer> lower = new PriorityQueue<>(Comparator.reverseOrder());
        private final PriorityQueue<Integer> upper = new PriorityQueue<>();

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

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4};
        System.out.println("min = " + min(nums));                       // 1
        System.out.println("max = " + max(nums));                       // 6
        System.out.println("2nd largest = " + kthLargest(nums, 2));     // 5

        Entry winner = topPlayer(java.util.List.of(
            new Entry("Alice", 100, 1200),
            new Entry("Bob",   100,  900),
            new Entry("Cara",   90, 1500)
        ));
        System.out.println("top player = " + winner);    // Bob (100, 900)

        MedianFinder mf = new MedianFinder();
        mf.addNum(1); mf.addNum(2);
        System.out.println("median after 1,2   = " + mf.findMedian());   // 1.5
        mf.addNum(3);
        System.out.println("median after 1,2,3 = " + mf.findMedian());   // 2.0
    }
}
