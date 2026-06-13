package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * LeetCode 503 — Next Greater Element II
 * Difficulty: Medium   Tags: Array, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/next-greater-element-ii/
 *
 * <h2>Problem</h2>
 * Given a CIRCULAR array {@code nums} (the next element of {@code nums[n-1]}
 * is {@code nums[0]}), return the next greater number for every element. If
 * no greater element exists, output -1 for that position.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,1]     -> [2,-1,2]
 *   [1,2,3,4,3] -> [2,3,4,-1,4]
 * </pre>
 *
 * <h2>Approach — monotonic stack over 2n iterations</h2>
 * Same as LC 496 but walk indices {@code 0..2n-1}, using modular index
 * {@code i % n}. We push indices once each (the second pass only pops the
 * stragglers that needed wrap-around).
 *
 * <h2>Why 2n iterations suffice</h2>
 * After visiting every element twice, any unresolved index could not have a
 * next-greater anywhere in the circular array — leave it as -1.
 *
 * <h2>Stack of indices, not values</h2>
 * We need to write into {@code result[idx]} when we pop, so the stack holds
 * indices. Push the modular index; compare values via {@code nums[stack.peek()]}.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0503_NextGreaterElementII
 * </pre>
 */
public class LC0503_NextGreaterElementII {

    /** Monotonic stack with 2n walk — O(n). */
    public static int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        Arrays.fill(out, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * n; i++) {
            int v = nums[i % n];
            while (!stack.isEmpty() && nums[stack.peek()] < v) {
                out[stack.pop()] = v;
            }
            if (i < n) stack.push(i);                       // push indices only first pass
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{1, 2, 1})));        // [2, -1, 2]
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{1, 2, 3, 4, 3})));   // [2, 3, 4, -1, 4]
        System.out.println(Arrays.toString(nextGreaterElements(new int[]{5, 4, 3, 2, 1})));   // [-1, 5, 5, 5, 5]
    }
}
