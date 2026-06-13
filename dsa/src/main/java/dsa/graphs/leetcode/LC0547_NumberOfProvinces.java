package dsa.graphs.leetcode;

/**
 * LeetCode 547 — Number of Provinces
 * Difficulty: Medium   Tags: DFS, BFS, Union Find, Graph
 * URL: https://leetcode.com/problems/number-of-provinces/
 *
 * <h2>Problem</h2>
 * There are n cities. Some are connected directly, some indirectly. A province
 * is a group of directly or indirectly connected cities and no other cities
 * outside the group. Given an n x n matrix {@code isConnected} where
 * {@code isConnected[i][j] == 1} iff city i and j are directly connected,
 * return the total number of provinces.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; n &le; 200</li>
 *   <li>{@code isConnected[i][i] == 1}, symmetric, 0/1 entries.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[1,1,0],[1,1,0],[0,0,1]]  -> 2
 *   [[1,0,0],[0,1,0],[0,0,1]]  -> 3
 * </pre>
 *
 * <h2>Approach — Union-Find (canonical)</h2>
 * Initialise n disjoint sets. For each pair {@code (i, j)} with {@code i < j}
 * and {@code isConnected[i][j] == 1}, union them. After processing, count the
 * number of remaining components (decrement on each successful union).
 *
 * <h2>Path compression + union by rank</h2>
 * Keeps union-find near-O(1) per op (technically O(α(n)) amortised). For n = 200
 * this barely matters but it's free, so include it.
 *
 * <h2>Alternative — DFS / BFS</h2>
 * Walk each unvisited city, DFS-mark all reachable, count components. Same
 * asymptotic cost; union-find scales better when the graph changes over time.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All connected — 1 province.</li>
 *   <li>All isolated — n provinces.</li>
 *   <li>Identity diagonal only — same as fully isolated.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0547_NumberOfProvinces
 * </pre>
 */
public class LC0547_NumberOfProvinces {

    /** Union-Find with path compression and union by rank. */
    public static int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;
        int components = n;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    int ra = find(parent, i), rb = find(parent, j);
                    if (ra != rb) {
                        if (rank[ra] < rank[rb])      parent[ra] = rb;
                        else if (rank[ra] > rank[rb]) parent[rb] = ra;
                        else                          { parent[rb] = ra; rank[ra]++; }
                        components--;
                    }
                }
            }
        }
        return components;
    }

    private static int find(int[] parent, int x) {
        while (parent[x] != x) {
            parent[x] = parent[parent[x]];
            x = parent[x];
        }
        return x;
    }

    public static void main(String[] args) {
        System.out.println(findCircleNum(new int[][]{{1, 1, 0}, {1, 1, 0}, {0, 0, 1}}));   // 2
        System.out.println(findCircleNum(new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}}));   // 3
        System.out.println(findCircleNum(new int[][]{{1}}));                                 // 1
    }
}
