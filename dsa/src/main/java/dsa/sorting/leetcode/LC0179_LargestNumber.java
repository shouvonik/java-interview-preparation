package dsa.sorting.leetcode;

import java.util.Arrays;

/**
 * LeetCode 179 — Largest Number
 * Difficulty: Medium   Tags: Array, String, Greedy, Sorting
 * URL: https://leetcode.com/problems/largest-number/
 *
 * <h2>Problem</h2>
 * Given a list of non-negative integers {@code nums}, arrange them such that
 * they form the largest number, and return it as a string.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 100</li>
 *   <li>0 &le; nums[i] &le; 10^9</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [10, 2]                -> "210"
 *   [3, 30, 34, 5, 9]      -> "9534330"
 *   [0, 0]                  -> "0"        (special-case all-zeros)
 *   [1]                     -> "1"
 * </pre>
 *
 * <h2>Approach — custom comparator on concatenation</h2>
 * To compare two strings {@code a} and {@code b} as candidates for "which goes
 * first in the concatenation", compare {@code (a + b)} against {@code (b + a)}
 * lexicographically. Whichever is bigger as a number should come first.
 * <p>
 * Sort all numbers (as strings) with this comparator in descending order, then
 * join.
 *
 * <h2>Why concatenation comparison works</h2>
 * For two same-length strings, lexicographic order equals numeric order. For
 * different-length strings (e.g. "3" vs "30"), comparing "330" vs "303" decides
 * which prefix wins: 330 &gt; 303, so "3" should come before "30". The trick
 * uniformises the comparison length.
 *
 * <h2>Why the all-zeros special case</h2>
 * If the largest concatenation starts with '0', every number is 0, and we
 * should return "0" rather than "0000...".
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All zeros — return "0".</li>
 *   <li>Single element — return its string.</li>
 *   <li>Equal-length numbers — lexicographic comparison matches numeric.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0179_LargestNumber
 * </pre>
 */
public class LC0179_LargestNumber {

    /** O(n log n) sort with custom comparator. */
    public static String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);

        // Whichever concatenation is bigger comes first.
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));

        if (strs[0].equals("0")) return "0";              // all-zeros short-circuit
        return String.join("", strs);
    }

    public static void main(String[] args) {
        System.out.println(largestNumber(new int[]{10, 2}));             // 210
        System.out.println(largestNumber(new int[]{3, 30, 34, 5, 9}));   // 9534330
        System.out.println(largestNumber(new int[]{0, 0}));              // 0
        System.out.println(largestNumber(new int[]{1}));                 // 1
        System.out.println(largestNumber(new int[]{432, 43243}));        // 43243432
    }
}
