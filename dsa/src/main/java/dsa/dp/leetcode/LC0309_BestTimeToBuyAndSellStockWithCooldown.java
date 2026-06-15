package dsa.dp.leetcode;

/**
 * LeetCode 309 — Best Time to Buy and Sell Stock with Cooldown
 * Difficulty: Medium   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
 *
 * <h2>Problem</h2>
 * Given an array of daily prices, find the maximum profit with unlimited
 * transactions, but you must wait one day after selling before buying again
 * (cooldown).
 *
 * <h2>Approach — state machine DP</h2>
 * Three states per day:
 * <ul>
 *   <li>{@code hold} — currently holding a stock.</li>
 *   <li>{@code sold} — just sold today (cooldown next day).</li>
 *   <li>{@code rest} — not holding, free to buy.</li>
 * </ul>
 * Transitions:
 * <ul>
 *   <li>{@code hold' = max(hold, rest - price)}</li>
 *   <li>{@code sold' = hold + price}</li>
 *   <li>{@code rest' = max(rest, sold)}</li>
 * </ul>
 * Answer is {@code max(sold, rest)} on the last day.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0309_BestTimeToBuyAndSellStockWithCooldown
 * </pre>
 */
public class LC0309_BestTimeToBuyAndSellStockWithCooldown {

    public static int maxProfit(int[] prices) {
        int hold = Integer.MIN_VALUE, sold = 0, rest = 0;
        for (int p : prices) {
            int prevSold = sold;
            sold = hold + p;
            hold = Math.max(hold, rest - p);
            rest = Math.max(rest, prevSold);
        }
        return Math.max(sold, rest);
    }

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1, 2, 3, 0, 2}));   // 3
        System.out.println(maxProfit(new int[]{1}));                // 0
        System.out.println(maxProfit(new int[]{6, 1, 3, 2, 4, 7})); // 6
    }
}
