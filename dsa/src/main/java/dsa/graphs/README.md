# Graphs

Graph problems live or die on **picking the right algorithm**. The problem statement always hints at the algorithm if you know the keywords. The decision guide below is the most important table in this entire repo.

## Decision guide — which algorithm does this problem want?

| You see... | Reach for... |
| --- | --- |
| "Connected components", "islands", "reachable" | DFS or BFS on a grid/graph |
| "Shortest path, unweighted edges" | BFS |
| "Shortest path, non-negative weights" | Dijkstra |
| "Shortest path, negative weights allowed" | Bellman-Ford |
| "All-pairs shortest paths, small n" | Floyd-Warshall |
| "Order tasks given dependencies" | Topological sort (Kahn's or DFS) |
| "Detect cycle in directed graph" | DFS with coloring (white/gray/black) |
| "Group items dynamically as they're related" | Union-Find |
| "Minimum spanning tree" | Kruskal (union-find) or Prim |
| "Course schedule possible? / Build order?" | Topo sort |

## Patterns covered in `Graphs.java`

| Pattern | Complexity |
| --- | --- |
| Grid DFS — number of islands | O(R × C) |
| Multi-source BFS — rotting oranges | O(R × C) |
| Topological sort (Kahn's, in-degree queue) | O(V + E) |
| Union-Find (path compression + union by rank) | ~O(α(n)) per op |
| Dijkstra with PriorityQueue | O((V + E) log V) |

## Real-life framing

- **Grid DFS** → CCTV camera coverage map: which connected areas are unmonitored?
- **Multi-source BFS** → service degradation propagating from multiple failed nodes.
- **Topo sort** → ordering microservice startup based on dependency graph.
- **Union-Find** → tracking province/cluster membership as new connections come in.
- **Dijkstra** → cheapest currency conversion via FX intermediates.

## Templates — memorise these

**Grid DFS:**
```java
void dfs(int r, int c, char[][] grid) {
    if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length) return;
    if (grid[r][c] != '1') return;
    grid[r][c] = 'V';                          // mark visited
    dfs(r+1, c, grid); dfs(r-1, c, grid);
    dfs(r, c+1, grid); dfs(r, c-1, grid);
}
```

**Kahn's topological sort:**
```java
int[] inDeg = new int[V];
for (each edge u->v) inDeg[v]++;
Queue<Integer> q = new ArrayDeque<>();
for (int v = 0; v < V; v++) if (inDeg[v] == 0) q.offer(v);

List<Integer> order = new ArrayList<>();
while (!q.isEmpty()) {
    int u = q.poll();
    order.add(u);
    for (int v : adj[u]) if (--inDeg[v] == 0) q.offer(v);
}
// If order.size() < V, there's a cycle.
```

**Union-Find with path compression + union by rank:**
```java
int find(int x) {
    while (parent[x] != x) {
        parent[x] = parent[parent[x]];    // path compression (halving)
        x = parent[x];
    }
    return x;
}

void union(int a, int b) {
    int ra = find(a), rb = find(b);
    if (ra == rb) return;
    if (rank[ra] < rank[rb]) { parent[ra] = rb; }
    else if (rank[ra] > rank[rb]) { parent[rb] = ra; }
    else { parent[rb] = ra; rank[ra]++; }
}
```

**Dijkstra with PriorityQueue:**
```java
int[] dist = new int[V];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;
PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
pq.offer(new int[]{src, 0});
while (!pq.isEmpty()) {
    int[] top = pq.poll();
    int u = top[0], d = top[1];
    if (d > dist[u]) continue;                  // stale entry
    for (int[] edge : adj[u]) {                  // edge = {v, weight}
        int v = edge[0], w = edge[1];
        if (dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
            pq.offer(new int[]{v, dist[v]});
        }
    }
}
```

## Pitfalls

- **Visited tracking** — for grids, mutate the grid OR keep a `boolean[][] visited`. Forgetting causes infinite recursion.
- **Stale PQ entries** — Java's PriorityQueue doesn't support decrease-key. Always check `if (d > dist[u]) continue;` after polling.
- **Topo sort cycle detection** — if your output has fewer than V nodes, the input had a cycle.
- **Union-Find correctness** — `union(find(a), find(b))` ensures rank check uses the *roots*.

## Curated LeetCode

| # | Problem | Difficulty | Algorithm |
| --- | --- | --- | --- |
| 200 | [Number of Islands](https://leetcode.com/problems/number-of-islands/) | Medium | Grid DFS/BFS |
| 994 | [Rotting Oranges](https://leetcode.com/problems/rotting-oranges/) | Medium | Multi-source BFS |
| 207 | [Course Schedule](https://leetcode.com/problems/course-schedule/) | Medium | Topo sort / cycle |
| 210 | [Course Schedule II](https://leetcode.com/problems/course-schedule-ii/) | Medium | Topo sort order |
| 547 | [Number of Provinces](https://leetcode.com/problems/number-of-provinces/) | Medium | Union-Find or DFS |
| 684 | [Redundant Connection](https://leetcode.com/problems/redundant-connection/) | Medium | Union-Find |
| 743 | [Network Delay Time](https://leetcode.com/problems/network-delay-time/) | Medium | Dijkstra |
| 127 | [Word Ladder](https://leetcode.com/problems/word-ladder/) | Hard | BFS on implicit graph |
| 269 | [Alien Dictionary](https://leetcode.com/problems/alien-dictionary/) | Hard | Topo sort |
| 787 | [Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/) | Medium | Bellman-Ford / BFS with limit |

## Run

```bash
cd dsa/src/main/java
javac dsa/graphs/Graphs.java
java dsa.graphs.Graphs
```
