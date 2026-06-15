package dsa.heaps.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 347 — Top K Frequent Elements
 * Difficulty: Medium   Tags: Array, Hash Table, Sorting, Heap, Bucket Sort, Quickselect
 * URL: https://leetcode.com/problems/top-k-frequent-elements/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} and an integer {@code k}, return the
 * {@code k} most frequent elements. The answer may be returned in any order.
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1,1,1,2,2,3], k = 2  -> [1, 2]
 *   nums = [1], k = 1             -> [1]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Min-heap of size k</b> — O(n log k).
 * Count frequencies, then keep a min-heap of the top-k by frequency.
 * <p>
 * <b>Bucket sort (canonical, O(n))</b>.
 * Frequency is bounded by n; create buckets indexed by frequency. Walk
 * high-to-low filling the answer.
 *
 * <h2>Why bucket sort fits perfectly</h2>
 * Frequencies are integers in {@code [1..n]}, so they're a small fixed range.
 * No comparison sort needed.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k = number of distinct elements — return all distinct values.</li>
 *   <li>All identical — single element, returned k times? No: the problem says
 *       distinct elements, so just {[that value]}.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0347_TopKFrequentElements
 * </pre>
 */
public class LC0347_TopKFrequentElements {

    /** Bucket sort by frequency — O(n). */
    public static int[] topKFrequent(int[] nums, int k) {
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

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2))); // [1, 2]
        System.out.println(java.util.Arrays.toString(topKFrequent(new int[]{1}, 1)));                 // [1]
    }
}
