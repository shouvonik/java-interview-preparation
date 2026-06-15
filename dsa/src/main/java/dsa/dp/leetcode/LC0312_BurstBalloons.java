package dsa.dp.leetcode;

/**
 * LeetCode 312 — Burst Balloons
 * Difficulty: Hard   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/burst-balloons/
 *
 * <h2>Problem</h2>
 * Given {@code n} balloons indexed 0..n-1. {@code nums[i]} is the value of
 * the i-th balloon. Bursting balloon i gives you {@code nums[left] * nums[i]
 * * nums[right]} coins, where {@code left} and {@code right} are i's adjacent
 * indices (out of bounds is treated as 1). Find the maximum coins you can
 * collect by bursting all balloons wisely.
 *
 * <h2>Approach — interval DP</h2>
 * Think of bursting in REVERSE: pick which balloon is burst LAST in a given
 * range {@code (i, j)}. Once chosen, the two sub-ranges become independent.
 * <p>
 * Pad nums with sentinels 1 on both ends. Let {@code dp[i][j]} = max coins
 * from bursting all balloons strictly between i and j. For each k in (i, j),
 * try k as the last burst:
 * {@code dp[i][j] = max over k of (dp[i][k] + nums[i] * nums[k] * nums[j] + dp[k][j])}.
 *
 * <h2>Why "last burst" instead of first</h2>
 * If we picked "first burst", the two sub-ranges would no longer be
 * independent — the neighbours change once you burst. Choosing the LAST one
 * fixes its neighbours as i and j, decoupling the subproblems.
 *
 * <h2>Complexity</h2>
 * O(n^3) time, O(n^2) space.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0312_BurstBalloons
 * </pre>
 */
public class LC0312_BurstBalloons {

    public static int maxCoins(int[] nums) {
        int n = nums.length;
        int[] a = new int[n + 2];
        a[0] = a[n + 1] = 1;
        for (int i = 0; i < n; i++) a[i + 1] = nums[i];
        int[][] dp = new int[n + 2][n + 2];
        for (int len = 2; len < n + 2; len++) {
            for (int i = 0; i + len < n + 2; i++) {
                int j = i + len;
                for (int k = i + 1; k < j; k++) {
                    dp[i][j] = Math.max(dp[i][j],
                        dp[i][k] + a[i] * a[k] * a[j] + dp[k][j]);
                }
            }
        }
        return dp[0][n + 1];
    }

    public static void main(String[] args) {
        System.out.println(maxCoins(new int[]{3, 1, 5, 8}));   // 167
        System.out.println(maxCoins(new int[]{1, 5}));         // 10
    }
}
