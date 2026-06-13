package dsa.graphs.leetcode;

/**
 * LeetCode 200 — Number of Islands
 * Difficulty: Medium   Tags: Array, DFS, BFS, Union Find, Matrix
 * URL: https://leetcode.com/problems/number-of-islands/
 *
 * <h2>Problem</h2>
 * Given an m x n 2D grid map of '1's (land) and '0's (water), return the
 * number of islands. An island is surrounded by water and is formed by
 * connecting adjacent lands horizontally or vertically. You may assume all
 * four edges of the grid are surrounded by water.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; m, n &le; 300</li>
 *   <li>grid[i][j] is '0' or '1'.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[1,1,1,1,0],
 *    [1,1,0,1,0],
 *    [1,1,0,0,0],
 *    [0,0,0,0,0]]   -> 1
 *
 *   [[1,1,0,0,0],
 *    [1,1,0,0,0],
 *    [0,0,1,0,0],
 *    [0,0,0,1,1]]   -> 3
 * </pre>
 *
 * <h2>Approach — flood fill via DFS</h2>
 * Walk the grid; whenever a '1' is found, run DFS to mark the whole connected
 * component as visited (set to 'V' or '0'), and increment the island count.
 *
 * <h2>Why mutate the grid</h2>
 * Marking visits in the grid itself avoids allocating a separate
 * {@code boolean[m][n]}. If the caller can't accept mutation, copy the grid
 * first or use the boolean variant.
 *
 * <h2>Alternatives</h2>
 * <ul>
 *   <li><b>BFS</b> — same idea with a queue; equivalent complexity, no recursion
 *       stack risk on extreme inputs.</li>
 *   <li><b>Union-Find</b> — union each land cell with its 4 neighbours; count
 *       roots at the end. Useful when grid changes over time.</li>
 * </ul>
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All water — 0 islands.</li>
 *   <li>All land — 1 island.</li>
 *   <li>Single cell — 0 or 1 depending on contents.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0200_NumberOfIslands
 * </pre>
 */
public class LC0200_NumberOfIslands {

    /** DFS flood fill — O(m * n). Mutates the grid. */
    public static int numIslands(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '1') {
                    flood(grid, r, c);
                    count++;
                }
            }
        }
        return count;
    }

    private static void flood(char[][] g, int r, int c) {
        if (r < 0 || r >= g.length || c < 0 || c >= g[0].length) return;
        if (g[r][c] != '1') return;
        g[r][c] = 'V';
        flood(g, r + 1, c); flood(g, r - 1, c);
        flood(g, r, c + 1); flood(g, r, c - 1);
    }

    public static void main(String[] args) {
        char[][] a = {
            {'1','1','1','1','0'},
            {'1','1','0','1','0'},
            {'1','1','0','0','0'},
            {'0','0','0','0','0'}
        };
        System.out.println(numIslands(a));    // 1

        char[][] b = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println(numIslands(b));    // 3
    }
}
