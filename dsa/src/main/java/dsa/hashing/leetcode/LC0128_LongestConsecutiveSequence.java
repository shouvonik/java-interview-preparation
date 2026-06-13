package dsa.hashing.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 128 — Longest Consecutive Sequence
 * Difficulty: Medium   Tags: Array, Hash Table, Union Find
 * URL: https://leetcode.com/problems/longest-consecutive-sequence/
 *
 * <h2>Problem</h2>
 * Given an unsorted array of integers, return the length of the longest
 * consecutive elements sequence. Must run in O(n) time.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; nums.length &le; 10^5</li>
 *   <li>-10^9 &le; nums[i] &le; 10^9</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [100, 4, 200, 1, 3, 2]               -> 4    ([1, 2, 3, 4])
 *   [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]       -> 9    ([0..8])
 *   []                                    -> 0
 *   [1, 1, 1]                             -> 1    (duplicates ignored)
 * </pre>
 *
 * <h2>Approach — set + "start of a run"</h2>
 * Put all values in a HashSet. For each value {@code x}, ONLY start counting
 * if {@code x - 1} is NOT in the set (meaning x is the start of a run).
 * Then walk forward {@code x + 1, x + 2, ...} until you fall out of the set.
 * Track the maximum run length.
 *
 * <h2>Why is this O(n)?</h2>
 * Each value is the start of at most one run, and only run starts trigger the
 * inner while loop. The inner loop's total work across all run starts is the
 * sum of run lengths — at most n. So total = O(n) amortised.
 *
 * <h2>Why sorting doesn't qualify</h2>
 * Sorting then scanning would be O(n log n). The problem demands O(n), which
 * is only achievable with a hash structure (or union-find, also O(n) amortised).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty input — 0.</li>
 *   <li>All duplicates — 1 (the duplicates collapse to a single set element).</li>
 *   <li>Already consecutive — n.</li>
 *   <li>Negative ranges — same logic, no special case.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0128_LongestConsecutiveSequence
 * </pre>
 */
public class LC0128_LongestConsecutiveSequence {

    /** O(n) — HashSet + run-start trick. */
    public static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int best = 0;
        for (int x : set) {
            if (set.contains(x - 1)) continue;             // not a run start
            int len = 1;
            while (set.contains(x + len)) len++;
            best = Math.max(best, len);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(longestConsecutive(new int[]{100, 4, 200, 1, 3, 2}));            // 4
        System.out.println(longestConsecutive(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));    // 9
        System.out.println(longestConsecutive(new int[]{}));                                 // 0
        System.out.println(longestConsecutive(new int[]{1, 1, 1}));                          // 1
        System.out.println(longestConsecutive(new int[]{-5, -4, -3, 100}));                  // 3
    }
}
