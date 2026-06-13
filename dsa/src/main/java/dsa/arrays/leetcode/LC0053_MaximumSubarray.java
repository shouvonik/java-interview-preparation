package dsa.arrays.leetcode;

/**
 * LeetCode 53 — Maximum Subarray
 * Difficulty: Medium   Tags: Array, Dynamic Programming, Divide and Conquer
 * URL: https://leetcode.com/problems/maximum-subarray/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, find the contiguous subarray (containing
 * at least one number) which has the largest sum and return that sum.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-10^4 &le; nums[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [-2, 1, -3, 4, -1, 2, 1, -5, 4]   -> 6   (subarray [4, -1, 2, 1])
 *   [1]                                -> 1
 *   [5, 4, -1, 7, 8]                   -> 23
 *   [-3, -2, -1]                       -> -1  (must pick at least one)
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * Try every starting index i, sum forward and track the best. Slow.
 * <p>
 * <b>Kadane's algorithm (canonical)</b> — O(n) time, O(1) space.
 * Maintain {@code current} = max sum of a subarray ending at the current index.
 * At each step, either extend the previous best ({@code current + nums[i]}) or
 * start fresh ({@code nums[i]}). The global best is the running maximum of
 * {@code current}.
 *
 * <h2>Why Kadane's works</h2>
 * The optimal subarray ending at i either extends the optimal subarray ending
 * at i-1 (if that sum was positive), or starts fresh at i (if it was negative).
 * So once we know the optimal "ending at i-1", a single comparison gives us
 * "ending at i".
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All negatives — best is the single largest (least negative) element.</li>
 *   <li>Single element — return it.</li>
 *   <li>All positives — answer is the total sum.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0053_MaximumSubarray
 * </pre>
 */
public class LC0053_MaximumSubarray {

    /** Kadane's algorithm — O(n) time, O(1) space. */
    public static int maxSubArray(int[] nums) {
        int best = nums[0];
        int current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    /** Brute force — O(n^2). */
    public static int maxSubArrayBrute(int[] nums) {
        int best = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j < nums.length; j++) {
                sum += nums[j];
                best = Math.max(best, sum);
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        System.out.println(maxSubArray(new int[]{1}));                              // 1
        System.out.println(maxSubArray(new int[]{5, 4, -1, 7, 8}));                 // 23
        System.out.println(maxSubArray(new int[]{-3, -2, -1}));                     // -1
    }
}
