package dsa.dp.exercises.solutions;

import java.util.Arrays;

/**
 * Reference solutions for DpExercises.
 */
public class DpSolutions {

    // Exercise 1 — Climbing Stairs. Fibonacci shape; O(1) space.
    static int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    // Exercise 2 — Unique Paths. dp[i][j] = dp[i-1][j] + dp[i][j-1]; collapse to 1D.
    static int uniquePaths(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    // Exercise 3 — LIS via patience sorting; tails[k] = smallest possible tail of any IS of length k+1.
    static int lengthOfLIS(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int idx = Arrays.binarySearch(tails, 0, size, x);
            if (idx < 0) idx = -(idx + 1);
            tails[idx] = x;
            if (idx == size) size++;
        }
        return size;
    }

    // Exercise 4 — Partition Equal Subset Sum (0/1 knapsack, boolean).
    static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int n : nums) sum += n;
        if ((sum & 1) == 1) return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int n : nums) {
            for (int s = target; s >= n; s--) {            // descending to keep 0/1 semantics
                dp[s] |= dp[s - n];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        System.out.println("climbStairs(5)      = " + climbStairs(5));               // 8
        System.out.println("uniquePaths(3, 7)   = " + uniquePaths(3, 7));            // 28
        System.out.println("lengthOfLIS         = " + lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // 4
        System.out.println("canPartition        = " + canPartition(new int[]{1, 5, 11, 5}));    // true
    }
}
