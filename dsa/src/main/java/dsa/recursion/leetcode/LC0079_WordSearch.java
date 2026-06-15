package dsa.recursion.leetcode;

/**
 * LeetCode 79 — Word Search
 * Difficulty: Medium   Tags: Array, String, Backtracking, Matrix
 * URL: https://leetcode.com/problems/word-search/
 *
 * <h2>Problem</h2>
 * Given an m x n grid of characters {@code board} and a string {@code word},
 * return true if the word exists in the grid. The word can be constructed
 * from letters of sequentially adjacent cells (4-directional). The same cell
 * may not be used more than once.
 *
 * <h2>Examples</h2>
 * <pre>
 *   board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"  -> true
 *   board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"     -> true
 *   board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"    -> false
 * </pre>
 *
 * <h2>Approach — DFS with on-the-fly visited marking</h2>
 * For each cell, try to match {@code word[0]}. If it matches, mark visited
 * (overwrite with a sentinel like '#'), recurse to neighbours for the next
 * character, restore on backtrack.
 *
 * <h2>Why mutate the grid</h2>
 * Avoids allocating a {@code boolean[m][n]} visited array per call. Restore
 * before returning so neighbouring DFS roots see the original board.
 *
 * <h2>Pruning</h2>
 * Check board[r][c] != word[i] first; if not equal, return false immediately
 * without descending.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0079_WordSearch
 * </pre>
 */
public class LC0079_WordSearch {

    public static boolean exist(char[][] board, String word) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (dfs(board, word, r, c, 0)) return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] b, String word, int r, int c, int i) {
        if (i == word.length()) return true;
        if (r < 0 || r >= b.length || c < 0 || c >= b[0].length) return false;
        if (b[r][c] != word.charAt(i)) return false;

        char saved = b[r][c];
        b[r][c] = '#';
        boolean found = dfs(b, word, r + 1, c, i + 1)
                     || dfs(b, word, r - 1, c, i + 1)
                     || dfs(b, word, r, c + 1, i + 1)
                     || dfs(b, word, r, c - 1, i + 1);
        b[r][c] = saved;
        return found;
    }

    public static void main(String[] args) {
        char[][] board = {
            {'A','B','C','E'},
            {'S','F','C','S'},
            {'A','D','E','E'}
        };
        System.out.println(exist(board, "ABCCED"));   // true
        System.out.println(exist(board, "SEE"));      // true
        System.out.println(exist(board, "ABCB"));     // false
    }
}
