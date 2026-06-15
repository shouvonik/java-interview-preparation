package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 39 — Combination Sum
 * Difficulty: Medium   Tags: Array, Backtracking
 * URL: https://leetcode.com/problems/combination-sum/
 *
 * <h2>Problem</h2>
 * Given an array of DISTINCT positive integers {@code candidates} and a target
 * {@code target}, return a list of all unique combinations of {@code candidates}
 * where the chosen numbers sum to {@code target}. The same number may be chosen
 * an unlimited number of times. Two combinations are the same if they contain
 * the same multiset (order doesn't matter).
 *
 * <h2>Examples</h2>
 * <pre>
 *   candidates=[2,3,6,7], target=7  -> [[2,2,3],[7]]
 *   candidates=[2,3,5],   target=8  -> [[2,2,2,2],[2,3,3],[3,5]]
 *   candidates=[2],       target=1  -> []
 * </pre>
 *
 * <h2>Approach — backtracking with start index</h2>
 * To avoid duplicate combinations (different orderings of the same multiset),
 * fix the order of choices: at each call only consider candidates at index
 * &ge; the current start. Allow re-using the same index by passing {@code i}
 * (not {@code i + 1}) into the recursive call.
 *
 * <h2>Pruning by sorting</h2>
 * Sort candidates ascending. When {@code remaining - cand &lt; 0}, break out
 * (since all later cands are even larger).
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0039_CombinationSum
 * </pre>
 */
public class LC0039_CombinationSum {

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        java.util.Arrays.sort(candidates);
        List<List<Integer>> out = new ArrayList<>();
        backtrack(candidates, target, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int[] cands, int remaining, int start, List<Integer> path, List<List<Integer>> out) {
        if (remaining == 0) { out.add(new ArrayList<>(path)); return; }
        for (int i = start; i < cands.length; i++) {
            if (cands[i] > remaining) break;
            path.add(cands[i]);
            backtrack(cands, remaining - cands[i], i, path, out);   // i, not i+1: allow re-use
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combinationSum(new int[]{2, 3, 6, 7}, 7));   // [[2, 2, 3], [7]]
        System.out.println(combinationSum(new int[]{2, 3, 5}, 8));      // [[2, 2, 2, 2], [2, 3, 3], [3, 5]]
        System.out.println(combinationSum(new int[]{2}, 1));            // []
    }
}
