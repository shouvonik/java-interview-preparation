package dsa.heaps.exercises.solutions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Reference solutions for HeapsExercises.
 */
public class HeapsSolutions {

    // Exercise 1 — Top K Frequent via bucket sort by frequency. O(n).
    static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) counts.merge(n, 1, Integer::sum);
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (var e : counts.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(e.getKey());
        }
        int[] out = new int[k];
        int idx = 0;
        for (int f = buckets.length - 1; f >= 1 && idx < k; f--) {
            if (buckets[f] == null) continue;
            for (int v : buckets[f]) {
                if (idx == k) break;
                out[idx++] = v;
            }
        }
        return out;
    }

    // Exercise 2 — Max-heap of size k, keyed by squared distance.
    static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> dist(b) - dist(a));
        for (int[] p : points) {
            heap.offer(p);
            if (heap.size() > k) heap.poll();
        }
        return heap.toArray(new int[0][]);
    }

    private static int dist(int[] p) { return p[0] * p[0] + p[1] * p[1]; }

    // Exercise 3 — Greedy via min-heap; cost = sum of combinations.
    static int connectRopes(int[] ropes) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int r : ropes) heap.offer(r);
        int cost = 0;
        while (heap.size() > 1) {
            int combined = heap.poll() + heap.poll();
            cost += combined;
            heap.offer(combined);
        }
        return cost;
    }

    // Exercise 4 — Task Scheduler closed-form formula.
    static int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char t : tasks) freq[t - 'A']++;
        int maxCount = 0, numMax = 0;
        for (int f : freq) {
            if (f > maxCount) { maxCount = f; numMax = 1; }
            else if (f == maxCount) numMax++;
        }
        return Math.max(tasks.length, (maxCount - 1) * (n + 1) + numMax);
    }

    public static void main(String[] args) {
        System.out.println("topKFrequent  = "
            + java.util.Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));  // [1, 2]
        System.out.println("kClosest      = "
            + java.util.Arrays.deepToString(kClosest(new int[][]{{1, 3}, {-2, 2}}, 1)));  // [[-2, 2]]
        System.out.println("connectRopes  = " + connectRopes(new int[]{4, 3, 2, 6}));     // 29
        System.out.println("leastInterval = " + leastInterval(new char[]{'A','A','A','B','B','B'}, 2)); // 8
    }
}
