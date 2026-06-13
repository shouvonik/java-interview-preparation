package dsa.graphs;

import java.util.*;

/**
 * Graphs — DFS/BFS on grids, topo sort, union-find, Dijkstra.
 *
 * Run: java dsa.graphs.Graphs
 */
public class Graphs {

    // ---------------------------------------------------------------------
    // 1) Grid DFS — number of islands (LeetCode 200).
    // ---------------------------------------------------------------------
    // Each call to dfs floods one island, marking cells as 'V'. Count starts at 0
    // and increments every time we kick off a fresh dfs from a land cell.
    static int numIslands(char[][] grid) {
        int count = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '1') {
                    floodFill(grid, r, c);
                    count++;
                }
            }
        }
        return count;
    }

    private static void floodFill(char[][] g, int r, int c) {
        if (r < 0 || r >= g.length || c < 0 || c >= g[0].length) return;
        if (g[r][c] != '1') return;
        g[r][c] = 'V';
        floodFill(g, r + 1, c); floodFill(g, r - 1, c);
        floodFill(g, r, c + 1); floodFill(g, r, c - 1);
    }

    // ---------------------------------------------------------------------
    // 2) Multi-source BFS — rotting oranges (LeetCode 994).
    // ---------------------------------------------------------------------
    // 0 = empty, 1 = fresh, 2 = rotten. Each minute, every rotten orange rots
    // its 4-neighbour fresh oranges. Return minutes until all fresh are rotten,
    // or -1 if impossible.
    static int rottingOranges(int[][] grid) {
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

    // ---------------------------------------------------------------------
    // 3) Topological sort (Kahn's) — microservice startup order.
    // ---------------------------------------------------------------------
    // Edges represent "u must start before v". Returns startup order, or
    // empty list if there's a cycle.
    static List<Integer> topoSort(int V, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        int[] inDeg = new int[V];
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            inDeg[e[1]]++;
        }
        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < V; v++) if (inDeg[v] == 0) q.offer(v);

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);
            for (int v : adj.get(u)) {
                if (--inDeg[v] == 0) q.offer(v);
            }
        }
        return order.size() == V ? order : List.of();   // cycle detected
    }

    // ---------------------------------------------------------------------
    // 4) Union-Find — number of provinces.
    // ---------------------------------------------------------------------
    static class UnionFind {
        int[] parent, rank;
        int components;

        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int find(int x) {
            while (parent[x] != x) {
                parent[x] = parent[parent[x]];      // path compression (halving)
                x = parent[x];
            }
            return x;
        }

        boolean union(int a, int b) {
            int ra = find(a), rb = find(b);
            if (ra == rb) return false;
            if (rank[ra] < rank[rb])      parent[ra] = rb;
            else if (rank[ra] > rank[rb]) parent[rb] = ra;
            else                          { parent[rb] = ra; rank[ra]++; }
            components--;
            return true;
        }
    }

    static int findProvinces(int[][] isConnected) {
        int n = isConnected.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) uf.union(i, j);
            }
        }
        return uf.components;
    }

    // ---------------------------------------------------------------------
    // 5) Dijkstra — shortest paths from a single source.
    // ---------------------------------------------------------------------
    // Graph as adjacency list of int[]{neighbour, weight}. Returns dist[] from src.
    static int[] dijkstra(int V, List<int[]>[] adj, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{src, 0});
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int u = top[0], d = top[1];
            if (d > dist[u]) continue;                          // stale entry
            for (int[] edge : adj[u]) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }
        return dist;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        char[][] grid = {
            {'1','1','0','0','0'},
            {'1','1','0','0','0'},
            {'0','0','1','0','0'},
            {'0','0','0','1','1'}
        };
        System.out.println("numIslands = " + numIslands(grid));     // 3

        int[][] oranges = {
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
        };
        System.out.println("rottingOranges = " + rottingOranges(oranges));   // 4

        // Service A must start before B and C; B before D; C before D.
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};
        System.out.println("topoSort = " + topoSort(4, edges));     // [0, 1, 2, 3]

        int[][] connections = {
            {1, 1, 0},
            {1, 1, 0},
            {0, 0, 1}
        };
        System.out.println("findProvinces = " + findProvinces(connections)); // 2

        // Dijkstra demo: 4 nodes, edges with weights
        // 0 --1--> 2,  0 --4--> 1,  2 --2--> 1,  1 --1--> 3,  2 --5--> 3
        @SuppressWarnings("unchecked")
        List<int[]>[] adj = new List[4];
        for (int i = 0; i < 4; i++) adj[i] = new ArrayList<>();
        adj[0].add(new int[]{2, 1});
        adj[0].add(new int[]{1, 4});
        adj[2].add(new int[]{1, 2});
        adj[1].add(new int[]{3, 1});
        adj[2].add(new int[]{3, 5});
        System.out.println("dijkstra = " + Arrays.toString(dijkstra(4, adj, 0)));
        // [0, 3, 1, 4]
    }
}
