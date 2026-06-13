package dsa.slidingwindow.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 239 — Sliding Window Maximum
 * Difficulty: Hard   Tags: Array, Queue, Sliding Window, Heap, Monotonic Queue
 * URL: https://leetcode.com/problems/sliding-window-maximum/
 *
 * <h2>Problem</h2>
 * You are given an array of integers {@code nums} and an integer {@code k}.
 * There is a sliding window of size k which is moving from the very left of
 * the array to the very right. Return an array of the max of each window.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-10^4 &le; nums[i] &le; 10^4</li>
 *   <li>1 &le; k &le; nums.length</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1,3,-1,-3,5,3,6,7], k = 3  ->  [3, 3, 5, 5, 6, 7]
 *   nums = [1], k = 1                   ->  [1]
 *   nums = [9, 11], k = 2               ->  [11]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Max-heap</b> — O(n log n).
 * Push each (value, index); when polling the max, discard entries with expired
 * index. Works but slower than the deque trick.
 * <p>
 * <b>Monotonic deque (canonical)</b> — O(n).
 * Maintain a deque of INDICES whose corresponding values are strictly decreasing
 * from front to back. The front index always holds the maximum value in the
 * current window.
 *
 * <h2>Deque rules</h2>
 * <ul>
 *   <li>Before adding {@code i}: while {@code nums[dq.last()] <= nums[i]}, poll
 *       last — the older value can never be a window max if a newer-and-bigger
 *       exists in the same or later window.</li>
 *   <li>While {@code dq.first() <= i - k}: poll first — that index has fallen
 *       out of the current window.</li>
 *   <li>After adding, if {@code i >= k - 1}, emit {@code nums[dq.first()]}.</li>
 * </ul>
 *
 * <h2>Why O(n)?</h2>
 * Each index is added and removed at most once. The combined work over all
 * iterations is O(n).
 *
 * <h2>Why store INDICES, not values?</h2>
 * We need the index to detect expiration (whether the front has fallen out of
 * the window). Values alone don't carry that information.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == 1 — output equals input (every single-element window is its own max).</li>
 *   <li>k == n — single output: the max of nums.</li>
 *   <li>All identical — every window max equals the value.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0239_SlidingWindowMaximum
 * </pre>
 */
public class LC0239_SlidingWindowMaximum {

    /** Monotonic deque — O(n). */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] out = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // Drop expired front.
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            // Maintain decreasing invariant.
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = nums[dq.peekFirst()];
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        // [3, 3, 5, 5, 6, 7]
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{1}, 1)));               // [1]
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{9, 11}, 2)));           // [11]
        System.out.println(Arrays.toString(maxSlidingWindow(new int[]{4, -2}, 2)));           // [4]
    }
}
