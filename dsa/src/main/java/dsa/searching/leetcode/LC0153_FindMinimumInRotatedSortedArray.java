package dsa.searching.leetcode;

/**
 * LeetCode 153 — Find Minimum in Rotated Sorted Array
 * Difficulty: Medium   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * <h2>Problem</h2>
 * A sorted ascending array of unique integers was rotated at some pivot.
 * Return the minimum element. Must run in O(log n).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == nums.length</li>
 *   <li>1 &le; n &le; 5000</li>
 *   <li>-5000 &le; nums[i] &le; 5000</li>
 *   <li>All elements of nums are unique.</li>
 *   <li>nums is guaranteed to be a rotated sorted array.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3, 4, 5, 1, 2]            -> 1
 *   [4, 5, 6, 7, 0, 1, 2]      -> 0
 *   [11, 13, 15, 17]           -> 11   (no rotation)
 *   [2, 1]                     -> 1
 * </pre>
 *
 * <h2>Approach</h2>
 * Binary search comparing {@code nums[mid]} to {@code nums[hi]}:
 * <ul>
 *   <li>If {@code nums[mid] > nums[hi]}, the minimum lies STRICTLY to the right
 *       of mid (the rotation point is past mid). Set {@code lo = mid + 1}.</li>
 *   <li>Otherwise the minimum is at mid OR to its left. Set {@code hi = mid}.</li>
 * </ul>
 * When lo == hi, that index is the minimum.
 *
 * <h2>Why compare to hi (not lo)?</h2>
 * Comparing {@code nums[mid] > nums[lo]} sounds symmetric but breaks for the
 * unrotated case (e.g. {@code [1, 2, 3]}: nums[mid] > nums[lo], we'd skip the
 * actual min). Comparing to hi gives a clean monotone test: "is the right half
 * fully sorted (including mid)?"
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Not rotated — first comparison sends hi = mid; converges to index 0.</li>
 *   <li>Single element — loop doesn't execute, returns nums[0].</li>
 *   <li>Rotation by exactly 1 — handled (e.g. {@code [2, 1]}).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0153_FindMinimumInRotatedSortedArray
 * </pre>
 */
public class LC0153_FindMinimumInRotatedSortedArray {

    /** O(log n). */
    public static int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi]) lo = mid + 1;
            else                      hi = mid;
        }
        return nums[lo];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{3, 4, 5, 1, 2}));            // 1
        System.out.println(findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));      // 0
        System.out.println(findMin(new int[]{11, 13, 15, 17}));           // 11
        System.out.println(findMin(new int[]{2, 1}));                     // 1
        System.out.println(findMin(new int[]{1}));                        // 1
    }
}
