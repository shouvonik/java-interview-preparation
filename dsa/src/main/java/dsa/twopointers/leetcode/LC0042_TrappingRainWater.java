package dsa.twopointers.leetcode;

/**
 * LeetCode 42 — Trapping Rain Water
 * Difficulty: Hard   Tags: Array, Two Pointers, Dynamic Programming, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/trapping-rain-water/
 *
 * <h2>Problem</h2>
 * Given n non-negative integers representing an elevation map where the width
 * of each bar is 1, compute how much water it can trap after raining.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == height.length</li>
 *   <li>1 &le; n &le; 2 * 10^4</li>
 *   <li>0 &le; height[i] &le; 10^5</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [0,1,0,2,1,0,1,3,2,1,2,1]  -> 6
 *   [4, 2, 0, 3, 2, 5]          -> 9
 * </pre>
 *
 * <h2>Key insight</h2>
 * At any index {@code i}, water trapped is:
 * {@code max(0, min(maxLeft(i), maxRight(i)) - height[i])}.
 * Either compute {@code maxLeft} and {@code maxRight} arrays (O(n) space) or
 * use two pointers to do it in O(1) extra space.
 *
 * <h2>Approach 1 — precomputed max arrays — O(n) time, O(n) space</h2>
 * {@code maxLeft[i]} = max of {@code height[0..i]}.
 * {@code maxRight[i]} = max of {@code height[i..n-1]}.
 * Sum {@code min(maxLeft[i], maxRight[i]) - height[i]} for each i.
 *
 * <h2>Approach 2 — two pointers (canonical) — O(n) time, O(1) space</h2>
 * Maintain {@code left, right, maxLeft, maxRight}. At each step, advance from
 * the SHORTER side: if {@code height[left] < height[right]}, work on the left.
 * Update {@code maxLeft}, add {@code maxLeft - height[left]} to the total,
 * then increment {@code left}. Mirror on the right.
 *
 * <h2>Why advancing the shorter side is correct</h2>
 * If the left wall is shorter than the right, then for the current cell on the
 * left, the binding constraint is {@code maxLeft} (because some wall on the
 * right is at least {@code height[right]} &ge; {@code height[left]} — enough
 * to hold whatever {@code maxLeft} permits).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Monotonically increasing or decreasing — traps 0.</li>
 *   <li>Single peak — traps 0.</li>
 *   <li>All zeros — traps 0.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0042_TrappingRainWater
 * </pre>
 */
public class LC0042_TrappingRainWater {

    /** Two pointers — O(n) time, O(1) space. */
    public static int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int maxLeft = 0, maxRight = 0, total = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= maxLeft) maxLeft = height[left];
                else                          total += maxLeft - height[left];
                left++;
            } else {
                if (height[right] >= maxRight) maxRight = height[right];
                else                            total += maxRight - height[right];
                right--;
            }
        }
        return total;
    }

    /** Precomputed max-from-left / max-from-right — O(n) time, O(n) space. */
    public static int trapPrecomputed(int[] height) {
        int n = height.length;
        int[] maxLeft = new int[n], maxRight = new int[n];
        maxLeft[0] = height[0];
        for (int i = 1; i < n; i++) maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
        maxRight[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) maxRight[i] = Math.max(maxRight[i + 1], height[i]);
        int total = 0;
        for (int i = 0; i < n; i++) total += Math.min(maxLeft[i], maxRight[i]) - height[i];
        return total;
    }

    public static void main(String[] args) {
        System.out.println(trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));   // 6
        System.out.println(trap(new int[]{4, 2, 0, 3, 2, 5}));                       // 9
        System.out.println(trap(new int[]{1, 2, 3, 4}));                             // 0
        System.out.println(trap(new int[]{0}));                                       // 0
    }
}
