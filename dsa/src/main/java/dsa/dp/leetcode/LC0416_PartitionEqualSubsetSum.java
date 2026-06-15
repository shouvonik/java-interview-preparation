package dsa.dp.leetcode;

/**
 * LeetCode 416 — Partition Equal Subset Sum
 * Difficulty: Medium   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/partition-equal-subset-sum/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return true if you can partition the
 * array into two subsets such that the sum of elements in both subsets is equal.
 *
 * <h2>Approach — 0/1 knapsack, boolean form</h2>
 * Total sum S. If S is odd, return false. Otherwise the target is S / 2; ask
 * whether some subset sums to that target. Standard 0/1 knapsack: {@code dp[s]}
 * = "can we hit sum s with a subset?". Iterate over nums; for each n, iterate
 * s from target DOWN TO n (descending so we don't reuse the same element).
 * Set {@code dp[s] |= dp[s - n]}. Final: return {@code dp[target]}.
 *
 * <h2>Why descending</h2>
 * Ascending would treat the current n as if it were available unlimited times
 * (unbounded knapsack); descending preserves the 0/1 semantics.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0416_PartitionEqualSubsetSum
 * </pre>
 */
public class LC0416_PartitionEqualSubsetSum {

    public static boolean canPartition(int[] nums) {
        int sum = 0;
        for (int n : nums) sum += n;
        if ((sum & 1) == 1) return false;
        int target = sum / 2;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
        for (int n : nums) {
            for (int s = target; s >= n; s--) {
                dp[s] |= dp[s - n];
            }
        }
        return dp[target];
    }

    public static void main(String[] args) {
        System.out.println(canPartition(new int[]{1, 5, 11, 5}));   // true ([1,5,5] | [11])
        System.out.println(canPartition(new int[]{1, 2, 3, 5}));    // false
        System.out.println(canPartition(new int[]{1}));             // false
        System.out.println(canPartition(new int[]{2, 2}));          // true
    }
}
