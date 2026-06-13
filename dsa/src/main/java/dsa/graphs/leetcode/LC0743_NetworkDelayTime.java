package dsa.graphs.leetcode;

import java.util.*;

/**
 * LeetCode 743 — Network Delay Time
 * Difficulty: Medium   Tags: DFS, BFS, Graph, Shortest Path, Heap
 * URL: https://leetcode.com/problems/network-delay-time/
 *
 * <h2>Problem</h2>
 * There are n network nodes labeled 1..n. {@code times[i] = (u_i, v_i, w_i)}
 * means a directed edge from {@code u_i} to {@code v_i} with travel time
 * {@code w_i}. We send a signal from node k. Return the time it takes for all
 * n nodes to receive the signal, or -1 if it's impossible.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; k &le; n &le; 100</li>
 *   <li>1 &le; times.length &le; 6000</li>
 *   <li>0 &le; w_i &le; 100</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2  -> 2
 *   times = [[1,2,1]],                 n = 2, k = 1  -> 1
 *   times = [[1,2,1]],                 n = 2, k = 2  -> -1
 * </pre>
 *
 * <h2>Approach — Dijkstra from source k</h2>
 * Single-source shortest paths with non-negative weights. The answer is the
 * MAXIMUM of all final distances; if any is infinite, return -1.
 *
 * <h2>Implementation notes</h2>
 * <ul>
 *   <li>1-indexed nodes — allocate arrays of size n + 1.</li>
 *   <li>Stale heap entries — Java's PriorityQueue can't decrease-key, so we
 *       lazily skip entries where the popped distance is bigger than the
 *       recorded one.</li>
 * </ul>
 *
 * <h2>Why not Bellman-Ford</h2>
 * BF handles negative weights but runs in O(VE). Here weights are non-negative,
 * so Dijkstra at O((V + E) log V) is faster.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Some nodes unreachable from k — return -1.</li>
 *   <li>Self-loops or zero-weight edges — algorithm handles them.</li>
 *   <li>Single node (n=1, k=1) — answer is 0.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0743_NetworkDelayTime
 * </pre>
 */
public class LC0743_NetworkDelayTime {

    /** Dijkstra — O((V + E) log V). */
    public static int networkDelayTime(int[][] times, int n, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] t : times) adj.get(t[0]).add(new int[]{t[1], t[2]});

        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{k, 0});
        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int u = top[0], d = top[1];
            if (d > dist[u]) continue;                          // stale entry
            for (int[] e : adj.get(u)) {
                int v = e[0], w = e[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{v, dist[v]});
                }
            }
        }

        int max = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1;
            max = Math.max(max, dist[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2));   // 2
        System.out.println(networkDelayTime(new int[][]{{1, 2, 1}}, 2, 1));                          // 1
        System.out.println(networkDelayTime(new int[][]{{1, 2, 1}}, 2, 2));                          // -1
    }
}
