package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 131 — Palindrome Partitioning
 * Difficulty: Medium   Tags: String, Dynamic Programming, Backtracking
 * URL: https://leetcode.com/problems/palindrome-partitioning/
 *
 * <h2>Problem</h2>
 * Given a string {@code s}, partition it such that every substring of the
 * partition is a palindrome. Return all possible palindrome partitionings.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "aab"  -> [["a","a","b"],["aa","b"]]
 *   "a"    -> [["a"]]
 * </pre>
 *
 * <h2>Approach — backtracking, cut at every valid palindrome prefix</h2>
 * From a given {@code start}, try every {@code end &ge; start}. If
 * {@code s[start..end]} is a palindrome, add it to the path and recurse from
 * {@code end + 1}. When {@code start == n}, snapshot the path.
 *
 * <h2>Optional speedup — precomputed palindrome table</h2>
 * Build {@code isPal[i][j]} in O(n^2) DP up front. Cuts the palindrome check
 * to O(1) per cut. For larger inputs this matters; the inline check is fine
 * for typical sizes.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0131_PalindromePartitioning
 * </pre>
 */
public class LC0131_PalindromePartitioning {

    public static List<List<String>> partition(String s) {
        List<List<String>> out = new ArrayList<>();
        backtrack(s, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(String s, int start, List<String> path, List<List<String>> out) {
        if (start == s.length()) { out.add(new ArrayList<>(path)); return; }
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                path.add(s.substring(start, end + 1));
                backtrack(s, end + 1, path, out);
                path.remove(path.size() - 1);
            }
        }
    }

    private static boolean isPalindrome(String s, int lo, int hi) {
        while (lo < hi) {
            if (s.charAt(lo++) != s.charAt(hi--)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(partition("aab"));   // [[a, a, b], [aa, b]]
        System.out.println(partition("a"));     // [[a]]
    }
}
