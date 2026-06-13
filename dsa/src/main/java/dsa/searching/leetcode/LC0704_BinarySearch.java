package dsa.searching.leetcode;

/**
 * LeetCode 704 — Binary Search
 * Difficulty: Easy   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/binary-search/
 *
 * <h2>Problem</h2>
 * Given a sorted (ascending) array of unique integers and a target value,
 * return the index of the target, or -1 if absent. Must run in O(log n).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^4</li>
 *   <li>-10^4 &le; nums[i], target &le; 10^4</li>
 *   <li>All integers in nums are unique.</li>
 *   <li>nums is sorted ascending.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [-1, 0, 3, 5, 9, 12], target = 9   ->  4
 *   nums = [-1, 0, 3, 5, 9, 12], target = 2   -> -1
 *   nums = [5],                  target = 5   ->  0
 *   nums = [5],                  target = -5  -> -1
 * </pre>
 *
 * <h2>The canonical template — closed range [lo, hi]</h2>
 * <pre>
 *   int lo = 0, hi = n - 1;
 *   while (lo &lt;= hi) {                 // &lt;= because hi is inclusive
 *       int mid = lo + (hi - lo) / 2; // overflow-safe
 *       if (arr[mid] == target) return mid;
 *       if (arr[mid] &lt; target) lo = mid + 1;
 *       else                   hi = mid - 1;
 *   }
 *   return -1;
 * </pre>
 *
 * <h2>Why {@code lo + (hi - lo) / 2} and not {@code (lo + hi) / 2}?</h2>
 * For {@code lo} and {@code hi} both close to {@code Integer.MAX_VALUE}, the
 * sum overflows to a negative number, giving a wrong (or out-of-bounds) mid.
 * The {@code lo + (hi - lo) / 2} form never overflows in this range.
 *
 * <h2>Pitfalls</h2>
 * <ul>
 *   <li>Mixing {@code &lt;= hi} with {@code hi = mid} (or {@code &lt; hi} with
 *       {@code hi = mid - 1}) — infinite loop. Pick one template.</li>
 *   <li>For "first / last occurrence with duplicates", use lower_bound /
 *       upper_bound instead.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0704_BinarySearch
 * </pre>
 */
public class LC0704_BinarySearch {

    /** Closed-range binary search — O(log n). */
    public static int search(int[] nums, int target) {
        int lo = 0, hi = nums.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[mid] < target) lo = mid + 1;
            else                    hi = mid - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 9));     // 4
        System.out.println(search(new int[]{-1, 0, 3, 5, 9, 12}, 2));     // -1
        System.out.println(search(new int[]{5}, 5));                       // 0
        System.out.println(search(new int[]{5}, -5));                      // -1
        System.out.println(search(new int[]{1, 2, 3, 4, 5}, 1));           // 0
        System.out.println(search(new int[]{1, 2, 3, 4, 5}, 5));           // 4
    }
}
