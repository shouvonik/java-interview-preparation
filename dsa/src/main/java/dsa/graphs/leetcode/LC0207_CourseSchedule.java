package dsa.graphs.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 207 — Course Schedule
 * Difficulty: Medium   Tags: DFS, BFS, Graph, Topological Sort
 * URL: https://leetcode.com/problems/course-schedule/
 *
 * <h2>Problem</h2>
 * There are {@code numCourses} labeled 0..n-1. You are given prerequisites
 * where {@code prerequisites[i] = [a, b]} means you must finish course b
 * before course a. Return true if you can finish all courses (i.e. the
 * dependency graph has no cycle).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; numCourses &le; 2000</li>
 *   <li>0 &le; prerequisites.length &le; 5000</li>
 *   <li>All pairs are unique.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   numCourses = 2, prereqs = [[1,0]]            -> true
 *   numCourses = 2, prereqs = [[1,0], [0,1]]     -> false (cycle)
 * </pre>
 *
 * <h2>Approach — Kahn's algorithm (BFS topological sort)</h2>
 * Build adjacency list and indegree array. Enqueue all nodes with indegree 0.
 * Repeatedly pop a node, decrement neighbours' indegrees, enqueue any that
 * drop to 0. If we process all n nodes, the graph is a DAG (no cycle); else
 * a cycle exists.
 *
 * <h2>Edge direction</h2>
 * Treat {@code [a, b]} as "edge from b to a": finishing b enables a. So
 * {@code adj[b].add(a)} and {@code indegree[a]++}.
 *
 * <h2>Alternative — DFS with three-colour cycle detection</h2>
 * White (unvisited) -> Grey (in current DFS path) -> Black (finished). A grey
 * neighbour means a back-edge — cycle. Same O(V + E) but recursive.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>No prereqs — trivially true.</li>
 *   <li>Self-prereq {@code [0, 0]} — instant cycle, false.</li>
 *   <li>Disconnected components — each component checked independently.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0207_CourseSchedule
 * </pre>
 */
public class LC0207_CourseSchedule {

    /** Kahn's topological sort — O(V + E). */
    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indeg = new int[numCourses];
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            indeg[p[0]]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < numCourses; v++) if (indeg[v] == 0) q.offer(v);

        int processed = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            processed++;
            for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
        }
        return processed == numCourses;
    }

    public static void main(String[] args) {
        System.out.println(canFinish(2, new int[][]{{1, 0}}));                   // true
        System.out.println(canFinish(2, new int[][]{{1, 0}, {0, 1}}));            // false
        System.out.println(canFinish(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})); // true
        System.out.println(canFinish(3, new int[][]{}));                          // true
    }
}
