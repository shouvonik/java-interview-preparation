package dsa.slidingwindow.leetcode;

/**
 * LeetCode 76 — Minimum Window Substring
 * Difficulty: Hard   Tags: Hash Table, String, Sliding Window
 * URL: https://leetcode.com/problems/minimum-window-substring/
 *
 * <h2>Problem</h2>
 * Given two strings {@code s} and {@code t} of lengths m and n, return the
 * minimum window substring of {@code s} that contains every character of
 * {@code t} (including duplicates). If no such substring exists, return "".
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>m == s.length</li>
 *   <li>n == t.length</li>
 *   <li>1 &le; m, n &le; 10^5</li>
 *   <li>s and t consist of uppercase and lowercase English letters.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   s = "ADOBECODEBANC", t = "ABC"  -> "BANC"
 *   s = "a",             t = "a"    -> "a"
 *   s = "a",             t = "aa"   -> ""    (not enough 'a')
 * </pre>
 *
 * <h2>Approach — variable window with two counters</h2>
 * <ol>
 *   <li>Build a {@code need[]} array (or HashMap) of required char counts from t.</li>
 *   <li>Maintain a {@code required} integer = total characters still missing.</li>
 *   <li>Expand {@code right}: decrement {@code need[c]}; if it was &gt; 0 before
 *       (i.e. we genuinely needed this char), decrement {@code required}.</li>
 *   <li>When {@code required == 0}, window is valid. Try to shrink from {@code left}:
 *       increment {@code need[s[left]]}; if it becomes &gt; 0, increment
 *       {@code required} (we now miss that char). Update best window during shrink.</li>
 * </ol>
 *
 * <h2>Why {@code need[]} goes negative</h2>
 * We let need go negative to track surplus chars. This way, removing a "surplus"
 * char from the window just brings need toward 0; only when it crosses back
 * above 0 do we register an actual deficit.
 *
 * <h2>Why update best DURING the inner shrink</h2>
 * For minimum-window problems, the smallest valid window appears as we shrink.
 * For maximum-valid problems (LC3), best is updated AFTER the while loop,
 * because the window grows.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>s shorter than t — return "".</li>
 *   <li>t has duplicate chars — need[] handles this; required counts each copy.</li>
 *   <li>Multiple equally short windows — any is acceptable; the algorithm
 *       returns the first one found.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0076_MinimumWindowSubstring
 * </pre>
 */
public class LC0076_MinimumWindowSubstring {

    /** Variable window with need-array + required counter — O(m + n). */
    public static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int required = t.length();                       // chars still missing

        int left = 0;
        int bestLen = Integer.MAX_VALUE;
        int bestStart = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) required--;
            need[c]--;                                   // can go negative

            while (required == 0) {
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                char lc = s.charAt(left);
                need[lc]++;
                if (need[lc] > 0) required++;             // crossed back into deficit
                left++;
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    public static void main(String[] args) {
        System.out.println(minWindow("ADOBECODEBANC", "ABC"));  // BANC
        System.out.println(minWindow("a", "a"));                 // a
        System.out.println(minWindow("a", "aa"));                // ""
        System.out.println(minWindow("ab", "b"));                // b
    }
}
