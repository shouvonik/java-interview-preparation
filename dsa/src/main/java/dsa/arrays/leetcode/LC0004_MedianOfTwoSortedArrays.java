package dsa.arrays.leetcode;

/**
 * LeetCode 4 — Median of Two Sorted Arrays
 * Difficulty: Hard   Tags: Array, Binary Search, Divide and Conquer
 * URL: https://leetcode.com/problems/median-of-two-sorted-arrays/
 *
 * <h2>Problem</h2>
 * Given two sorted arrays {@code nums1} and {@code nums2}, return the median
 * of the combined sorted array. The overall runtime must be O(log (m+n)).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>nums1.length == m, nums2.length == n</li>
 *   <li>0 &le; m, n &le; 1000</li>
 *   <li>1 &le; m + n &le; 2000</li>
 *   <li>-10^6 &le; nums[i] &le; 10^6</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums1 = [1, 3], nums2 = [2]        -> 2.0      (merged = [1,2,3], median = 2)
 *   nums1 = [1, 2], nums2 = [3, 4]     -> 2.5      (merged = [1,2,3,4], median = (2+3)/2)
 *   nums1 = [],     nums2 = [1]        -> 1.0
 *   nums1 = [2],    nums2 = []         -> 2.0
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Merge then index</b> — O(m+n) time, O(m+n) space.
 * Build the merged array, return the middle element(s). Easy but doesn't meet
 * the O(log(m+n)) requirement.
 * <p>
 * <b>Binary search on partition (canonical)</b> — O(log min(m, n)) time, O(1) space.
 * Partition each array into a left and right half such that
 * {@code |left| = |right| (+/- 1)} and {@code max(left) <= min(right)} across both.
 * Binary-search the partition index {@code i} in the shorter array; the partition
 * {@code j} in the other is determined by total length. Adjust based on whether
 * the partition is too far left or right.
 *
 * <h2>Why binary search?</h2>
 * Medians depend on the relative order of elements around the split point, not
 * the elements themselves. We can prune half of the search space at each step
 * by comparing the four boundary values: nums1[i-1], nums1[i], nums2[j-1], nums2[j].
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>One array empty — median of the other (handled by sentinel +/- infinity).</li>
 *   <li>All elements of one array are smaller than the other — partition lands at 0 or m.</li>
 *   <li>Odd vs even total length — odd: max of left side; even: avg of max-left + min-right.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0004_MedianOfTwoSortedArrays
 * </pre>
 */
public class LC0004_MedianOfTwoSortedArrays {

    /** Binary search on partition — O(log min(m, n)). */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Always binary-search the shorter array to keep complexity at O(log min(m, n)).
        if (nums1.length > nums2.length) {
            int[] tmp = nums1; nums1 = nums2; nums2 = tmp;
        }
        int m = nums1.length, n = nums2.length;
        int totalLeft = (m + n + 1) / 2;       // size of combined left half
        int lo = 0, hi = m;

        while (lo <= hi) {
            int i = (lo + hi) / 2;             // partition in nums1
            int j = totalLeft - i;             // partition in nums2

            int left1  = (i == 0) ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = (i == m) ? Integer.MAX_VALUE : nums1[i];
            int left2  = (j == 0) ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = (j == n) ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                // Found a valid partition.
                if (((m + n) & 1) == 1) return Math.max(left1, left2);
                return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
            }
            if (left1 > right2) hi = i - 1;    // partition i too far right
            else                lo = i + 1;    // partition i too far left
        }
        throw new IllegalArgumentException("input arrays not sorted");
    }

    /** Merge-then-index — O(m + n), kept for sanity comparison. */
    public static double findMedianSortedArraysBrute(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        int[] merged = new int[m + n];
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) merged[k++] = nums1[i] <= nums2[j] ? nums1[i++] : nums2[j++];
        while (i < m) merged[k++] = nums1[i++];
        while (j < n) merged[k++] = nums2[j++];
        int total = m + n;
        if ((total & 1) == 1) return merged[total / 2];
        return (merged[total / 2 - 1] + merged[total / 2]) / 2.0;
    }

    public static void main(String[] args) {
        System.out.println(findMedianSortedArrays(new int[]{1, 3}, new int[]{2}));         // 2.0
        System.out.println(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4}));      // 2.5
        System.out.println(findMedianSortedArrays(new int[]{}, new int[]{1}));             // 1.0
        System.out.println(findMedianSortedArrays(new int[]{2}, new int[]{}));             // 2.0
        System.out.println(findMedianSortedArrays(new int[]{1, 2, 3}, new int[]{4, 5, 6})); // 3.5
    }
}
