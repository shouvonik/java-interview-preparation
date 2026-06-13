package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 155 — Min Stack
 * Difficulty: Medium   Tags: Stack, Design
 * URL: https://leetcode.com/problems/min-stack/
 *
 * <h2>Problem</h2>
 * Design a stack that supports {@code push}, {@code pop}, {@code top}, and
 * retrieving the minimum element, all in O(1).
 *
 * <h2>Approach — auxiliary "min-so-far" stack</h2>
 * Maintain a second stack tracking the running minimum. On push, push
 * {@code min(value, prev min)} onto the aux stack. On pop, pop the aux too.
 * {@code getMin} returns the aux top.
 *
 * <h2>Why a second stack rather than tracking one int</h2>
 * Tracking a single min as a field doesn't survive pop: if you pop the current
 * min, you've lost the second-best. The aux stack preserves the history of
 * mins at each prefix.
 *
 * <h2>Space optimisation</h2>
 * Only push to the aux stack when {@code value <= current min}. On pop, pop
 * aux iff the popped equals the current min. Same O(1) operations, often less
 * memory for inputs with rare new minima.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Mixing pushes / pops — aux stays in sync.</li>
 *   <li>Multiple copies of the same min value — handle by ≤ comparison in the
 *       space-optimised variant.</li>
 *   <li>{@code top} / {@code getMin} after only one push — both return that value.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0155_MinStack
 * </pre>
 */
public class LC0155_MinStack {

    public static class MinStack {
        private final Deque<Integer> values = new ArrayDeque<>();
        private final Deque<Integer> mins = new ArrayDeque<>();

        public void push(int val) {
            values.push(val);
            mins.push(mins.isEmpty() ? val : Math.min(val, mins.peek()));
        }

        public void pop() {
            values.pop();
            mins.pop();
        }

        public int top()    { return values.peek(); }
        public int getMin() { return mins.peek(); }
    }

    public static void main(String[] args) {
        MinStack s = new MinStack();
        s.push(-2); s.push(0); s.push(-3);
        System.out.println(s.getMin());   // -3
        s.pop();
        System.out.println(s.top());      // 0
        System.out.println(s.getMin());   // -2
    }
}
