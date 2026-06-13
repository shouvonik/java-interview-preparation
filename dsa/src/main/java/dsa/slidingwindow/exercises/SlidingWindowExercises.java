package dsa.slidingwindow.exercises;

/**
 * Sliding Window — exercises.
 */
public class SlidingWindowExercises {

    /**
     * Exercise 1 — Maximum Average Subarray I (LeetCode 643).
     * Return the maximum average of any contiguous subarray of size k.
     * Example: nums=[1,12,-5,-6,50,3], k=4 -> 12.75
     */
    static double findMaxAverage(int[] nums, int k) {
        // TODO
        return 0.0;
    }

    /**
     * Exercise 2 — Longest Repeating Character Replacement (LeetCode 424).
     * You can replace at most k characters. Return the length of the longest
     * substring containing the same letter after replacements.
     * Hint: window is valid if (windowLen - mostFrequentCharCount) <= k.
     * Example: s="AABABBA", k=1 -> 4
     */
    static int characterReplacement(String s, int k) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Permutation in String (LeetCode 567).
     * Return true if s2 contains a permutation of s1 as a substring.
     * Hint: fixed window of size s1.length(), compare counts.
     * Example: s1="ab", s2="eidbaooo" -> true
     */
    static boolean checkInclusion(String s1, String s2) {
        // TODO
        return false;
    }

    /**
     * Exercise 4 — Minimum Window Substring (LeetCode 76, hard).
     * Smallest substring of s that contains every char of t (with multiplicity).
     * Example: s="ADOBECODEBANC", t="ABC" -> "BANC"
     */
    static String minWindow(String s, String t) {
        // TODO
        return "";
    }

    public static void main(String[] args) {
        System.out.println("findMaxAverage = " + findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4));
        System.out.println("characterReplacement = " + characterReplacement("AABABBA", 1));
        System.out.println("checkInclusion = " + checkInclusion("ab", "eidbaooo"));
        System.out.println("minWindow = " + minWindow("ADOBECODEBANC", "ABC"));
    }
}
