package dsa.sorting.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * LeetCode 56 — Merge Intervals
 * Difficulty: Medium   Tags: Array, Sorting
 * URL: https://leetcode.com/problems/merge-intervals/
 *
 * <h2>Problem</h2>
 * Given an array of intervals {@code intervals[i] = [start_i, end_i]}, merge
 * all overlapping intervals and return an array of the non-overlapping
 * intervals that cover all the input intervals.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; intervals.length &le; 10^4</li>
 *   <li>0 &le; start_i &le; end_i &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[1,3],[2,6],[8,10],[15,18]]  ->  [[1,6],[8,10],[15,18]]
 *   [[1,4],[4,5]]                  ->  [[1,5]]
 *   [[1,4],[0,4]]                  ->  [[0,4]]
 *   [[1,4],[2,3]]                  ->  [[1,4]]   (second nested inside first)
 * </pre>
 *
 * <h2>Approach</h2>
 * 1. Sort intervals by start time — O(n log n).
 * 2. Sweep: keep a "current" interval; for each next one, if it overlaps
 *    (its start &le; current.end), extend current.end to max(current.end, next.end).
 *    Otherwise emit current and start fresh.
 *
 * <h2>Why sort by start</h2>
 * After sorting by start, overlapping intervals are always adjacent. Without
 * sorting, you'd need pairwise comparisons (O(n^2)).
 *
 * <h2>Touching counts as overlap</h2>
 * {@code [[1,4],[4,5]]} is treated as overlapping (typical LeetCode convention).
 * Use {@code next.start <= current.end} (not strict {@code &lt;}).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single interval — returned as-is.</li>
 *   <li>One nested inside another — extend doesn't shrink, so it's preserved.</li>
 *   <li>All non-overlapping — output equals sorted input.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0056_MergeIntervals
 * </pre>
 */
public class LC0056_MergeIntervals {

    /** Sort by start + linear sweep — O(n log n) time, O(n) output. */
    public static int[][] merge(int[][] intervals) {
        if (intervals.length == 0) return new int[0][];
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        List<int[]> out = new ArrayList<>();
        int[] cur = intervals[0].clone();                   // clone so we don't mutate input
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            if (next[0] <= cur[1]) {
                cur[1] = Math.max(cur[1], next[1]);
            } else {
                out.add(cur);
                cur = next.clone();
            }
        }
        out.add(cur);
        return out.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
        // [[1, 6], [8, 10], [15, 18]]

        System.out.println(Arrays.deepToString(merge(new int[][]{{1, 4}, {4, 5}})));
        // [[1, 5]]

        System.out.println(Arrays.deepToString(merge(new int[][]{{1, 4}, {0, 4}})));
        // [[0, 4]]

        System.out.println(Arrays.deepToString(merge(new int[][]{{1, 4}, {2, 3}})));
        // [[1, 4]]
    }
}
