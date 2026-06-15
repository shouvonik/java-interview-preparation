package dsa.dp.leetcode;

/**
 * LeetCode 5 — Longest Palindromic Substring
 * Difficulty: Medium   Tags: Two Pointers, String, Dynamic Programming
 * URL: https://leetcode.com/problems/longest-palindromic-substring/
 *
 * <h2>Problem</h2>
 * Given a string s, return the longest palindromic substring in s.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "babad"  -> "bab"  (or "aba")
 *   "cbbd"   -> "bb"
 * </pre>
 *
 * <h2>Approach — expand around centre (O(n^2))</h2>
 * Every palindrome has a centre. There are 2n - 1 candidate centres:
 * each character (odd length) and each gap (even length). For each centre,
 * expand outward while characters match; remember the longest.
 * <p>
 * Simpler and uses O(1) space, vs. the O(n^2)-space DP approach.
 *
 * <h2>Manacher's algorithm</h2>
 * Optimal O(n), but very involved. Rarely required for interviews.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0005_LongestPalindromicSubstring
 * </pre>
 */
public class LC0005_LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        int start = 0, len = 0;
        for (int i = 0; i < s.length(); i++) {
            int l1 = expand(s, i, i);
            int l2 = expand(s, i, i + 1);
            int best = Math.max(l1, l2);
            if (best > len) {
                len = best;
                start = i - (best - 1) / 2;
            }
        }
        return s.substring(start, start + len);
    }

    private static int expand(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--; right++;
        }
        return right - left - 1;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));   // bab or aba
        System.out.println(longestPalindrome("cbbd"));    // bb
        System.out.println(longestPalindrome("a"));       // a
    }
}
