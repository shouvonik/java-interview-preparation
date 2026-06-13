package dsa.graphs.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 210 — Course Schedule II
 * Difficulty: Medium   Tags: DFS, BFS, Graph, Topological Sort
 * URL: https://leetcode.com/problems/course-schedule-ii/
 *
 * <h2>Problem</h2>
 * Return any valid ordering of {@code numCourses} courses (0..n-1) such that
 * each {@code prerequisites[i] = [a, b]} (must finish b before a) is respected.
 * If no valid ordering exists (cycle), return an empty array.
 *
 * <h2>Examples</h2>
 * <pre>
 *   numCourses = 2, prereqs = [[1,0]]                    -> [0, 1]
 *   numCourses = 4, prereqs = [[1,0],[2,0],[3,1],[3,2]]  -> [0,1,2,3] or [0,2,1,3]
 *   numCourses = 2, prereqs = [[1,0],[0,1]]              -> []
 * </pre>
 *
 * <h2>Approach — Kahn's topological sort, capturing order</h2>
 * Same as LC207 but record the order as we pop. If we don't pop all n nodes,
 * a cycle exists; return empty.
 *
 * <h2>Why BFS-based is preferred over DFS here</h2>
 * Kahn's produces a valid order directly. DFS-based topo sort works too but
 * requires reversing the post-order finish stack — more code, easier to mess up.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>No prereqs — return [0..n-1] in any order; algorithm gives ascending.</li>
 *   <li>Multiple valid orders — any is acceptable.</li>
 *   <li>Cycle — return empty.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0210_CourseScheduleII
 * </pre>
 */
public class LC0210_CourseScheduleII {

    /** Kahn's algorithm — O(V + E). */
    public static int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());
        int[] indeg = new int[numCourses];
        for (int[] p : prerequisites) {
            adj.get(p[1]).add(p[0]);
            indeg[p[0]]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < numCourses; v++) if (indeg[v] == 0) q.offer(v);

        int[] order = new int[numCourses];
        int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            order[idx++] = u;
            for (int v : adj.get(u)) if (--indeg[v] == 0) q.offer(v);
        }
        return idx == numCourses ? order : new int[0];
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(findOrder(2, new int[][]{{1, 0}})));
        // [0, 1]
        System.out.println(java.util.Arrays.toString(findOrder(4, new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
        // [0, 1, 2, 3] or [0, 2, 1, 3]
        System.out.println(java.util.Arrays.toString(findOrder(2, new int[][]{{1, 0}, {0, 1}})));
        // []
    }
}
