package dsa.slidingwindow.leetcode;

import java.util.Arrays;

/**
 * LeetCode 567 — Permutation in String
 * Difficulty: Medium   Tags: Hash Table, Two Pointers, String, Sliding Window
 * URL: https://leetcode.com/problems/permutation-in-string/
 *
 * <h2>Problem</h2>
 * Given two strings {@code s1} and {@code s2}, return true if {@code s2}
 * contains a permutation of {@code s1}, i.e. one of {@code s1}'s permutations
 * is a substring of {@code s2}.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; s1.length, s2.length &le; 10^4</li>
 *   <li>Strings consist of lowercase English letters.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   s1 = "ab",  s2 = "eidbaooo"  -> true   ("ba" is a permutation of "ab")
 *   s1 = "ab",  s2 = "eidboaoo"  -> false
 *   s1 = "adc", s2 = "dcda"      -> true
 * </pre>
 *
 * <h2>Approach — fixed-size window + char-count comparison</h2>
 * A permutation of s1 has the same length and character-multiset as s1.
 * <ol>
 *   <li>Build a 26-int count array from s1 — the "target" multiset.</li>
 *   <li>Slide a window of size {@code s1.length()} over s2, maintaining its
 *       own 26-int count array.</li>
 *   <li>If at any window position the two arrays are equal, return true.</li>
 * </ol>
 *
 * <h2>Why fixed-size works</h2>
 * Any permutation of s1 has exactly s1.length characters. So we only need to
 * check substrings of that length. Sliding maintains the count incrementally
 * (one add, one remove per step) — O(n) total.
 *
 * <h2>Optimisation — count "matches" instead of full array compare</h2>
 * Maintain {@code matches} = number of letters (0..25) where target[c] == window[c].
 * Update {@code matches} as you slide. The window is a permutation when
 * {@code matches == 26}. Avoids the O(26) per-step array compare. Both versions
 * are shown below.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>{@code s1.length() > s2.length()} — return false immediately.</li>
 *   <li>s1 equals s2 — return true (every string is a permutation of itself).</li>
 *   <li>Empty strings — not in constraint range but safe: false / true.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0567_PermutationInString
 * </pre>
 */
public class LC0567_PermutationInString {

    /** Fixed window with array equals check — O((n - m + 1) * 26). */
    public static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int m = s1.length();
        int[] target = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < m; i++) {
            target[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }
        if (Arrays.equals(target, window)) return true;
        for (int right = m; right < s2.length(); right++) {
            window[s2.charAt(right) - 'a']++;
            window[s2.charAt(right - m) - 'a']--;
            if (Arrays.equals(target, window)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(checkInclusion("ab", "eidbaooo"));   // true
        System.out.println(checkInclusion("ab", "eidboaoo"));   // false
        System.out.println(checkInclusion("adc", "dcda"));      // true
        System.out.println(checkInclusion("a", "ab"));          // true
        System.out.println(checkInclusion("ab", "a"));          // false
    }
}
