package dsa.sorting.leetcode;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * LeetCode 215 — Kth Largest Element in an Array
 * Difficulty: Medium   Tags: Array, Sorting, Heap (Priority Queue), Quickselect
 * URL: https://leetcode.com/problems/kth-largest-element-in-an-array/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} and an integer {@code k}, return the
 * kth largest element (i.e. the element that would be at index {@code n - k}
 * in a sorted ascending array). Must NOT be the kth distinct element.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; k &le; nums.length &le; 10^5</li>
 *   <li>-10^4 &le; nums[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3, 2, 1, 5, 6, 4],          k = 2  ->  5
 *   [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4  ->  4
 *   [1],                         k = 1  ->  1
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Sort then index</b> — O(n log n).
 * Trivial but slower than needed.
 * <p>
 * <b>Min-heap of size k</b> — O(n log k) time, O(k) space.
 * Walk the array, push to a min-heap; if size exceeds k, poll. At the end the
 * root is the kth largest. Good streaming choice.
 * <p>
 * <b>Quickselect (canonical for offline)</b> — O(n) avg, O(n^2) worst.
 * Variant of quicksort that recurses into only one half. With a random pivot,
 * expected linear time. The classic "best you can do without sorting".
 *
 * <h2>Quickselect intuition</h2>
 * Partition around a pivot so that elements smaller than the pivot are on the
 * left. Let {@code p} be the pivot's final index. We want the value at index
 * {@code n - k}. If {@code p == n - k}, return it; if {@code p < n - k},
 * recurse right; else recurse left.
 *
 * <h2>Why a random pivot</h2>
 * Adversarial inputs (already-sorted) make a deterministic pivot O(n^2). A
 * random pivot makes the bad case astronomically unlikely while keeping the
 * code simple.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0215_KthLargestElementInAnArray
 * </pre>
 */
public class LC0215_KthLargestElementInAnArray {

    /** Quickselect — O(n) average. */
    public static int findKthLargest(int[] nums, int k) {
        int target = nums.length - k;                     // 0-indexed position we want
        int lo = 0, hi = nums.length - 1;
        Random rng = new Random(42);
        while (lo <= hi) {
            int p = partition(nums, lo, hi, lo + rng.nextInt(hi - lo + 1));
            if (p == target) return nums[p];
            if (p < target)  lo = p + 1;
            else             hi = p - 1;
        }
        throw new IllegalStateException("unreachable");
    }

    private static int partition(int[] arr, int lo, int hi, int pivotIdx) {
        int pivot = arr[pivotIdx];
        swap(arr, pivotIdx, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (arr[i] < pivot) swap(arr, i, store++);
        }
        swap(arr, store, hi);
        return store;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    /** Min-heap of size k — O(n log k). Good when n is huge and streaming. */
    public static int findKthLargestHeap(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int n : nums) {
            heap.offer(n);
            if (heap.size() > k) heap.poll();
        }
        return heap.peek();
    }

    public static void main(String[] args) {
        System.out.println(findKthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));                  // 5
        System.out.println(findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 4));         // 4
        System.out.println(findKthLargest(new int[]{1}, 1));                                  // 1
        System.out.println(findKthLargestHeap(new int[]{3, 2, 1, 5, 6, 4}, 2));               // 5
    }
}
