package dsa.greedy.leetcode;

/**
 * LeetCode 122 — Best Time to Buy and Sell Stock II
 * Difficulty: Medium   Tags: Array, Dynamic Programming, Greedy
 * URL: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
 *
 * <h2>Problem</h2>
 * You are given prices of a stock per day. You may complete as many
 * transactions as you like (multiple buys and sells), but cannot hold more
 * than one share at a time. Return the maximum profit.
 *
 * <h2>Approach — greedy: sum all positive deltas</h2>
 * For each adjacent pair {@code (prices[i-1], prices[i])}, if {@code prices[i]
 * > prices[i-1]}, capture that profit. This is equivalent to buying every
 * local minimum and selling every local maximum (overlapping intervals collapse
 * to the same total profit).
 *
 * <h2>Why this beats DP</h2>
 * No constraint on number of transactions makes the state machine collapse:
 * any rising step is pure profit, any falling step costs nothing if you sat out.
 * Greedy captures it directly in O(n).
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0122_BestTimeToBuyAndSellStockII
 * </pre>
 */
public class LC0122_BestTimeToBuyAndSellStockII {

    public static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        }
        return profit;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4}));   // 7 ((5-1)+(6-3))
        System.out.println(maxProfit(new int[]{1, 2, 3, 4, 5}));      // 4
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));      // 0
    }
}
