package dsa.graphs.leetcode;

/**
 * LeetCode 684 — Redundant Connection
 * Difficulty: Medium   Tags: DFS, BFS, Union Find, Graph
 * URL: https://leetcode.com/problems/redundant-connection/
 *
 * <h2>Problem</h2>
 * In this problem, a tree is an undirected graph that is connected and has no
 * cycles. You are given a graph that started as a tree with n nodes labeled 1
 * through n, with one additional edge added. The added edge has two different
 * vertices chosen from 1 to n, and was not an edge that already existed. The
 * graph is represented by an edge list of length n. Return an edge that can be
 * removed so that the resulting graph is a tree. If there are multiple, return
 * the LAST one in the input.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[1,2],[1,3],[2,3]]                  -> [2,3]
 *   [[1,2],[2,3],[3,4],[1,4],[1,5]]      -> [1,4]
 * </pre>
 *
 * <h2>Approach — Union-Find</h2>
 * Process edges in order. For each edge, if its endpoints already share a root,
 * adding it closes a cycle — that's the redundant edge. Returning the FIRST
 * such edge (in processing order) matches the "last one in the input" rule
 * because there's exactly one extra edge added.
 *
 * <h2>Why this returns the "last" one</h2>
 * The problem guarantees exactly one extra edge. Before that edge appears, all
 * earlier edges are part of a tree (no cycles). The very first edge whose
 * endpoints already share a root IS the redundant one.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Three-node triangle — any edge could close the cycle; the input
 *       guarantees one specific answer.</li>
 *   <li>Self-loop {@code [u, u]} — first occurrence is redundant.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0684_RedundantConnection
 * </pre>
 */
public class LC0684_RedundantConnection {

    /** Union-Find — O(n α(n)). */
    public static int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        for (int i = 0; i <= n; i++) parent[i] = i;
        for (int[] e : edges) {
            int ra = find(parent, e[0]);
            int rb = find(parent, e[1]);
            if (ra == rb) return e;
            parent[ra] = rb;
        }
        throw new IllegalArgumentException("no redundant edge");
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(
            findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}})));        // [2, 3]
        System.out.println(java.util.Arrays.toString(
            findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}})));  // [1, 4]
    }
}
