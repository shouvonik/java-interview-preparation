package dsa.greedy.exercises;

/**
 * Greedy — exercises.
 */
public class GreedyExercises {

    /**
     * Exercise 1 — Gas Station (LeetCode 134).
     * Find the starting index that allows you to circle the route, or -1.
     * Example: gas=[1,2,3,4,5], cost=[3,4,5,1,2] -> 3.
     */
    static int canCompleteCircuit(int[] gas, int[] cost) {
        // TODO
        return -1;
    }

    /**
     * Exercise 2 — Non-overlapping Intervals (LeetCode 435).
     * Min number of intervals to remove so the rest don't overlap.
     * Example: [[1,2],[2,3],[3,4],[1,3]] -> 1.
     */
    static int eraseOverlapIntervals(int[][] intervals) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Jump Game II (LeetCode 45).
     * Minimum number of jumps to reach the last index (always possible).
     * Example: [2,3,1,1,4] -> 2.
     */
    static int jump(int[] nums) {
        // TODO
        return 0;
    }

    /**
     * Exercise 4 — Partition Labels (LeetCode 763).
     * Partition s so each letter appears in at most one part; return part sizes.
     * Example: "ababcbacadefegdehijhklij" -> [9, 7, 8].
     */
    static java.util.List<Integer> partitionLabels(String s) {
        // TODO
        return java.util.List.of();
    }

    public static void main(String[] args) {
        System.out.println("canCompleteCircuit  = "
            + canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2}));
        System.out.println("eraseOverlap        = "
            + eraseOverlapIntervals(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}}));
        System.out.println("jump                = " + jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println("partitionLabels     = " + partitionLabels("ababcbacadefegdehijhklij"));
    }
}
