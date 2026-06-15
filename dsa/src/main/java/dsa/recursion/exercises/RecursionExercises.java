package dsa.recursion.exercises;

import java.util.List;

/**
 * Recursion & Backtracking — exercises.
 */
public class RecursionExercises {

    /**
     * Exercise 1 — Permutations (LeetCode 46).
     * Return all permutations of an array of DISTINCT integers.
     * Example: [1,2,3] -> 6 lists.
     */
    static List<List<Integer>> permute(int[] nums) {
        // TODO — backtracking with a used[] flag.
        return List.of();
    }

    /**
     * Exercise 2 — Generate Parentheses (LeetCode 22).
     * Generate all valid parentheses strings of n pairs.
     * Example: n = 3 -> ["((()))","(()())","(())()","()(())","()()()"].
     */
    static List<String> generateParenthesis(int n) {
        // TODO — backtracking with open/close counts.
        return List.of();
    }

    /**
     * Exercise 3 — Word Search (LeetCode 79).
     * Does the word exist as a sequence of adjacent cells in the grid?
     * Example: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], "ABCCED" -> true.
     */
    static boolean exist(char[][] board, String word) {
        // TODO — DFS with on-the-fly visited marking ('#').
        return false;
    }

    /**
     * Exercise 4 — Letter Combinations of a Phone Number (LeetCode 17).
     * Example: "23" -> ["ad","ae","af","bd","be","bf","cd","ce","cf"].
     */
    static List<String> letterCombinations(String digits) {
        // TODO
        return List.of();
    }

    public static void main(String[] args) {
        System.out.println("permute      = " + permute(new int[]{1, 2, 3}));
        System.out.println("parentheses  = " + generateParenthesis(3));
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("wordSearch   = " + exist(board, "ABCCED"));
        System.out.println("letterCombos = " + letterCombinations("23"));
    }
}
