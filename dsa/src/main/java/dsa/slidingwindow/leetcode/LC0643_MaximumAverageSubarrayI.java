package dsa.slidingwindow.leetcode;

/**
 * LeetCode 643 — Maximum Average Subarray I
 * Difficulty: Easy   Tags: Array, Sliding Window
 * URL: https://leetcode.com/problems/maximum-average-subarray-i/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} consisting of n elements and an integer
 * {@code k}, find a contiguous subarray of length k that has the maximum
 * average value and return this value.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == nums.length</li>
 *   <li>1 &le; k &le; n &le; 10^5</li>
 *   <li>-10^4 &le; nums[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1, 12, -5, -6, 50, 3], k = 4  -> 12.75   ([12, -5, -6, 50])
 *   nums = [5], k = 1                       -> 5.0
 * </pre>
 *
 * <h2>Approach — fixed-size sliding window</h2>
 * Average is monotone in sum for fixed window size, so maximise the sum and
 * divide at the end. Build the initial window sum in O(k), then slide: add
 * the new element and subtract the leaving element in O(1) per step.
 *
 * <h2>Use long to avoid overflow</h2>
 * For the constraints, an int sum is safe ({@code 10^5 * 10^4 = 10^9}), but
 * accumulating into a {@code long} costs nothing and keeps the habit for
 * tighter problems.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == n — answer is the average of the whole array.</li>
 *   <li>All negative — the "max" is the least negative average; algorithm
 *       still works since we initialise best to the first window's sum.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0643_MaximumAverageSubarrayI
 * </pre>
 */
public class LC0643_MaximumAverageSubarrayI {

    /** Fixed window — O(n). */
    public static double findMaxAverage(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        long best = sum;
        for (int right = k; right < nums.length; right++) {
            sum += nums[right] - nums[right - k];
            best = Math.max(best, sum);
        }
        return best / (double) k;
    }

    public static void main(String[] args) {
        System.out.println(findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4)); // 12.75
        System.out.println(findMaxAverage(new int[]{5}, 1));                     // 5.0
        System.out.println(findMaxAverage(new int[]{-1}, 1));                    // -1.0
        System.out.println(findMaxAverage(new int[]{0, 4, 0, 3, 2}, 1));         // 4.0
    }
}
