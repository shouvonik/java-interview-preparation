package dsa.twopointers.leetcode;

/**
 * LeetCode 125 — Valid Palindrome
 * Difficulty: Easy   Tags: Two Pointers, String
 * URL: https://leetcode.com/problems/valid-palindrome/
 *
 * <h2>Problem</h2>
 * A phrase is a palindrome if, after converting all uppercase letters into
 * lowercase letters and removing all non-alphanumeric characters, it reads the
 * same forward and backward. Given a string {@code s}, return true if it is a
 * palindrome, or false otherwise.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; s.length &le; 2 * 10^5</li>
 *   <li>s consists only of printable ASCII characters.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   "A man, a plan, a canal: Panama"  -> true
 *   "race a car"                       -> false
 *   " "                                 -> true   (empty after cleaning)
 *   "0P"                                -> false  ('0' and 'p' differ; numbers vs letters)
 * </pre>
 *
 * <h2>Approach — opposite-ends two pointers, skip non-alphanumeric</h2>
 * Two pointers from both ends. At each step, advance them past any
 * non-alphanumeric character. Compare lowercase versions. If they differ,
 * return false. Otherwise both move inward.
 *
 * <h2>Why this avoids building a cleaned string</h2>
 * Building a new string is O(n) extra memory. The two-pointer approach checks
 * the palindrome property in place — O(1) extra space.
 *
 * <h2>Pitfall — alphanumeric not just alphabetic</h2>
 * Digits count: "12321" is a palindrome. {@code Character.isLetterOrDigit}
 * handles both letters and digits correctly. {@code Character.isLetter} alone
 * would skip valid digits.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single character — true.</li>
 *   <li>Only punctuation — empty effective string — true.</li>
 *   <li>Case differences — normalise with {@code Character.toLowerCase}.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0125_ValidPalindrome
 * </pre>
 */
public class LC0125_ValidPalindrome {

    /** Opposite-ends + skip non-alphanumeric — O(n). */
    public static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))  left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                return false;
            }
            left++; right--;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome("A man, a plan, a canal: Panama"));   // true
        System.out.println(isPalindrome("race a car"));                        // false
        System.out.println(isPalindrome(" "));                                  // true
        System.out.println(isPalindrome("0P"));                                 // false
        System.out.println(isPalindrome("12321"));                              // true
    }
}
