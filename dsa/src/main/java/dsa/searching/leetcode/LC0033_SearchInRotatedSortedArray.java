package dsa.searching.leetcode;

/**
 * LeetCode 33 — Search in Rotated Sorted Array
 * Difficulty: Medium   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/search-in-rotated-sorted-array/
 *
 * <h2>Problem</h2>
 * A sorted ascending array of distinct integers was rotated at some pivot
 * unknown to you. Given the rotated array {@code nums} and a {@code target},
 * return its index or -1 if absent. Must run in O(log n).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 5000</li>
 *   <li>-10^4 &le; nums[i], target &le; 10^4</li>
 *   <li>All values of nums are unique.</li>
 *   <li>nums is guaranteed to be a rotated sorted array.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [4, 5, 6, 7, 0, 1, 2], target = 0  ->  4
 *   nums = [4, 5, 6, 7, 0, 1, 2], target = 3  -> -1
 *   nums = [1],                   target = 0  -> -1
 *   nums = [1, 3],                target = 3  ->  1
 * </pre>
 *
 * <h2>Approach</h2>
 * Classic binary search with a key insight: at any midpoint, at least one of
 * the halves {@code [lo..mid]} or {@code [mid..hi]} is sorted (the rotation
 * point lives in the other). Decide which half is sorted, then check whether
 * the target lies inside that sorted half. If it does, search there; otherwise
 * search the other half.
 *
 * <h2>Why this works</h2>
 * The array can be viewed as two sorted runs concatenated. The midpoint always
 * lands in one of them, so {@code nums[lo] <= nums[mid]} cleanly tells us
 * "left half is the older sorted run". A simple bounds check on the sorted
 * half decides which side to recurse on — O(log n) total.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Array not rotated — algorithm still works (left half is always sorted).</li>
 *   <li>Single element — handled by the initial lo == hi loop guard.</li>
 *   <li>Target at lo or hi — comparison uses {@code <=} and {@code &lt;} to
 *       include the endpoint correctly.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0033_SearchInRotatedSortedArray
 * </pre>
 */
public class LC0033_SearchInRotatedSortedArray {

    /** O(log n) modified binary search. */
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;

            if (nums[lo] <= nums[mid]) {                              // left half sorted
                if (nums[lo] <= target && target < nums[mid]) hi = mid - 1;
                else                                           lo = mid + 1;
            } else {                                                  // right half sorted
                if (nums[mid] < target && target <= nums[hi]) lo = mid + 1;
                else                                           hi = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));   // 4
        System.out.println(search(new int[]{4, 5, 6, 7, 0, 1, 2}, 3));   // -1
        System.out.println(search(new int[]{1}, 0));                      // -1
        System.out.println(search(new int[]{1, 3}, 3));                   // 1
        System.out.println(search(new int[]{3, 1}, 1));                   // 1
        System.out.println(search(new int[]{5, 1, 3}, 3));                // 2
    }
}
