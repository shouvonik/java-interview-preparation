package dsa.searching.leetcode;

/**
 * LeetCode 35 — Search Insert Position
 * Difficulty: Easy   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/search-insert-position/
 *
 * <h2>Problem</h2>
 * Given a sorted array of distinct integers and a target value, return the
 * index if the target is found. If not, return the index where it would be
 * inserted to keep the array sorted. Must run in O(log n).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^4</li>
 *   <li>-10^4 &le; nums[i], target &le; 10^4</li>
 *   <li>nums sorted ascending, no duplicates.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1, 3, 5, 6], target = 5  -> 2
 *   nums = [1, 3, 5, 6], target = 2  -> 1
 *   nums = [1, 3, 5, 6], target = 7  -> 4
 *   nums = [1, 3, 5, 6], target = 0  -> 0
 * </pre>
 *
 * <h2>Approach</h2>
 * This is the classic <b>lower bound</b>: the first index {@code i} such that
 * {@code nums[i] >= target}. Half-open binary search on {@code [lo, hi)} with
 * {@code hi = n}; the answer is {@code lo} when the loop exits.
 *
 * <h2>Why lower bound gives the insert position</h2>
 * If target is present, lower bound returns its index (matches case 1).
 * If absent, lower bound returns the first index of something larger than
 * target — which is exactly where you'd insert it to keep order.
 * Off the end (all elements smaller) gives {@code n}.
 *
 * <h2>Template to memorise</h2>
 * <pre>
 *   int lo = 0, hi = n;        // half-open: hi = n, not n - 1
 *   while (lo &lt; hi) {           // strict &lt; here
 *       int mid = lo + (hi - lo) / 2;
 *       if (nums[mid] &lt; target) lo = mid + 1;
 *       else                     hi = mid;
 *   }
 *   return lo;
 * </pre>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0035_SearchInsertPosition
 * </pre>
 */
public class LC0035_SearchInsertPosition {

    /** Lower-bound binary search — O(log n). */
    public static int searchInsert(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) lo = mid + 1;
            else                    hi = mid;
        }
        return lo;
    }

    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 5));   // 2
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 2));   // 1
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 7));   // 4
        System.out.println(searchInsert(new int[]{1, 3, 5, 6}, 0));   // 0
        System.out.println(searchInsert(new int[]{1}, 1));            // 0
        System.out.println(searchInsert(new int[]{1}, 2));            // 1
    }
}
