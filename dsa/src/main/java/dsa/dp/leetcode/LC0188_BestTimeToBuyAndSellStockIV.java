package dsa.dp.leetcode;

/**
 * LeetCode 188 — Best Time to Buy and Sell Stock IV
 * Difficulty: Hard   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/
 *
 * <h2>Problem</h2>
 * You are given an integer array {@code prices} where {@code prices[i]} is the
 * price of a stock on day i, and an integer {@code k}. Find the maximum profit
 * you can achieve with at most {@code k} transactions. You may not engage in
 * multiple transactions simultaneously (must sell before buying again).
 *
 * <h2>Approach — 2D DP over (transactions, day)</h2>
 * {@code dp[t][i]} = max profit using at most t transactions through day i.
 * Transitions:
 * <ul>
 *   <li>Skip day: {@code dp[t][i] = dp[t][i - 1]}.</li>
 *   <li>Sell on day i: for each {@code j &lt; i}, profit = {@code prices[i] - prices[j] + dp[t-1][j]}.</li>
 * </ul>
 * The naive loop is O(k n^2). Maintain {@code bestPrev = max over j of (dp[t-1][j] - prices[j])}
 * to reduce to O(k n).
 *
 * <h2>Edge case: k &ge; n / 2</h2>
 * With k that large, we can essentially trade every up-trending pair — the
 * problem reduces to "sum of all positive deltas". Short-circuit for speed.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0188_BestTimeToBuyAndSellStockIV
 * </pre>
 */
public class LC0188_BestTimeToBuyAndSellStockIV {

    public static int maxProfit(int k, int[] prices) {
        int n = prices.length;
        if (n == 0 || k == 0) return 0;
        if (k >= n / 2) {
            int profit = 0;
            for (int i = 1; i < n; i++) profit += Math.max(0, prices[i] - prices[i - 1]);
            return profit;
        }
        int[][] dp = new int[k + 1][n];
        for (int t = 1; t <= k; t++) {
            int bestPrev = -prices[0];                  // dp[t-1][j] - prices[j], best so far
            for (int i = 1; i < n; i++) {
                dp[t][i] = Math.max(dp[t][i - 1], prices[i] + bestPrev);
                bestPrev = Math.max(bestPrev, dp[t - 1][i] - prices[i]);
            }
        }
        return dp[k][n - 1];
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(2, new int[]{2, 4, 1}));               // 2
        System.out.println(maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));      // 7
        System.out.println(maxProfit(0, new int[]{1, 2, 3}));               // 0
        System.out.println(maxProfit(100, new int[]{1, 2, 1, 5}));          // 5
    }
}
