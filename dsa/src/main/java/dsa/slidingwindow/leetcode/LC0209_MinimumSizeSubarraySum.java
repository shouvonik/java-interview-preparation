package dsa.slidingwindow.leetcode;

/**
 * LeetCode 209 — Minimum Size Subarray Sum
 * Difficulty: Medium   Tags: Array, Binary Search, Sliding Window, Prefix Sum
 * URL: https://leetcode.com/problems/minimum-size-subarray-sum/
 *
 * <h2>Problem</h2>
 * Given an array of positive integers {@code nums} and a positive integer
 * {@code target}, return the minimal length of a contiguous subarray of which
 * the sum is &ge; {@code target}. If no such subarray exists, return 0.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; target &le; 10^9</li>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>1 &le; nums[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   target = 7, nums = [2,3,1,2,4,3]  -> 2    ([4,3])
 *   target = 4, nums = [1,4,4]         -> 1    ([4])
 *   target = 11, nums = [1,1,1,1,1,1,1,1]  -> 0
 * </pre>
 *
 * <h2>Approach — variable window, shrink-while-valid</h2>
 * Expand {@code right}, accumulating into {@code sum}. While {@code sum >= target},
 * record candidate length and shrink from {@code left}. Returns the best length
 * seen, or 0 if no window ever became valid.
 *
 * <h2>Why this works because values are positive</h2>
 * Positivity ensures monotonicity: once a window sums to &ge; target, shrinking
 * from the left strictly decreases the sum. If values could be negative, this
 * shrink step would be invalid (you'd lose validity but the sum could swing
 * back up later).
 *
 * <h2>Alternative — binary search on prefix sums</h2>
 * Build prefix sums; for each i, binary search for the smallest j such that
 * {@code prefix[j] - prefix[i] >= target}. O(n log n) — sliding window O(n)
 * is better.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Whole array still smaller than target — return 0.</li>
 *   <li>Single element &ge; target — return 1.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0209_MinimumSizeSubarraySum
 * </pre>
 */
public class LC0209_MinimumSizeSubarraySum {

    /** Variable window with shrink-while-valid — O(n). */
    public static int minSubArrayLen(int target, int[] nums) {
        int left = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    public static void main(String[] args) {
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));            // 2
        System.out.println(minSubArrayLen(4, new int[]{1, 4, 4}));                      // 1
        System.out.println(minSubArrayLen(11, new int[]{1, 1, 1, 1, 1, 1, 1, 1}));      // 0
        System.out.println(minSubArrayLen(15, new int[]{1, 2, 3, 4, 5}));               // 5
    }
}
