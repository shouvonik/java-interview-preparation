package dsa.sorting.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 315 — Count of Smaller Numbers After Self
 * Difficulty: Hard   Tags: Array, Binary Indexed Tree, Segment Tree, Merge Sort
 * URL: https://leetcode.com/problems/count-of-smaller-numbers-after-self/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return an array {@code counts} where
 * {@code counts[i]} is the number of smaller elements to the right of
 * {@code nums[i]}.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-10^4 &le; nums[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [5, 2, 6, 1]  ->  [2, 1, 1, 0]
 *     5 -> [2,1] smaller -> 2
 *     2 -> [1]   smaller -> 1
 *     6 -> [1]   smaller -> 1
 *     1 -> []           -> 0
 *   [-1]          ->  [0]
 *   [-1, -1]      ->  [0, 0]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2). For each i, scan right. Times out for n=10^5.
 * <p>
 * <b>Merge sort (canonical)</b> — O(n log n).
 * During the merge step of a stable merge sort, every time we take an element
 * from the right half before exhausting the left half, the remaining left-half
 * elements are all greater than that right-half element and they all sit to
 * the LEFT of it in the original array. But we want "smaller after self", so
 * the perspective flips: when we place a LEFT element into the merged array,
 * the number of right-half elements already placed is the number of elements
 * smaller than this one that originally appeared to its right.
 * <p>
 * Carry indices (not values) through the merge so we can update {@code counts[origIdx]}.
 * <p>
 * <b>Binary Indexed Tree</b> — O(n log n).
 * Coordinate-compress values; iterate right to left; at each step, query the
 * BIT for "how many values smaller than the current have been seen so far".
 * Then update the BIT. Cleaner if you've practised BIT; longer to write from scratch.
 *
 * <h2>Why merge sort works</h2>
 * Each "smaller-after-self" pair (i, j) with i &lt; j and {@code nums[i] > nums[j]}
 * is an <b>inversion</b>. Counting inversions is the textbook merge-sort
 * exercise; this problem asks for inversions split BY origin index.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single element — counts[0] = 0.</li>
 *   <li>All equal — all zeros (we count strictly smaller).</li>
 *   <li>Already descending — counts[i] = n - 1 - i.</li>
 *   <li>Already ascending — all zeros.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0315_CountOfSmallerNumbersAfterSelf
 * </pre>
 */
public class LC0315_CountOfSmallerNumbersAfterSelf {

    /** Merge sort on indices — O(n log n). */
    public static List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] counts = new int[n];
        int[] indices = new int[n];
        for (int i = 0; i < n; i++) indices[i] = i;
        int[] buffer = new int[n];
        mergeSort(nums, indices, buffer, counts, 0, n - 1);
        List<Integer> out = new ArrayList<>(n);
        for (int c : counts) out.add(c);
        return out;
    }

    private static void mergeSort(int[] nums, int[] idx, int[] buf, int[] counts, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(nums, idx, buf, counts, lo, mid);
        mergeSort(nums, idx, buf, counts, mid + 1, hi);
        merge(nums, idx, buf, counts, lo, mid, hi);
    }

    /** Stable merge of two sorted halves by value, tracking right-side overtake counts. */
    private static void merge(int[] nums, int[] idx, int[] buf, int[] counts, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) buf[i] = idx[i];
        int i = lo, j = mid + 1, k = lo;
        int rightTaken = 0;
        while (i <= mid && j <= hi) {
            if (nums[buf[i]] <= nums[buf[j]]) {
                // Placing a left-half index; rightTaken counts how many right-half
                // elements (strictly smaller, hence to-the-right-and-smaller in the
                // original array) have already been placed before it.
                counts[buf[i]] += rightTaken;
                idx[k++] = buf[i++];
            } else {
                rightTaken++;
                idx[k++] = buf[j++];
            }
        }
        while (i <= mid) {
            counts[buf[i]] += rightTaken;
            idx[k++] = buf[i++];
        }
        while (j <= hi) idx[k++] = buf[j++];
    }

    /** Brute force — O(n^2). Reference only. */
    public static List<Integer> countSmallerBrute(int[] nums) {
        List<Integer> out = new ArrayList<>(nums.length);
        for (int i = 0; i < nums.length; i++) {
            int c = 0;
            for (int j = i + 1; j < nums.length; j++) if (nums[j] < nums[i]) c++;
            out.add(c);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(countSmaller(new int[]{5, 2, 6, 1}));      // [2, 1, 1, 0]
        System.out.println(countSmaller(new int[]{-1}));               // [0]
        System.out.println(countSmaller(new int[]{-1, -1}));           // [0, 0]
        System.out.println(countSmaller(new int[]{2, 0, 1}));          // [2, 0, 0]
        System.out.println(countSmaller(new int[]{1, 2, 3, 4}));       // [0, 0, 0, 0]
        System.out.println(countSmaller(new int[]{4, 3, 2, 1}));       // [3, 2, 1, 0]
    }
}
