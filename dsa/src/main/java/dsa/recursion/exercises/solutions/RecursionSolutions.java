package dsa.recursion.exercises.solutions;

import java.util.ArrayList;
import java.util.List;

/**
 * Reference solutions for RecursionExercises.
 */
public class RecursionSolutions {

    // Exercise 1 — Permutations via used[] flag.
    static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> out = new ArrayList<>();
        backtrackPermute(nums, new boolean[nums.length], new ArrayList<>(), out);
        return out;
    }

    private static void backtrackPermute(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> out) {
        if (path.size() == nums.length) { out.add(new ArrayList<>(path)); return; }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            path.add(nums[i]);
            backtrackPermute(nums, used, path, out);
            path.remove(path.size() - 1);
            used[i] = false;
        }
    }

    // Exercise 2 — Generate Parentheses with open/close counts.
    static List<String> generateParenthesis(int n) {
        List<String> out = new ArrayList<>();
        backtrackParens(n, 0, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrackParens(int n, int open, int close, StringBuilder sb, List<String> out) {
        if (sb.length() == 2 * n) { out.add(sb.toString()); return; }
        if (open < n) {
            sb.append('(');
            backtrackParens(n, open + 1, close, sb, out);
            sb.setLength(sb.length() - 1);
        }
        if (close < open) {
            sb.append(')');
            backtrackParens(n, open, close + 1, sb, out);
            sb.setLength(sb.length() - 1);
        }
    }

    // Exercise 3 — Word Search with in-place visited marking.
    static boolean exist(char[][] board, String word) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (dfsExist(board, word, r, c, 0)) return true;
            }
        }
        return false;
    }

    private static boolean dfsExist(char[][] b, String word, int r, int c, int i) {
        if (i == word.length()) return true;
        if (r < 0 || r >= b.length || c < 0 || c >= b[0].length) return false;
        if (b[r][c] != word.charAt(i)) return false;
        char saved = b[r][c];
        b[r][c] = '#';
        boolean found = dfsExist(b, word, r + 1, c, i + 1)
                     || dfsExist(b, word, r - 1, c, i + 1)
                     || dfsExist(b, word, r, c + 1, i + 1)
                     || dfsExist(b, word, r, c - 1, i + 1);
        b[r][c] = saved;
        return found;
    }

    // Exercise 4 — Phone keypad combinations.
    private static final String[] KEYPAD = {
        "",     "",     "abc",  "def",  "ghi",
        "jkl",  "mno",  "pqrs", "tuv",  "wxyz"
    };

    static List<String> letterCombinations(String digits) {
        List<String> out = new ArrayList<>();
        if (digits.isEmpty()) return out;
        backtrackLetters(digits, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrackLetters(String digits, int idx, StringBuilder sb, List<String> out) {
        if (idx == digits.length()) { out.add(sb.toString()); return; }
        for (char c : KEYPAD[digits.charAt(idx) - '0'].toCharArray()) {
            sb.append(c);
            backtrackLetters(digits, idx + 1, sb, out);
            sb.setLength(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("permute      = " + permute(new int[]{1, 2, 3}));        // 6 lists
        System.out.println("parentheses  = " + generateParenthesis(3));              // 5 strings
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        System.out.println("wordSearch   = " + exist(board, "ABCCED"));              // true
        System.out.println("letterCombos = " + letterCombinations("23"));            // [ad, ae, af, bd, be, bf, cd, ce, cf]
    }
}
