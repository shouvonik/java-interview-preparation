package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 51 — N-Queens
 * Difficulty: Hard   Tags: Array, Backtracking
 * URL: https://leetcode.com/problems/n-queens/
 *
 * <h2>Problem</h2>
 * Place n queens on an n x n chessboard so that no two queens attack each other.
 * Return all DISTINCT solutions; each solution is a list of strings where
 * 'Q' is a queen and '.' is an empty square.
 *
 * <h2>Approach — row-by-row backtracking with O(1) attack checks</h2>
 * Place exactly one queen per row. Track three boolean sets:
 * <ul>
 *   <li>{@code cols[c]} — column c has a queen.</li>
 *   <li>{@code diag1[r + c]} — '\' diagonal (top-left to bottom-right) hit.</li>
 *   <li>{@code diag2[r - c + n - 1]} — '/' diagonal hit.</li>
 * </ul>
 * For each row, try every column; skip if any set is hit; otherwise place,
 * mark, recurse, unmark.
 *
 * <h2>Why these diagonal indices</h2>
 * On a '\' diagonal {@code r + c} is constant; on a '/' diagonal {@code r - c}
 * is constant. Shifting by {@code n - 1} makes the latter non-negative.
 *
 * <h2>Complexity</h2>
 * Worst case O(n!), but heavy pruning makes practical n &le; ~13 fast.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0051_NQueens
 * </pre>
 */
public class LC0051_NQueens {

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> out = new ArrayList<>();
        int[] queens = new int[n];                          // queens[r] = column
        boolean[] cols  = new boolean[n];
        boolean[] diag1 = new boolean[2 * n - 1];
        boolean[] diag2 = new boolean[2 * n - 1];
        backtrack(0, n, queens, cols, diag1, diag2, out);
        return out;
    }

    private static void backtrack(int r, int n, int[] queens,
                                  boolean[] cols, boolean[] d1, boolean[] d2,
                                  List<List<String>> out) {
        if (r == n) { out.add(render(queens, n)); return; }
        for (int c = 0; c < n; c++) {
            int dd1 = r + c, dd2 = r - c + n - 1;
            if (cols[c] || d1[dd1] || d2[dd2]) continue;
            queens[r] = c;
            cols[c] = d1[dd1] = d2[dd2] = true;
            backtrack(r + 1, n, queens, cols, d1, d2, out);
            cols[c] = d1[dd1] = d2[dd2] = false;
        }
    }

    private static List<String> render(int[] queens, int n) {
        List<String> board = new ArrayList<>(n);
        for (int r = 0; r < n; r++) {
            char[] row = new char[n];
            Arrays.fill(row, '.');
            row[queens[r]] = 'Q';
            board.add(new String(row));
        }
        return board;
    }

    public static void main(String[] args) {
        System.out.println("n=4 solutions = " + solveNQueens(4).size());   // 2
        System.out.println("n=1 solutions = " + solveNQueens(1).size());   // 1
        System.out.println("n=2 solutions = " + solveNQueens(2).size());   // 0
        System.out.println("n=8 solutions = " + solveNQueens(8).size());   // 92
    }
}
