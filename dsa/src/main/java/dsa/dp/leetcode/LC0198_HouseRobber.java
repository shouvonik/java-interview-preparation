package dsa.dp.leetcode;

/**
 * LeetCode 198 — House Robber
 * Difficulty: Medium   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/house-robber/
 *
 * <h2>Problem</h2>
 * You are a professional robber planning to rob houses along a street. Each
 * house has a stash; if you rob two adjacent houses on the same night the
 * police catch you. Return the maximum amount you can rob without alerting
 * the police.
 *
 * <h2>Recurrence</h2>
 * {@code dp[i] = max(dp[i - 1],  dp[i - 2] + nums[i])}.
 * Skip current house, or rob it (excluding the previous one).
 *
 * <h2>Space O(1)</h2>
 * Only the last two values are needed.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0198_HouseRobber
 * </pre>
 */
public class LC0198_HouseRobber {

    public static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int n : nums) {
            int curr = Math.max(prev1, prev2 + n);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{1, 2, 3, 1}));     // 4
        System.out.println(rob(new int[]{2, 7, 9, 3, 1}));  // 12
        System.out.println(rob(new int[]{}));                // 0
        System.out.println(rob(new int[]{5}));               // 5
    }
}
