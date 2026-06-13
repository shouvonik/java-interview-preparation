package dsa.hashing.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * LeetCode 36 — Valid Sudoku
 * Difficulty: Medium   Tags: Array, Hash Table, Matrix
 * URL: https://leetcode.com/problems/valid-sudoku/
 *
 * <h2>Problem</h2>
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be
 * validated according to the rules:
 * <ul>
 *   <li>Each row must contain digits 1-9 without repetition.</li>
 *   <li>Each column must contain digits 1-9 without repetition.</li>
 *   <li>Each of the nine 3x3 sub-boxes must contain digits 1-9 without repetition.</li>
 * </ul>
 * Note: A valid layout doesn't need to be solvable. Empty cells are '.'.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>board.length == 9</li>
 *   <li>board[i].length == 9</li>
 *   <li>board[i][j] is a digit 1-9 or '.'</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   Valid board (partial): returns true.
 *   If '5' appears twice in the same row/col/box: returns false.
 * </pre>
 *
 * <h2>Approach — composite-key HashSet</h2>
 * One pass over the grid. For each non-'.' cell, attempt to add three
 * namespaced strings to a single HashSet:
 * <ul>
 *   <li>"r&lt;row&gt;=&lt;digit&gt;" — the digit is now claimed for this row.</li>
 *   <li>"c&lt;col&gt;=&lt;digit&gt;" — for this column.</li>
 *   <li>"b&lt;box&gt;=&lt;digit&gt;" — for this 3x3 box.</li>
 * </ul>
 * If any add returns false (already present), the board is invalid.
 * <p>
 * Box index: {@code box = (row / 3) * 3 + (col / 3)}, giving 0..8 in a
 * row-major order over the nine boxes.
 *
 * <h2>Why namespaced keys?</h2>
 * Without the "r"/"c"/"b" prefix, "5" in row 1 and "5" in column 5 would
 * collide. Prefixes keep the three constraint families in separate logical sets.
 *
 * <h2>Alternative</h2>
 * Three separate {@code Set[]} arrays (rows, cols, boxes). Slightly more memory
 * but no string allocation; faster in practice. Choose by interviewer taste.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All empty board ('.') — return true (no constraint violated).</li>
 *   <li>Single digit appearing once — fine.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0036_ValidSudoku
 * </pre>
 */
public class LC0036_ValidSudoku {

    /** Single-pass with composite-key HashSet — O(1) for fixed 9x9. */
    public static boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') continue;
                int box = (r / 3) * 3 + (c / 3);
                if (!seen.add("r" + r + "=" + ch)) return false;
                if (!seen.add("c" + c + "=" + ch)) return false;
                if (!seen.add("b" + box + "=" + ch)) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] valid = {
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
        System.out.println(isValidSudoku(valid));   // true

        char[][] invalid = {
            {'8','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},   // '8' duplicate in column 0
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println(isValidSudoku(invalid)); // false
    }
}
