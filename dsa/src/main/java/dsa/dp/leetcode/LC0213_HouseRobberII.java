package dsa.dp.leetcode;

/**
 * LeetCode 213 — House Robber II
 * Difficulty: Medium   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/house-robber-ii/
 *
 * <h2>Problem</h2>
 * Same as House Robber, except houses are arranged in a CIRCLE: the first and
 * last houses are adjacent. Maximize stash without robbing adjacent (cyclic).
 *
 * <h2>Approach — two linear robs</h2>
 * Run the linear House Robber on two slices:
 * <ul>
 *   <li>{@code nums[0..n-2]} — excludes the last house.</li>
 *   <li>{@code nums[1..n-1]} — excludes the first house.</li>
 * </ul>
 * Return the max. This avoids ever robbing both first AND last together.
 *
 * <h2>Edge case</h2>
 * Length 1 — the only house can be robbed.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0213_HouseRobberII
 * </pre>
 */
public class LC0213_HouseRobberII {

    public static int rob(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];
        return Math.max(robLinear(nums, 0, n - 2), robLinear(nums, 1, n - 1));
    }

    private static int robLinear(int[] nums, int lo, int hi) {
        int prev2 = 0, prev1 = 0;
        for (int i = lo; i <= hi; i++) {
            int curr = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        System.out.println(rob(new int[]{2, 3, 2}));        // 3 (can't rob both 2's)
        System.out.println(rob(new int[]{1, 2, 3, 1}));     // 4
        System.out.println(rob(new int[]{1, 2, 3}));        // 3
        System.out.println(rob(new int[]{5}));              // 5
    }
}
