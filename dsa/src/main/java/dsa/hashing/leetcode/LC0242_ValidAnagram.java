package dsa.hashing.leetcode;

/**
 * LeetCode 242 — Valid Anagram
 * Difficulty: Easy   Tags: Hash Table, String, Sorting
 * URL: https://leetcode.com/problems/valid-anagram/
 *
 * <h2>Problem</h2>
 * Given two strings {@code s} and {@code t}, return true if {@code t} is an
 * anagram of {@code s}, false otherwise.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; s.length, t.length &le; 5 * 10^4</li>
 *   <li>s and t consist of lowercase English letters.</li>
 * </ul>
 *
 * <h2>Follow-up</h2>
 * What if the inputs contain Unicode characters? Use a HashMap&lt;Character, Integer&gt;
 * instead of a fixed-size int[26].
 *
 * <h2>Examples</h2>
 * <pre>
 *   s = "anagram", t = "nagaram"  -> true
 *   s = "rat",     t = "car"      -> false
 *   s = "a",       t = "ab"       -> false  (different lengths)
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Sort both</b> — O(n log n) time, O(n) space.
 * Sort each and compare. Simple but slower than necessary.
 * <p>
 * <b>Count array (canonical)</b> — O(n) time, O(1) space (fixed 26 ints).
 * Increment on s, decrement on t (single pass). If lengths differ, return false
 * early; otherwise check all counts are zero.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Different lengths — early return false.</li>
 *   <li>Same string — true.</li>
 *   <li>Single character pair "a", "a" — true.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0242_ValidAnagram
 * </pre>
 */
public class LC0242_ValidAnagram {

    /** Count array — O(n). */
    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }
        for (int c : count) {
            if (c != 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isAnagram("anagram", "nagaram"));  // true
        System.out.println(isAnagram("rat", "car"));           // false
        System.out.println(isAnagram("a", "ab"));              // false
        System.out.println(isAnagram("a", "a"));               // true
    }
}
