package dsa.graphs.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 994 — Rotting Oranges
 * Difficulty: Medium   Tags: Array, BFS, Matrix
 * URL: https://leetcode.com/problems/rotting-oranges/
 *
 * <h2>Problem</h2>
 * You are given an m x n grid where each cell can have one of three values:
 * <ul>
 *   <li>0: empty</li>
 *   <li>1: a fresh orange</li>
 *   <li>2: a rotten orange</li>
 * </ul>
 * Every minute, any fresh orange that is 4-directionally adjacent to a rotten
 * one becomes rotten. Return the minimum number of minutes until no fresh
 * oranges remain, or -1 if it's impossible.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>m == grid.length</li>
 *   <li>n == grid[0].length</li>
 *   <li>1 &le; m, n &le; 10</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[2,1,1],[1,1,0],[0,1,1]]  -> 4
 *   [[2,1,1],[0,1,1],[1,0,1]]  -> -1
 *   [[0,2]]                     -> 0   (no fresh oranges)
 * </pre>
 *
 * <h2>Approach — multi-source BFS</h2>
 * Push every rotten orange into the queue initially; count fresh ones. Run BFS
 * by minutes: each minute, every currently-rotten orange rots its fresh
 * neighbours. Stop when no more fresh or the queue is empty.
 *
 * <h2>Why multi-source matters</h2>
 * Multiple rotten oranges spread in parallel; we want the time for the LAST
 * fresh orange to rot. A single-source BFS would over-count by serialising.
 * Starting all rotten ones at minute 0 captures the parallelism.
 *
 * <h2>Termination</h2>
 * If after the BFS some fresh oranges remain, return -1 (unreachable from any
 * rotten one). Otherwise return the minute count.
 *
 * <h2>Common bug — counting one minute too many</h2>
 * Increment {@code minutes} only AFTER processing a level that actually rotted
 * something. The version below increments per level but the outer guard
 * {@code while (!q.isEmpty() && fresh > 0)} ensures we don't tick when there's
 * nothing left to spread to.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>No fresh oranges at start — return 0.</li>
 *   <li>No rotten oranges and fresh exist — return -1.</li>
 *   <li>Empty grid (not in constraints, but defensible) — return 0.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0994_RottingOranges
 * </pre>
 */
public class LC0994_RottingOranges {

    /** Multi-source BFS — O(m * n). */
    public static int orangesRotting(int[][] grid) {
        int R = grid.length, C = grid[0].length;
        Deque<int[]> q = new ArrayDeque<>();
        int fresh = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == 2) q.offer(new int[]{r, c});
                else if (grid[r][c] == 1) fresh++;
            }
        }
        if (fresh == 0) return 0;

        int minutes = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!q.isEmpty() && fresh > 0) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int[] cell = q.poll();
                for (int[] d : dirs) {
                    int nr = cell[0] + d[0], nc = cell[1] + d[1];
                    if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                    if (grid[nr][nc] != 1) continue;
                    grid[nr][nc] = 2;
                    fresh--;
                    q.offer(new int[]{nr, nc});
                }
            }
            minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }

    public static void main(String[] args) {
        System.out.println(orangesRotting(new int[][]{{2, 1, 1}, {1, 1, 0}, {0, 1, 1}}));   // 4
        System.out.println(orangesRotting(new int[][]{{2, 1, 1}, {0, 1, 1}, {1, 0, 1}}));   // -1
        System.out.println(orangesRotting(new int[][]{{0, 2}}));                              // 0
    }
}
