package dsa.greedy.exercises.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Reference solutions for GreedyExercises.
 */
public class GreedySolutions {

    // Exercise 1 — Gas Station. Single pass, reset start when tank goes negative.
    static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) { start = i + 1; tank = 0; }
        }
        return total < 0 ? -1 : start;
    }

    // Exercise 2 — Non-overlapping Intervals. Greedy by end, count removals.
    static int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));
        int kept = 1, end = intervals[0][1];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= end) { kept++; end = intervals[i][1]; }
        }
        return intervals.length - kept;
    }

    // Exercise 3 — Jump Game II. Track current-end and farthest; commit a jump
    // when we reach current-end.
    static int jump(int[] nums) {
        int jumps = 0, currentEnd = 0, farthest = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;
            }
        }
        return jumps;
    }

    // Exercise 4 — Partition Labels. Track last-occurrence; close partition
    // when i catches up to the accumulated end.
    static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;
        List<Integer> out = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                out.add(end - start + 1);
                start = i + 1;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("canCompleteCircuit  = "
            + canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));   // 3
        System.out.println("eraseOverlap        = "
            + eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));         // 1
        System.out.println("jump                = " + jump(new int[]{2, 3, 1, 1, 4}));    // 2
        System.out.println("partitionLabels     = " + partitionLabels("ababcbacadefegdehijhklij")); // [9, 7, 8]
    }
}
