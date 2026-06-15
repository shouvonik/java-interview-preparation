package dsa.greedy;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Greedy — locally-optimal choices that compose to globally-optimal answers.
 *
 * Run: java dsa.greedy.Greedy
 *
 * Greedy works when a problem has the exchange-argument property: any optimal
 * solution can be transformed into the greedy one without making it worse.
 * Cross-check with a tiny adversarial example before committing to greedy.
 */
public class Greedy {

    // ---------------------------------------------------------------------
    // 1) Activity selection — max non-overlapping intervals, sort by END.
    // ---------------------------------------------------------------------
    // Why end and not start? Picking the interval that ends earliest leaves
    // the most room for what follows.
    static int maxNonOverlapping(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int count = 0, end = Integer.MIN_VALUE;
        for (int[] iv : intervals) {
            if (iv[0] >= end) {
                count++;
                end = iv[1];
            }
        }
        return count;
    }

    // ---------------------------------------------------------------------
    // 2) Best Time to Buy/Sell Stock II — sum positive deltas.
    // ---------------------------------------------------------------------
    static int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) profit += prices[i] - prices[i - 1];
        }
        return profit;
    }

    // ---------------------------------------------------------------------
    // 3) Jump Game — track farthest reach.
    // ---------------------------------------------------------------------
    static boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) return false;
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }

    // ---------------------------------------------------------------------
    // 4) When greedy FAILS — coin change with arbitrary denominations.
    // ---------------------------------------------------------------------
    // Greedy: always pick the largest coin that fits.
    // For coins=[1,3,4], target=6: greedy picks 4+1+1 = 3 coins.
    // Optimal: 3+3 = 2 coins. So coin change is DP, not greedy.
    static int coinChangeGreedy(int[] coins, int amount) {
        int[] sorted = coins.clone();
        Arrays.sort(sorted);
        int count = 0, remaining = amount;
        for (int i = sorted.length - 1; i >= 0; i--) {
            while (remaining >= sorted[i]) {
                remaining -= sorted[i];
                count++;
            }
        }
        return remaining == 0 ? count : -1;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // Sort by end -> pick (1,3), (4,6) -> 2.
        int[][] meetings = {{1, 3}, {2, 5}, {4, 6}};
        System.out.println("maxNonOverlapping = " + maxNonOverlapping(meetings));    // 2

        System.out.println("maxProfit (BSell II) = " + maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7

        System.out.println("canJump([2,3,1,1,4]) = " + canJump(new int[]{2, 3, 1, 1, 4})); // true
        System.out.println("canJump([3,2,1,0,4]) = " + canJump(new int[]{3, 2, 1, 0, 4})); // false

        // Greedy is wrong here!
        System.out.println("greedy coinChange([1,3,4], 6) = " + coinChangeGreedy(new int[]{1, 3, 4}, 6)); // 3
        System.out.println("                     optimal = 2 (3+3)");
    }
}
