package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 46 — Permutations
 * Difficulty: Medium   Tags: Array, Backtracking
 * URL: https://leetcode.com/problems/permutations/
 *
 * <h2>Problem</h2>
 * Given an array {@code nums} of DISTINCT integers, return all the possible
 * permutations. You can return the answer in any order.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3]  -> [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 *   [0,1]    -> [[0,1],[1,0]]
 *   [1]      -> [[1]]
 * </pre>
 *
 * <h2>Approach — backtracking with used[] flags</h2>
 * Build a permutation by picking, in turn, each unused element. {@code used[i]}
 * tracks which indices are currently in the path. When the path is full, snapshot.
 *
 * <h2>Why not the swap-in-place trick</h2>
 * In-place swapping is more memory-efficient but harder to reason about and
 * to extend (e.g. for permutations II with duplicates). The flag version is
 * the readable default.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0046_Permutations
 * </pre>
 */
public class LC0046_Permutations {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(nums, new boolean[nums.length], new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == nums.length) { out.add(new ArrayList<>(path)); return; }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(nums[i]);
            backtrack(nums, used, path, out);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    public static void main(String[] args) {
        System.out.println(permute(new int[]{1, 2, 3}));   // all 6 permutations
        System.out.println(permute(new int[]{0, 1}));      // [[0, 1], [1, 0]]
        System.out.println(permute(new int[]{1}));         // [[1]]
    }
}
