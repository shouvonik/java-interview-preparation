package dsa.slidingwindow.leetcode;

/**
 * LeetCode 424 — Longest Repeating Character Replacement
 * Difficulty: Medium   Tags: Hash Table, String, Sliding Window
 * URL: https://leetcode.com/problems/longest-repeating-character-replacement/
 *
 * <h2>Problem</h2>
 * You are given a string {@code s} and an integer {@code k}. You can choose
 * any character of the string and change it to any other uppercase English
 * character. You can perform this operation at most {@code k} times. Return
 * the length of the longest substring containing the same letter you can get
 * after performing the above operations.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; s.length &le; 10^5</li>
 *   <li>s consists of only uppercase English letters.</li>
 *   <li>0 &le; k &le; s.length</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   s = "ABAB",   k = 2  -> 4   (replace 2 As with B, or 2 Bs with A)
 *   s = "AABABBA", k = 1 -> 4   ("AABA" or "ABBB")
 * </pre>
 *
 * <h2>Approach — variable window with max-char count</h2>
 * Slide a window. Within it, let {@code maxCount} = highest single-letter
 * frequency. Window is <b>valid</b> when {@code length - maxCount &le; k},
 * because we can convert the "other" {@code length - maxCount} chars into the
 * dominant one using at most k replacements.
 * <p>
 * Expand {@code right} updating counts; if invalid, shrink {@code left}. Always
 * track the best window length seen.
 *
 * <h2>The clever optimisation: don't shrink maxCount</h2>
 * When the window contracts, {@code maxCount} doesn't need to be re-computed
 * accurately. The answer only ever GROWS when a bigger {@code maxCount} is seen.
 * A stale {@code maxCount} can lead to a brief over-estimate of validity, but
 * since {@code best} only updates on length growth, the final answer is
 * unaffected. This trick keeps the algorithm O(n) without expensive recomputation.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == 0 — answer is the longest run of identical letters.</li>
 *   <li>k &ge; s.length — answer is s.length (convert everything).</li>
 *   <li>All identical letters — answer is s.length.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0424_LongestRepeatingCharacterReplacement
 * </pre>
 */
public class LC0424_LongestRepeatingCharacterReplacement {

    /** Variable window — O(n) using stale-maxCount trick. */
    public static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxCount = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            count[idx]++;
            maxCount = Math.max(maxCount, count[idx]);
            while ((right - left + 1) - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(characterReplacement("ABAB", 2));     // 4
        System.out.println(characterReplacement("AABABBA", 1));  // 4
        System.out.println(characterReplacement("A", 0));        // 1
        System.out.println(characterReplacement("AAAA", 2));     // 4
    }
}
