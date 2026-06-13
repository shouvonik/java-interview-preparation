package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 84 — Largest Rectangle in Histogram
 * Difficulty: Hard   Tags: Array, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/largest-rectangle-in-histogram/
 *
 * <h2>Problem</h2>
 * Given an array of integers {@code heights} representing the histogram's bar
 * heights where the width of each bar is 1, return the area of the largest
 * rectangle in the histogram.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [2,1,5,6,2,3]  -> 10   (5 and 6 form a 2-wide, 5-tall rectangle)
 *   [2, 4]          -> 4    (4 itself)
 *   [2,1,2]         -> 3    (1 with width 3)
 * </pre>
 *
 * <h2>Approach — monotonic increasing stack</h2>
 * For each bar, we want to know how far it can "stretch" left and right while
 * staying as the shortest in its window. The maximum width × height across all
 * bars is the answer.
 * <p>
 * Maintain a stack of indices whose heights are increasing. When the current
 * bar is shorter than the top, pop bars off: each popped bar's "width" is
 * {@code i - newTop - 1} (or {@code i} if stack empty). Update the best area.
 * Append a sentinel zero at the end to flush the stack.
 *
 * <h2>Why monotonic</h2>
 * As we walk, the stack always holds candidates whose contribution we haven't
 * finalised yet — and those candidates are increasing. Any shorter bar
 * encountered downstream finalises them.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Strictly increasing — final flush at the sentinel finalises all.</li>
 *   <li>Strictly decreasing — each bar is popped as soon as the next arrives.</li>
 *   <li>All equal — single rectangle = height × length.</li>
 *   <li>Single bar — area is its own height.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0084_LargestRectangleInHistogram
 * </pre>
 */
public class LC0084_LargestRectangleInHistogram {

    /** Monotonic increasing stack with sentinel — O(n). */
    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int best = 0;
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];          // sentinel 0 at end
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int top = stack.pop();
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                best = Math.max(best, heights[top] * width);
            }
            stack.push(i);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));   // 10
        System.out.println(largestRectangleArea(new int[]{2, 4}));                // 4
        System.out.println(largestRectangleArea(new int[]{2, 1, 2}));             // 3
        System.out.println(largestRectangleArea(new int[]{1}));                    // 1
    }
}
