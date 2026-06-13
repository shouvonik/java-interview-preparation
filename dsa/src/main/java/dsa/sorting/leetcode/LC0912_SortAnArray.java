package dsa.sorting.leetcode;

/**
 * LeetCode 912 — Sort an Array
 * Difficulty: Medium   Tags: Array, Divide and Conquer, Sorting, Merge Sort, Quick Sort
 * URL: https://leetcode.com/problems/sort-an-array/
 *
 * <h2>Problem</h2>
 * Given an array of integers {@code nums}, sort the array in ascending order
 * and return it. You must implement the sort yourself — no built-in
 * library sort. Must run in O(n log n) and use the smallest possible space.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 5 * 10^4</li>
 *   <li>-5 * 10^4 &le; nums[i] &le; 5 * 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [5, 2, 3, 1]        -> [1, 2, 3, 5]
 *   [5, 1, 1, 2, 0, 0]  -> [0, 0, 1, 1, 2, 5]
 *   [1]                  -> [1]
 * </pre>
 *
 * <h2>Approach — Merge sort</h2>
 * Predictable O(n log n) worst case, stable. Uses O(n) auxiliary space, which
 * for this constraint is fine.
 * <p>
 * <b>Why not quicksort?</b> LeetCode 912 historically had adversarial test
 * cases that drove naive quicksort to O(n^2) worst case. Quicksort with a
 * random pivot (or median-of-three) passes; merge sort is the safe default.
 *
 * <h2>Merge sort phases</h2>
 * 1. Divide: split into halves around mid.
 * 2. Conquer: recursively sort each half.
 * 3. Combine: merge two sorted halves into one.
 * <p>
 * The merge step is the only non-trivial code — walk two pointers and emit
 * the smaller of the two current candidates. Using {@code <=} keeps the sort
 * stable (equal elements retain input order).
 *
 * <h2>Why reuse a buffer</h2>
 * Allocating a new buffer per recursion level wastes work. One buffer of size
 * n, reused, keeps space at O(n).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single element — base case returns immediately.</li>
 *   <li>Duplicates — handled; sort is stable.</li>
 *   <li>Negative numbers — comparisons work without modification.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0912_SortAnArray
 * </pre>
 */
public class LC0912_SortAnArray {

    /** In-place merge sort — O(n log n) time, O(n) space. */
    public static int[] sortArray(int[] nums) {
        if (nums.length <= 1) return nums;
        int[] buffer = new int[nums.length];
        mergeSort(nums, buffer, 0, nums.length - 1);
        return nums;
    }

    private static void mergeSort(int[] arr, int[] buf, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, buf, lo, mid);
        mergeSort(arr, buf, mid + 1, hi);
        merge(arr, buf, lo, mid, hi);
    }

    private static void merge(int[] arr, int[] buf, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) buf[i] = arr[i];
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (buf[i] <= buf[j]) arr[k++] = buf[i++];     // <= keeps it stable
            else                  arr[k++] = buf[j++];
        }
        while (i <= mid) arr[k++] = buf[i++];
        // The remaining right-side elements are already in place.
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(sortArray(new int[]{5, 2, 3, 1})));
        // [1, 2, 3, 5]

        System.out.println(java.util.Arrays.toString(sortArray(new int[]{5, 1, 1, 2, 0, 0})));
        // [0, 0, 1, 1, 2, 5]

        System.out.println(java.util.Arrays.toString(sortArray(new int[]{1})));
        // [1]

        System.out.println(java.util.Arrays.toString(sortArray(new int[]{-1, -3, -2, 0, 4})));
        // [-3, -2, -1, 0, 4]
    }
}
