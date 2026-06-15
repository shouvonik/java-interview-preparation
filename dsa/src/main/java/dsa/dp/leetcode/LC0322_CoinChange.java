package dsa.dp.leetcode;

import java.util.Arrays;

/**
 * LeetCode 322 — Coin Change
 * Difficulty: Medium   Tags: Array, Dynamic Programming, BFS
 * URL: https://leetcode.com/problems/coin-change/
 *
 * <h2>Problem</h2>
 * You are given an integer array {@code coins} representing denominations and
 * an integer {@code amount}. Return the fewest number of coins to make up the
 * amount, or -1 if impossible. You may use each coin an unlimited number of times.
 *
 * <h2>Approach — bottom-up DP (unbounded knapsack)</h2>
 * {@code dp[a]} = min coins to make amount {@code a}. {@code dp[0] = 0};
 * everywhere else initialise to {@code amount + 1} (sentinel for "impossible").
 * Transition: for each amount {@code a} and each coin {@code c &le; a},
 * {@code dp[a] = min(dp[a], dp[a - c] + 1)}.
 *
 * <h2>Why greedy fails</h2>
 * For arbitrary denominations, greedy (always take the largest coin that fits)
 * fails. E.g. {@code coins=[1,3,4], amount=6}: greedy gives 4+1+1=3 coins, but
 * optimal is 3+3=2 coins.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0322_CoinChange
 * </pre>
 */
public class LC0322_CoinChange {

    public static int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1);
        dp[0] = 0;
        for (int a = 1; a <= amount; a++) {
            for (int c : coins) {
                if (c <= a) dp[a] = Math.min(dp[a], dp[a - c] + 1);
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        System.out.println(coinChange(new int[]{1, 2, 5}, 11));   // 3 (5+5+1)
        System.out.println(coinChange(new int[]{2}, 3));           // -1
        System.out.println(coinChange(new int[]{1}, 0));           // 0
        System.out.println(coinChange(new int[]{1, 3, 4}, 6));     // 2 (3+3)
    }
}
