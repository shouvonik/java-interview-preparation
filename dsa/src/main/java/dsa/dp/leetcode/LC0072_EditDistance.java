package dsa.dp.leetcode;

/**
 * LeetCode 72 — Edit Distance
 * Difficulty: Hard   Tags: String, Dynamic Programming
 * URL: https://leetcode.com/problems/edit-distance/
 *
 * <h2>Problem</h2>
 * Given two strings {@code word1} and {@code word2}, return the minimum number
 * of operations to convert {@code word1} into {@code word2}. Allowed: insert,
 * delete, or replace a character.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "horse", "ros"      -> 3   (replace h->r, remove r, remove e)
 *   "intention", "execution" -> 5
 * </pre>
 *
 * <h2>Approach — 2D DP</h2>
 * Let {@code dp[i][j]} = min operations to convert {@code word1[0..i)} into
 * {@code word2[0..j)}. Transitions:
 * <ul>
 *   <li>If {@code word1[i-1] == word2[j-1]}: {@code dp[i][j] = dp[i-1][j-1]}.</li>
 *   <li>Else: {@code dp[i][j] = 1 + min(dp[i-1][j],  // delete
 *                                          dp[i][j-1],  // insert
 *                                          dp[i-1][j-1] // replace)}.</li>
 * </ul>
 * Base: {@code dp[i][0] = i, dp[0][j] = j} (deleting from / inserting into empty).
 *
 * <h2>Space optimisation</h2>
 * Only the previous row is needed at any time -> O(min(m, n)) space.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0072_EditDistance
 * </pre>
 */
public class LC0072_EditDistance {

    public static int minDistance(String w1, String w2) {
        int m = w1.length(), n = w2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1],
                                Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        System.out.println(minDistance("horse", "ros"));            // 3
        System.out.println(minDistance("intention", "execution"));   // 5
        System.out.println(minDistance("", "abc"));                 // 3
        System.out.println(minDistance("abc", ""));                 // 3
    }
}
