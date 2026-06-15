package dsa.greedy.leetcode;

/**
 * LeetCode 45 — Jump Game II
 * Difficulty: Medium   Tags: Array, Dynamic Programming, Greedy
 * URL: https://leetcode.com/problems/jump-game-ii/
 *
 * <h2>Problem</h2>
 * Given a 0-indexed array of integers {@code nums} where {@code nums[i]} is
 * the maximum jump length from position i, return the minimum number of jumps
 * to reach the last index. Assume you can always reach it.
 *
 * <h2>Approach — greedy BFS-style</h2>
 * Track {@code currentEnd} (farthest we can reach with the current number of
 * jumps) and {@code farthest} (the best we could reach if we add one more
 * jump). When i reaches {@code currentEnd}, take a jump: increment count and
 * set {@code currentEnd = farthest}.
 *
 * <h2>Why greedy works here</h2>
 * Each "level" is what's reachable with k jumps. Within a level, we look as
 * far ahead as possible; once we exhaust the level, we must commit to one
 * more jump. This is BFS layers in disguise.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0045_JumpGameII
 * </pre>
 */
public class LC0045_JumpGameII {

    public static int jump(int[] nums) {
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

    public static void main(String[] args) {
        System.out.println(jump(new int[]{2, 3, 1, 1, 4}));   // 2
        System.out.println(jump(new int[]{2, 3, 0, 1, 4}));   // 2
        System.out.println(jump(new int[]{1}));                // 0
    }
}
