package dsa.dp.leetcode;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * LeetCode 139 — Word Break
 * Difficulty: Medium   Tags: Hash Table, String, DP, Trie, Memoization
 * URL: https://leetcode.com/problems/word-break/
 *
 * <h2>Problem</h2>
 * Given a string {@code s} and a dictionary of strings {@code wordDict},
 * return true if {@code s} can be segmented into a space-separated sequence
 * of one or more dictionary words.
 *
 * <h2>Examples</h2>
 * <pre>
 *   s = "leetcode", wordDict = ["leet","code"]  -> true
 *   s = "applepenapple", wordDict = ["apple","pen"]  -> true
 *   s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]  -> false
 * </pre>
 *
 * <h2>Approach — bottom-up DP</h2>
 * {@code dp[i]} = true iff {@code s[0..i)} can be segmented. {@code dp[0] = true}.
 * For each i, check every j &lt; i: if {@code dp[j] && wordDict.contains(s[j..i))},
 * set {@code dp[i] = true}.
 *
 * <h2>Optimisation — limit inner loop by max word length</h2>
 * Only j values where {@code i - j &le; maxWordLen} can possibly match. Cuts
 * work when words are short relative to s.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0139_WordBreak
 * </pre>
 */
public class LC0139_WordBreak {

    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>(wordDict);
        int max = 0;
        for (String w : wordDict) max = Math.max(max, w.length());

        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = Math.max(0, i - max); j < i; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        System.out.println(wordBreak("leetcode", List.of("leet", "code")));            // true
        System.out.println(wordBreak("applepenapple", List.of("apple", "pen")));        // true
        System.out.println(wordBreak("catsandog", List.of("cats", "dog", "sand", "and", "cat"))); // false
    }
}
