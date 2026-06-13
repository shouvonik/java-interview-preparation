package dsa.arrays.leetcode;

/**
 * LeetCode 121 — Best Time to Buy and Sell Stock
 * Difficulty: Easy   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 *
 * <h2>Problem</h2>
 * You are given an array {@code prices} where {@code prices[i]} is the price
 * of a given stock on day i. You may complete at most one transaction (buy
 * once and sell once, sell strictly after buy). Return the maximum profit
 * achievable; if no profit is possible, return 0.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; prices.length &le; 10^5</li>
 *   <li>0 &le; prices[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [7, 1, 5, 3, 6, 4]   -> 5   (buy day 1 at 1, sell day 4 at 6)
 *   [7, 6, 4, 3, 1]      -> 0   (prices only go down)
 *   [2, 4, 1]            -> 2   (buy at 2, sell at 4)
 *   [1, 2]               -> 1
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * Try every (buy, sell) pair. Quadratic, fails for n = 10^5.
 * <p>
 * <b>Single pass (canonical)</b> — O(n) time, O(1) space.
 * Track the minimum price seen so far. For each new day, the best profit
 * achievable selling today is {@code price - minSoFar}. Keep the running max.
 *
 * <h2>Why a single pass works</h2>
 * The best sell at day i can only pair with the cheapest buy in days {@code 0..i-1}
 * (or i itself, giving 0 profit). Tracking the running min while scanning gives
 * the optimal buy day for every possible sell day in O(1) per step.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Strictly decreasing prices — answer is 0, no transaction is best.</li>
 *   <li>All equal — answer is 0.</li>
 *   <li>Single day — answer is 0 (must buy and sell, can't sell same day after buy).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0121_BestTimeToBuyAndSellStock
 * </pre>
 */
public class LC0121_BestTimeToBuyAndSellStock {

    /** Single pass — O(n) time, O(1) space. */
    public static int maxProfit(int[] prices) {
        int minSoFar = Integer.MAX_VALUE;
        int best = 0;
        for (int p : prices) {
            minSoFar = Math.min(minSoFar, p);
            best = Math.max(best, p - minSoFar);
        }
        return best;
    }

    /** Brute force — O(n^2). */
    public static int maxProfitBrute(int[] prices) {
        int best = 0;
        for (int buy = 0; buy < prices.length; buy++) {
            for (int sell = buy + 1; sell < prices.length; sell++) {
                best = Math.max(best, prices[sell] - prices[buy]);
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 5
        System.out.println(maxProfit(new int[]{7, 6, 4, 3, 1}));    // 0
        System.out.println(maxProfit(new int[]{2, 4, 1}));          // 2
        System.out.println(maxProfit(new int[]{1, 2}));             // 1
        System.out.println(maxProfit(new int[]{1}));                // 0
    }
}
