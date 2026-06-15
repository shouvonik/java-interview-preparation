package dsa.dp.exercises;

/**
 * Dynamic Programming — exercises. Try without peeking at the solutions.
 */
public class DpExercises {

    /**
     * Exercise 1 — Climbing Stairs (LeetCode 70).
     * Each step you can climb 1 or 2 stairs. How many distinct ways to reach step n?
     * Example: n = 5 -> 8.
     */
    static int climbStairs(int n) {
        // TODO
        return 0;
    }

    /**
     * Exercise 2 — Unique Paths (LeetCode 62).
     * Robot starts at top-left of an m x n grid, can move only right or down.
     * How many unique paths to bottom-right?
     * Example: m = 3, n = 7 -> 28.
     */
    static int uniquePaths(int m, int n) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Longest Increasing Subsequence (LeetCode 300).
     * Length of the longest strictly increasing subsequence (need not be contiguous).
     * Example: [10,9,2,5,3,7,101,18] -> 4 ([2,3,7,18]).
     * Bonus: get it to O(n log n) with patience sorting.
     */
    static int lengthOfLIS(int[] nums) {
        // TODO
        return 0;
    }

    /**
     * Exercise 4 — Partition Equal Subset Sum (LeetCode 416).
     * Can the array be split into two subsets with equal sums?
     * Hint: 0/1 knapsack. boolean dp[s] = "can we hit sum s?" Iterate over nums.
     * Example: [1,5,11,5] -> true.
     */
    static boolean canPartition(int[] nums) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        System.out.println("climbStairs(5)      = " + climbStairs(5));
        System.out.println("uniquePaths(3, 7)   = " + uniquePaths(3, 7));
        System.out.println("lengthOfLIS         = " + lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        System.out.println("canPartition        = " + canPartition(new int[]{1, 5, 11, 5}));
    }
}
