package dsa.slidingwindow.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 3 — Longest Substring Without Repeating Characters
 * Difficulty: Medium   Tags: Hash Table, String, Sliding Window
 * URL: https://leetcode.com/problems/longest-substring-without-repeating-characters/
 *
 * <h2>Problem</h2>
 * Given a string {@code s}, find the length of the longest substring without
 * repeating characters.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; s.length &le; 5 * 10^4</li>
 *   <li>s consists of English letters, digits, symbols, and spaces.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   "abcabcbb"  -> 3   ("abc")
 *   "bbbbb"     -> 1   ("b")
 *   "pwwkew"    -> 3   ("wke")
 *   ""          -> 0
 *   " "         -> 1
 * </pre>
 *
 * <h2>Approach — variable window with a Set</h2>
 * Maintain a HashSet of characters currently in the window {@code [left..right]}.
 * Expand by moving {@code right} and adding {@code s[right]}. If a duplicate
 * appears, shrink from the left by removing {@code s[left]} until the duplicate
 * is gone. After each expansion (and shrink), update {@code best}.
 *
 * <h2>Why this is O(n)</h2>
 * Each character is added to the window at most once and removed at most once,
 * so total work is O(n) — each pointer moves only forward.
 *
 * <h2>Speedup with index map</h2>
 * Instead of shrinking one step at a time, store {@code char -> last index} and
 * jump {@code left = max(left, lastIndex(c) + 1)} when you see a duplicate. Same
 * O(n) asymptotic, fewer operations in pathological cases.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty string — 0.</li>
 *   <li>All unique — n.</li>
 *   <li>All same character — 1.</li>
 *   <li>Unicode / spaces — same code works.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0003_LongestSubstringWithoutRepeatingCharacters
 * </pre>
 */
public class LC0003_LongestSubstringWithoutRepeatingCharacters {

    /** Variable window + HashSet — O(n). */
    public static int lengthOfLongestSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int left = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (window.contains(c)) {
                window.remove(s.charAt(left++));
            }
            window.add(c);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
        System.out.println(lengthOfLongestSubstring(""));         // 0
        System.out.println(lengthOfLongestSubstring(" "));        // 1
        System.out.println(lengthOfLongestSubstring("dvdf"));     // 3 ("vdf")
    }
}
