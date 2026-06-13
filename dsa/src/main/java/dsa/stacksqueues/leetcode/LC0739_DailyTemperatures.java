package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 739 — Daily Temperatures
 * Difficulty: Medium   Tags: Array, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/daily-temperatures/
 *
 * <h2>Problem</h2>
 * Given an array of integers {@code temperatures} representing daily
 * temperatures, return an array {@code answer} such that {@code answer[i]} is
 * the number of days until a warmer day. If no future day is warmer, set
 * {@code answer[i] == 0}.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [73,74,75,71,69,72,76,73]  -> [1,1,4,2,1,1,0,0]
 *   [30,40,50,60]              -> [1,1,1,0]
 *   [30,60,90]                  -> [1,1,0]
 * </pre>
 *
 * <h2>Approach — monotonic decreasing stack of indices</h2>
 * Walk left to right. The stack holds indices of days awaiting a warmer day.
 * For each day {@code i}, while the stack top's temperature is less than today's,
 * pop and record {@code answer[poppedIdx] = i - poppedIdx}. Push {@code i}.
 *
 * <h2>Why this is O(n)</h2>
 * Each index is pushed and popped at most once.
 *
 * <h2>Why "stack of indices"</h2>
 * The output is days-until, which is an INDEX difference. Storing indices lets
 * us write into the right slot when we pop.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0739_DailyTemperatures
 * </pre>
 */
public class LC0739_DailyTemperatures {

    /** Monotonic stack — O(n). */
    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] out = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                int j = stack.pop();
                out[j] = i - j;
            }
            stack.push(i);
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(
            dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));   // [1, 1, 4, 2, 1, 1, 0, 0]
        System.out.println(java.util.Arrays.toString(
            dailyTemperatures(new int[]{30, 40, 50, 60})));                    // [1, 1, 1, 0]
        System.out.println(java.util.Arrays.toString(
            dailyTemperatures(new int[]{30, 60, 90})));                        // [1, 1, 0]
    }
}
