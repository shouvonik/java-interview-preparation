package dsa.heaps.leetcode;

import java.util.PriorityQueue;

/**
 * LeetCode 973 — K Closest Points to Origin
 * Difficulty: Medium   Tags: Array, Math, Divide and Conquer, Geometry, Sorting, Heap, Quickselect
 * URL: https://leetcode.com/problems/k-closest-points-to-origin/
 *
 * <h2>Problem</h2>
 * Given an array of points where {@code points[i] = [x_i, y_i]} represents a
 * point on the X-Y plane and an integer {@code k}, return the {@code k}
 * closest points to the origin (0, 0). The distance is the Euclidean distance.
 * The answer can be returned in any order.
 *
 * <h2>Examples</h2>
 * <pre>
 *   points = [[1,3],[-2,2]], k = 1  -> [[-2,2]]
 *   points = [[3,3],[5,-1],[-2,4]], k = 2  -> [[3,3],[-2,4]]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Sort by distance</b> — O(n log n). Simple but unnecessary if k &lt;&lt; n.
 * <p>
 * <b>Max-heap of size k</b> — O(n log k).
 * Keep the K closest so far; the heap's worst (top) is the worst of the K. If
 * a new point beats it, swap.
 * <p>
 * <b>Quickselect</b> — O(n) average.
 * Partition partway around the k-th by squared distance. Best when n &gt;&gt; k
 * and you don't mind the worst-case quadratic risk (random pivot mitigates).
 *
 * <h2>Why squared distance (no sqrt)</h2>
 * {@code x^2 + y^2} preserves ordering for non-negative inputs (and the
 * Euclidean distance is non-negative), so we can skip the {@code Math.sqrt}.
 * Faster and avoids floating-point precision pitfalls.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0973_KClosestPointsToOrigin
 * </pre>
 */
public class LC0973_KClosestPointsToOrigin {

    /** Max-heap of size k — O(n log k). */
    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> dist(b) - dist(a));
        for (int[] p : points) {
            heap.offer(p);
            if (heap.size() > k) heap.poll();
        }
        return heap.toArray(new int[0][]);
    }

    private static int dist(int[] p) {
        return p[0] * p[0] + p[1] * p[1];
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.deepToString(
            kClosest(new int[][]{{1, 3}, {-2, 2}}, 1)));   // [[-2, 2]]
        System.out.println(java.util.Arrays.deepToString(
            kClosest(new int[][]{{3, 3}, {5, -1}, {-2, 4}}, 2)));   // [[3, 3], [-2, 4]] or [[-2, 4], [3, 3]]
    }
}
