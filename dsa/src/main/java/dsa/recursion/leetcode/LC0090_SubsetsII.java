package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 90 — Subsets II
 * Difficulty: Medium   Tags: Array, Backtracking, Bit Manipulation
 * URL: https://leetcode.com/problems/subsets-ii/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} that may contain DUPLICATES, return all
 * possible subsets. The solution set must not contain duplicate subsets.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,2]  -> [[],[1],[1,2],[1,2,2],[2],[2,2]]
 *   [0]      -> [[],[0]]
 * </pre>
 *
 * <h2>Approach — sort + skip-duplicates rule</h2>
 * Sort first. In the loop, skip {@code nums[i] == nums[i - 1]} when
 * {@code i > start} — i.e. we already used this value at this depth.
 *
 * <h2>Why "i > start"</h2>
 * The first occurrence of a value at a given depth MUST be picked (otherwise
 * we'd miss subsets that include it). Skipping later occurrences with the
 * same value at the same depth is what prevents duplicate subsets.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0090_SubsetsII
 * </pre>
 */
public class LC0090_SubsetsII {

    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> out) {
        out.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            path.add(nums[i]);
            backtrack(nums, i + 1, path, out);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(subsetsWithDup(new int[]{1, 2, 2}));    // [[],[1],[1,2],[1,2,2],[2],[2,2]]
        System.out.println(subsetsWithDup(new int[]{0}));          // [[],[0]]
    }
}
