package dsa.dp;

import java.util.Arrays;

/**
 * Dynamic Programming — patterns for coding interviews.
 *
 * Run: java dsa.dp.DpGuide
 *
 * The DP recipe in three questions:
 *   1) What is a subproblem? Usually dp[i] (1D) or dp[i][j] (2D).
 *   2) What is the recurrence? How does dp[i] depend on earlier states?
 *   3) What is the base case? Where do we start?
 *
 * Memoization (top-down) is easier to write; tabulation (bottom-up) is
 * usually faster and avoids stack risk.
 */
public class DpGuide {

    // ---------------------------------------------------------------------
    // 1) Top-down memoization — Fibonacci.
    // ---------------------------------------------------------------------
    // Without memo: O(2^n). With: O(n).
    static int fibMemo(int n) {
        Integer[] memo = new Integer[n + 1];
        return fibMemoHelper(n, memo);
    }

    private static int fibMemoHelper(int n, Integer[] memo) {
        if (n <= 1) return n;
        if (memo[n] != null) return memo[n];
        return memo[n] = fibMemoHelper(n - 1, memo) + fibMemoHelper(n - 2, memo);
    }

    // ---------------------------------------------------------------------
    // 2) Bottom-up tabulation — same Fibonacci, O(n) time, O(1) space.
    // ---------------------------------------------------------------------
    static int fibTab(int n) {
        if (n <= 1) return n;
        int prev2 = 0, prev1 = 1;
        for (int i = 2; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    // ---------------------------------------------------------------------
    // 3) 1D DP — House Robber.
    // ---------------------------------------------------------------------
    // Recurrence: dp[i] = max(dp[i-1], dp[i-2] + nums[i]) — skip or rob current house.
    static int rob(int[] nums) {
        int prev2 = 0, prev1 = 0;
        for (int n : nums) {
            int curr = Math.max(prev1, prev2 + n);
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    // ---------------------------------------------------------------------
    // 4) 1D DP, unbounded knapsack — Coin Change (min coins to make amount).
    // ---------------------------------------------------------------------
    // dp[a] = min coins to make amount a; dp[0] = 0; else dp[a] = 1 + min(dp[a - c]).
    static int coinChange(int[] coins, int amount) {
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

    // ---------------------------------------------------------------------
    // 5) 2D DP — Longest Common Subsequence (LCS).
    // ---------------------------------------------------------------------
    // dp[i][j] = LCS of t1[0..i) and t2[0..j).
    //   match: dp[i][j] = dp[i-1][j-1] + 1
    //   miss:  dp[i][j] = max(dp[i-1][j], dp[i][j-1])
    static int lcs(String t1, String t2) {
        int m = t1.length(), n = t2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (t1.charAt(i - 1) == t2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    // ---------------------------------------------------------------------
    // 6) State-machine DP — Best Time to Buy/Sell with Cooldown.
    // ---------------------------------------------------------------------
    // Three states: hold (own stock), sold (just sold, in cooldown), rest (free).
    // Transitions per day given price p:
    //   hold' = max(hold, rest - p)
    //   sold' = hold + p
    //   rest' = max(rest, sold)
    static int maxProfitCooldown(int[] prices) {
        int hold = Integer.MIN_VALUE, sold = 0, rest = 0;
        for (int p : prices) {
            int prevSold = sold;
            sold = hold + p;
            hold = Math.max(hold, rest - p);
            rest = Math.max(rest, prevSold);
        }
        return Math.max(sold, rest);
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("fibMemo(10)         = " + fibMemo(10));                       // 55
        System.out.println("fibTab(10)          = " + fibTab(10));                        // 55
        System.out.println("rob([2,7,9,3,1])    = " + rob(new int[]{2, 7, 9, 3, 1}));     // 12
        System.out.println("coinChange([1,2,5],11) = " + coinChange(new int[]{1, 2, 5}, 11)); // 3
        System.out.println("lcs(abcde, ace)     = " + lcs("abcde", "ace"));               // 3
        System.out.println("maxProfitCooldown   = " + maxProfitCooldown(new int[]{1, 2, 3, 0, 2})); // 3
    }
}
