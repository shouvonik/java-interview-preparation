package dsa.graphs.leetcode;

import java.util.Arrays;

/**
 * LeetCode 787 — Cheapest Flights Within K Stops
 * Difficulty: Medium   Tags: Graph, Dynamic Programming, Bellman-Ford, BFS
 * URL: https://leetcode.com/problems/cheapest-flights-within-k-stops/
 *
 * <h2>Problem</h2>
 * There are {@code n} cities connected by some number of flights. You are given
 * {@code flights[i] = [from_i, to_i, price_i]}. Find the cheapest price from
 * {@code src} to {@code dst} using AT MOST {@code k} stops. If no such route
 * exists, return -1.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; n &le; 100</li>
 *   <li>0 &le; flights.length &le; (n * (n - 1) / 2)</li>
 *   <li>0 &le; k &le; n - 1</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   n=4, flights=[[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src=0, dst=3, k=1  -> 700
 *   n=3, flights=[[0,1,100],[1,2,100],[0,2,500]], src=0, dst=2, k=1                       -> 200
 *   n=3, flights=[[0,1,100],[1,2,100],[0,2,500]], src=0, dst=2, k=0                       -> 500
 * </pre>
 *
 * <h2>Approach — Bellman-Ford with a hop limit</h2>
 * Run k+1 rounds (allowing up to k intermediate stops, so k+1 edges). Each
 * round, relax all edges using ONLY the distances from the previous round to
 * avoid double-counting hops.
 *
 * <h2>Why not Dijkstra?</h2>
 * Dijkstra fixes a node's distance once and never revisits, but with a hop
 * constraint, a longer cheap path might be valid while a shorter expensive
 * path isn't. Bellman-Ford visiting all edges per round naturally bounds the
 * hop count.
 *
 * <h2>The "previous round only" trick</h2>
 * If you relax against the in-progress array, you'd be using paths with more
 * hops than allowed by the current round. Copy {@code dist} to {@code prev}
 * at the start of each round, then relax {@code dist[v] = min(dist[v], prev[u] + w)}.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>src == dst — answer 0 (no flights needed).</li>
 *   <li>Disconnected — return -1.</li>
 *   <li>k = 0 — only direct flights count.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0787_CheapestFlightsWithinKStops
 * </pre>
 */
public class LC0787_CheapestFlightsWithinKStops {

    /** Bellman-Ford with k+1 rounds — O(k * E). */
    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        for (int i = 0; i <= k; i++) {
            int[] prev = dist.clone();
            for (int[] f : flights) {
                int u = f[0], v = f[1], w = f[2];
                if (prev[u] == Integer.MAX_VALUE) continue;
                if (prev[u] + w < dist[v]) dist[v] = prev[u] + w;
            }
        }
        return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }

    public static void main(String[] args) {
        System.out.println(findCheapestPrice(4,
            new int[][]{{0, 1, 100}, {1, 2, 100}, {2, 0, 100}, {1, 3, 600}, {2, 3, 200}}, 0, 3, 1)); // 700

        System.out.println(findCheapestPrice(3,
            new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 1));  // 200

        System.out.println(findCheapestPrice(3,
            new int[][]{{0, 1, 100}, {1, 2, 100}, {0, 2, 500}}, 0, 2, 0));  // 500
    }
}
