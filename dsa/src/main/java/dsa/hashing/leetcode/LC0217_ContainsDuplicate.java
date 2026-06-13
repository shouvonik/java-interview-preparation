package dsa.hashing.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 217 — Contains Duplicate
 * Difficulty: Easy   Tags: Array, Hash Table, Sorting
 * URL: https://leetcode.com/problems/contains-duplicate/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return true if any value appears at
 * least twice in the array, and false if every element is distinct.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-10^9 &le; nums[i] &le; 10^9</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1, 2, 3, 1]                 -> true
 *   [1, 2, 3, 4]                 -> false
 *   [1, 1, 1, 3, 3, 4, 3, 2, 4, 2] -> true
 *   [1]                          -> false
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Sort then scan</b> — O(n log n) time, O(1) extra space (or O(n) if you
 * can't mutate input). Adjacent equals signal a duplicate.
 * <p>
 * <b>HashSet (canonical)</b> — O(n) time, O(n) space.
 * {@code Set::add} returns false on a duplicate insert — short-circuit immediately.
 *
 * <h2>Why HashSet is preferred</h2>
 * Faster (linear vs n log n) and just as much code. The space cost is acceptable
 * for the given constraints. In an interview, default to this.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single element — false (no second element to compare).</li>
 *   <li>All identical — true after second element.</li>
 *   <li>Empty array (not in constraint range, but safe) — false.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0217_ContainsDuplicate
 * </pre>
 */
public class LC0217_ContainsDuplicate {

    /** HashSet single-pass — O(n) time. */
    public static boolean containsDuplicate(int[] nums) {
        Set<Integer> seen = new HashSet<>();
        for (int n : nums) {
            if (!seen.add(n)) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 1}));                          // true
        System.out.println(containsDuplicate(new int[]{1, 2, 3, 4}));                          // false
        System.out.println(containsDuplicate(new int[]{1, 1, 1, 3, 3, 4, 3, 2, 4, 2}));        // true
        System.out.println(containsDuplicate(new int[]{1}));                                   // false
    }
}
