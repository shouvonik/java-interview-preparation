package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 78 — Subsets
 * Difficulty: Medium   Tags: Array, Backtracking, Bit Manipulation
 * URL: https://leetcode.com/problems/subsets/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} of UNIQUE elements, return all possible
 * subsets (the power set). The solution set must not contain duplicate subsets.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3]  -> [[],[1],[2],[3],[1,2],[1,3],[2,3],[1,2,3]]
 *   [0]      -> [[],[0]]
 * </pre>
 *
 * <h2>Approach 1 — backtracking (canonical)</h2>
 * At each step, snapshot the current path as a subset, then iterate choices
 * from {@code start..n-1}: pick, recurse with {@code i + 1}, undo.
 *
 * <h2>Approach 2 — bitmask enumeration</h2>
 * Each of the {@code 2^n} subsets corresponds to an n-bit mask: bit i set
 * iff {@code nums[i]} is included. Iterate {@code mask = 0..(1 &lt;&lt; n) - 1}.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0078_Subsets
 * </pre>
 */
public class LC0078_Subsets {

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> out) {
        out.add(new ArrayList<>(path));
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrack(nums, i + 1, path, out);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(subsets(new int[]{1, 2, 3}));   // 8 subsets
        System.out.println(subsets(new int[]{0}));         // [[], [0]]
    }
}
