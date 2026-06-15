package dsa.recursion.leetcode;

/**
 * LeetCode 37 — Sudoku Solver
 * Difficulty: Hard   Tags: Array, Hash Table, Backtracking, Matrix
 * URL: https://leetcode.com/problems/sudoku-solver/
 *
 * <h2>Problem</h2>
 * Write a program to solve a Sudoku puzzle by filling the empty cells. A sudoku
 * solution must satisfy:
 * <ul>
 *   <li>Each of the digits 1-9 must occur exactly once in each row.</li>
 *   <li>Each of the digits 1-9 must occur exactly once in each column.</li>
 *   <li>Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes.</li>
 * </ul>
 * The given board may have only one solution.
 *
 * <h2>Approach — backtracking with constraint sets</h2>
 * Maintain 3 boolean arrays of size 9:
 * <ul>
 *   <li>{@code row[r][d]} — digit d is used in row r.</li>
 *   <li>{@code col[c][d]} — digit d is used in column c.</li>
 *   <li>{@code box[b][d]} — digit d is used in box b = (r/3)*3 + c/3.</li>
 * </ul>
 * Pre-populate from the initial board. Then recurse cell by cell: if the cell
 * is filled, advance. Otherwise try each digit 1-9 that none of the three sets
 * blocks, place it, recurse; backtrack on failure.
 *
 * <h2>Why O(1) constraint lookup matters</h2>
 * Naive checks (scan row/col/box every time) would multiply work by 27 per
 * candidate. The boolean arrays make legality O(1).
 *
 * <h2>Further speedups</h2>
 * Pick the next empty cell with the FEWEST candidates first (most constrained
 * variable heuristic). Reduces backtracking branches dramatically on hard
 * puzzles. Not implemented here for clarity.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0037_SudokuSolver
 * </pre>
 */
public class LC0037_SudokuSolver {

    public static void solveSudoku(char[][] board) {
        boolean[][] row = new boolean[9][10];
        boolean[][] col = new boolean[9][10];
        boolean[][] box = new boolean[9][10];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != '.') {
                    int d = board[r][c] - '0';
                    row[r][d] = col[c][d] = box[(r / 3) * 3 + c / 3][d] = true;
                }
            }
        }
        solve(board, row, col, box, 0);
    }

    private static boolean solve(char[][] b, boolean[][] r, boolean[][] c, boolean[][] x, int pos) {
        if (pos == 81) return true;
        int row = pos / 9, col = pos % 9;
        if (b[row][col] != '.') return solve(b, r, c, x, pos + 1);
        int boxIdx = (row / 3) * 3 + col / 3;
        for (int d = 1; d <= 9; d++) {
            if (r[row][d] || c[col][d] || x[boxIdx][d]) continue;
            r[row][d] = c[col][d] = x[boxIdx][d] = true;
            b[row][col] = (char) ('0' + d);
            if (solve(b, r, c, x, pos + 1)) return true;
            r[row][d] = c[col][d] = x[boxIdx][d] = false;
            b[row][col] = '.';
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        solveSudoku(board);
        for (char[] r : board) System.out.println(new String(r));
    }
}
