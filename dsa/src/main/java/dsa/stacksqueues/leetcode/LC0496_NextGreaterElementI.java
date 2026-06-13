package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 496 — Next Greater Element I
 * Difficulty: Easy   Tags: Array, Hash Table, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/next-greater-element-i/
 *
 * <h2>Problem</h2>
 * The next greater element of some element {@code x} in an array is the first
 * greater element to the right of {@code x} in the same array. Given two
 * 0-indexed arrays {@code nums1} (subset of {@code nums2}, unique elements),
 * find the next greater of each element of {@code nums1} as it appears in
 * {@code nums2}. Use -1 if none.
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums1=[4,1,2], nums2=[1,3,4,2]            -> [-1,3,-1]
 *   nums1=[2,4],   nums2=[1,2,3,4]            -> [3,-1]
 * </pre>
 *
 * <h2>Approach — monotonic decreasing stack + map</h2>
 * Walk {@code nums2}; maintain a stack of values waiting to find their next
 * greater. For each value {@code v}, pop while {@code stack.top() &lt; v} and
 * record {@code nextGreater[popped] = v}. Push {@code v}.
 * After the loop, any value still on the stack has no next greater — leave
 * unrecorded (its lookup will return -1).
 *
 * <h2>Why it's O(n + m)</h2>
 * Each element of nums2 is pushed once and popped at most once — O(n). Then
 * for each of m elements of nums1, look up in the map — O(m).
 *
 * <h2>Why a monotonic DECREASING stack</h2>
 * We pop when a NEW larger value arrives. Things still on the stack are
 * waiting; they must be in non-increasing order from bottom to top, else we'd
 * have already popped them.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0496_NextGreaterElementI
 * </pre>
 */
public class LC0496_NextGreaterElementI {

    /** Monotonic stack + map — O(n + m). */
    public static int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> next = new HashMap<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int v : nums2) {
            while (!stack.isEmpty() && stack.peek() < v) next.put(stack.pop(), v);
            stack.push(v);
        }
        int[] out = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) out[i] = next.getOrDefault(nums1[i], -1);
        return out;
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(
            nextGreaterElement(new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));   // [-1, 3, -1]
        System.out.println(java.util.Arrays.toString(
            nextGreaterElement(new int[]{2, 4}, new int[]{1, 2, 3, 4})));      // [3, -1]
    }
}
