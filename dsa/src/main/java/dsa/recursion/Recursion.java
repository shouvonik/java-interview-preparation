package dsa.recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Recursion & Backtracking — patterns for coding interviews.
 *
 * Run: java dsa.recursion.Recursion
 *
 * Recursion: a function calling itself; needs a base case and a smaller
 * subproblem at each call.
 * Backtracking: recursion that TRIES a choice, recurses, and UNDOES the
 * choice — the classic add / recurse / pop pattern.
 */
public class Recursion {

    // ---------------------------------------------------------------------
    // 1) Plain recursion — factorial.
    // ---------------------------------------------------------------------
    static long factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    // ---------------------------------------------------------------------
    // 2) Memoised recursion — Fibonacci.
    // ---------------------------------------------------------------------
    static int fib(int n) {
        Integer[] memo = new Integer[n + 1];
        return fibHelper(n, memo);
    }

    private static int fibHelper(int n, Integer[] memo) {
        if (n <= 1) return n;
        if (memo[n] != null) return memo[n];
        return memo[n] = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
    }

    // ---------------------------------------------------------------------
    // 3) Backtracking template — all subsets (power set).
    // ---------------------------------------------------------------------
    // At each step: snapshot the current path, then try every "next" choice.
    static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrackSubsets(nums, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrackSubsets(int[] nums, int start, List<Integer> path, List<List<Integer>> out) {
        out.add(new ArrayList<>(path));                       // snapshot — must copy!
        for (int i = start; i < nums.length; i++) {
            path.add(nums[i]);
            backtrackSubsets(nums, i + 1, path, out);
            path.remove(path.size() - 1);                      // undo
        }
    }

    // ---------------------------------------------------------------------
    // 4) Backtracking with pruning — combination sum.
    // ---------------------------------------------------------------------
    // Sort first, then break out of the loop when remaining < candidate.
    static List<List<Integer>> combinationSum(int[] cands, int target) {
        java.util.Arrays.sort(cands);
        List<List<Integer>> out = new ArrayList<>();
        backtrackCS(cands, target, 0, new ArrayList<>(), out);
        return out;
    }

    private static void backtrackCS(int[] cands, int remaining, int start, List<Integer> path, List<List<Integer>> out) {
        if (remaining == 0) { out.add(new ArrayList<>(path)); return; }
        for (int i = start; i < cands.length; i++) {
            if (cands[i] > remaining) break;                  // sorted -> prune
            path.add(cands[i]);
            backtrackCS(cands, remaining - cands[i], i, path, out);   // i, not i+1: allow reuse
            path.remove(path.size() - 1);
        }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("5!         = " + factorial(5));                  // 120
        System.out.println("fib(10)    = " + fib(10));                       // 55
        System.out.println("subsets    = " + subsets(new int[]{1, 2, 3}));   // 8 subsets
        System.out.println("comboSum   = " + combinationSum(new int[]{2, 3, 6, 7}, 7)); // [[2, 2, 3], [7]]
    }
}
