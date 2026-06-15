package dsa.greedy.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 435 — Non-overlapping Intervals
 * Difficulty: Medium   Tags: Array, Dynamic Programming, Greedy, Sorting
 * URL: https://leetcode.com/problems/non-overlapping-intervals/
 *
 * <h2>Problem</h2>
 * Given an array of intervals, return the minimum number of intervals you
 * need to remove to make the rest non-overlapping. Two intervals that only
 * touch endpoints (e.g. [1,2] and [2,3]) are considered non-overlapping.
 *
 * <h2>Approach — greedy by END time</h2>
 * Sort intervals by end time ascending. Walk through; keep the first one,
 * and for each next interval take it iff it starts at or after the previous
 * kept interval's end. Anything skipped counts as a removal.
 *
 * <h2>Why sort by end, not start</h2>
 * Choosing the interval that ends earliest leaves the most room for what
 * follows. Sorting by start (and keeping the first) can force later collisions.
 *
 * <h2>Exchange argument</h2>
 * Suppose an optimal solution doesn't include the earliest-ending interval at
 * some step. Swap that interval in: the rest still fits (its end is no later),
 * so the new solution is also optimal. Induction.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0435_NonOverlappingIntervals
 * </pre>
 */
public class LC0435_NonOverlappingIntervals {

    public static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int kept = 1, end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) {
                kept++;
                end = intervals[i][1];
            }
        }
        return intervals.length - kept;
    }

    public static void main(String[] args) {
        System.out.println(eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}})); // 1
        System.out.println(eraseOverlapIntervals(new int[][]{{1, 2}, {1, 2}, {1, 2}}));          // 2
        System.out.println(eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}}));                  // 0
    }
}
