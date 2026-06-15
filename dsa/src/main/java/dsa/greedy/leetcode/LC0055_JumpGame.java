package dsa.greedy.leetcode;

/**
 * LeetCode 55 — Jump Game
 * Difficulty: Medium   Tags: Array, Dynamic Programming, Greedy
 * URL: https://leetcode.com/problems/jump-game/
 *
 * <h2>Problem</h2>
 * You are given an integer array {@code nums}. You are initially positioned
 * at the array's first index, and each element represents your maximum jump
 * length at that position. Return true if you can reach the last index.
 *
 * <h2>Approach — greedy, track farthest reach</h2>
 * Walk left to right. Maintain {@code farthest} = max index reachable so far.
 * At index i, if {@code i > farthest}, we got stuck — return false. Update
 * {@code farthest = max(farthest, i + nums[i])}.
 *
 * <h2>Why this is sufficient</h2>
 * If at index i we can extend our reach, do so. If we ever reach an index
 * &ge; last index, we're done. The greedy never benefits from "saving" a
 * jump; reaching farther is always at least as good.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0055_JumpGame
 * </pre>
 */
public class LC0055_JumpGame {

    public static boolean canJump(int[] nums) {
        int farthest = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > farthest) return false;
            farthest = Math.max(farthest, i + nums[i]);
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(canJump(new int[]{2, 3, 1, 1, 4}));   // true
        System.out.println(canJump(new int[]{3, 2, 1, 0, 4}));   // false
        System.out.println(canJump(new int[]{0}));                // true (already there)
    }
}
