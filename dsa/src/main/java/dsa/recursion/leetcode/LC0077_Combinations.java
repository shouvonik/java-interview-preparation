package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 77 — Combinations
 * Difficulty: Medium   Tags: Backtracking
 * URL: https://leetcode.com/problems/combinations/
 *
 * <h2>Problem</h2>
 * Given two integers {@code n} and {@code k}, return all possible combinations
 * of {@code k} numbers chosen from {@code [1..n]}.
 *
 * <h2>Examples</h2>
 * <pre>
 *   n = 4, k = 2  -> [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
 *   n = 1, k = 1  -> [[1]]
 * </pre>
 *
 * <h2>Approach — backtracking with start index</h2>
 * Pick numbers in ascending order. {@code backtrack(start)} considers choices
 * {@code start..n}; recurse with {@code i + 1} to keep order strict.
 *
 * <h2>Pruning</h2>
 * If the number of remaining slots exceeds {@code n - i + 1}, no need to
 * iterate further at this level. Loop bound: {@code i &le; n - (k - path.size()) + 1}.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0077_Combinations
 * </pre>
 */
public class LC0077_Combinations {

    public static List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> out = new ArrayList<>();
        backtrack(n, k, 1, new ArrayList<>(), out);
        return out;
    }

    private static void backtrack(int n, int k, int start, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == k) { out.add(new ArrayList<>(path)); return; }
        int needed = k - path.size();
        for (int i = start; i <= n - needed + 1; i++) {       // pruning bound
            path.add(i);
            backtrack(n, k, i + 1, path, out);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(combine(4, 2));   // [[1,2],[1,3],[1,4],[2,3],[2,4],[3,4]]
        System.out.println(combine(1, 1));   // [[1]]
    }
}
