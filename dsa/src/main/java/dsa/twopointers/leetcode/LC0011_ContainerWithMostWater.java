package dsa.twopointers.leetcode;

/**
 * LeetCode 11 — Container With Most Water
 * Difficulty: Medium   Tags: Array, Two Pointers, Greedy
 * URL: https://leetcode.com/problems/container-with-most-water/
 *
 * <h2>Problem</h2>
 * You are given an integer array {@code height} of length n. There are n
 * vertical lines drawn at positions {@code (i, 0)} to {@code (i, height[i])}.
 * Find two lines that, together with the x-axis, form a container holding the
 * most water. Return the maximum amount of water it can store.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == height.length</li>
 *   <li>2 &le; n &le; 10^5</li>
 *   <li>0 &le; height[i] &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,8,6,2,5,4,8,3,7]  -> 49   (lines at index 1 and 8, height min(8,7)=7, width 7)
 *   [1, 1]               -> 1
 * </pre>
 *
 * <h2>Approach — opposite-ends two pointers + greedy move</h2>
 * Start with {@code left = 0, right = n - 1}. The area is
 * {@code (right - left) * min(height[left], height[right])}. Move the pointer
 * pointing to the SHORTER wall inward; track the best area.
 *
 * <h2>Why moving the shorter wall is safe</h2>
 * If we kept the shorter wall and moved the taller one inward, the width
 * decreases AND the height is still bounded by the (still-short) shorter wall.
 * So the area can only stay the same or shrink. Moving the shorter wall is the
 * only move with potential upside (a taller new wall).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Two-element array — area = min of the two.</li>
 *   <li>Equal heights everywhere — width dominates; answer = max width * height.</li>
 *   <li>Zero heights mixed in — the algorithm naturally skips past them.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0011_ContainerWithMostWater
 * </pre>
 */
public class LC0011_ContainerWithMostWater {

    /** Opposite-ends, greedy short-side move — O(n). */
    public static int maxArea(int[] height) {
        int left = 0, right = height.length - 1, best = 0;
        while (left < right) {
            int h = Math.min(height[left], height[right]);
            best = Math.max(best, h * (right - left));
            if (height[left] < height[right]) left++;
            else                              right--;
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));  // 49
        System.out.println(maxArea(new int[]{1, 1}));                       // 1
        System.out.println(maxArea(new int[]{4, 3, 2, 1, 4}));              // 16
        System.out.println(maxArea(new int[]{1, 2, 1}));                    // 2
    }
}
