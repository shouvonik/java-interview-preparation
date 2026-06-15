package dsa.dp.leetcode;

import java.util.Arrays;

/**
 * LeetCode 300 — Longest Increasing Subsequence
 * Difficulty: Medium   Tags: Array, Binary Search, DP
 * URL: https://leetcode.com/problems/longest-increasing-subsequence/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return the length of the longest
 * strictly increasing subsequence (not necessarily contiguous).
 *
 * <h2>Examples</h2>
 * <pre>
 *   [10,9,2,5,3,7,101,18]  -> 4   ([2,3,7,18] or [2,5,7,18] or [2,5,7,101])
 *   [0,1,0,3,2,3]           -> 4
 *   [7,7,7,7,7,7,7]         -> 1
 * </pre>
 *
 * <h2>Approach 1 — O(n^2) DP</h2>
 * {@code dp[i]} = length of LIS ending at i. {@code dp[i] = 1 + max(dp[j]) for j &lt; i, nums[j] &lt; nums[i]}.
 *
 * <h2>Approach 2 — patience sorting / binary search (O(n log n))</h2>
 * Maintain a sorted array {@code tails} where {@code tails[k]} = smallest
 * possible tail value of any increasing subsequence of length {@code k + 1}.
 * For each {@code x}, binary-search for the first element &ge; {@code x};
 * replace it (or append if none). Final length = tails.length.
 *
 * <h2>Why this works (Approach 2)</h2>
 * Each tail represents the BEST candidate to extend a length-k IS. Keeping it
 * minimal leaves the most room for future numbers to extend. The actual
 * sequence is NOT necessarily in tails — only its length is correct.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0300_LongestIncreasingSubsequence
 * </pre>
 */
public class LC0300_LongestIncreasingSubsequence {

    /** Patience sorting + binary search — O(n log n). */
    public static int lengthOfLIS(int[] nums) {
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

    public static void main(String[] args) {
        System.out.println(lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // 4
        System.out.println(lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));            // 4
        System.out.println(lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));         // 1
    }
}
