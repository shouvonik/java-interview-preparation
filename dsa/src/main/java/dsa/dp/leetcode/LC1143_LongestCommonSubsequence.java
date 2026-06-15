package dsa.dp.leetcode;

/**
 * LeetCode 1143 — Longest Common Subsequence
 * Difficulty: Medium   Tags: String, Dynamic Programming
 * URL: https://leetcode.com/problems/longest-common-subsequence/
 *
 * <h2>Problem</h2>
 * Given two strings {@code text1} and {@code text2}, return the length of
 * their longest common subsequence. If there is no common subsequence, return 0.
 *
 * <h2>Approach — 2D DP</h2>
 * {@code dp[i][j]} = LCS length of {@code text1[0..i)} and {@code text2[0..j)}.
 * <ul>
 *   <li>If chars match: {@code dp[i][j] = dp[i-1][j-1] + 1}.</li>
 *   <li>Else: {@code dp[i][j] = max(dp[i-1][j], dp[i][j-1])}.</li>
 * </ul>
 * O(mn) time, O(mn) space; reducible to O(min(m, n)) space with two rolling rows.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC1143_LongestCommonSubsequence
 * </pre>
 */
public class LC1143_LongestCommonSubsequence {

    public static int longestCommonSubsequence(String t1, String t2) {
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

    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abcde", "ace"));   // 3 ("ace")
        System.out.println(longestCommonSubsequence("abc", "abc"));     // 3
        System.out.println(longestCommonSubsequence("abc", "def"));     // 0
    }
}
