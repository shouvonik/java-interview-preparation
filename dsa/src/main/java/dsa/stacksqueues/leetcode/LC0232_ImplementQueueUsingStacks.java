package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 232 — Implement Queue using Stacks
 * Difficulty: Easy   Tags: Stack, Design, Queue
 * URL: https://leetcode.com/problems/implement-queue-using-stacks/
 *
 * <h2>Problem</h2>
 * Implement a first in first out (FIFO) queue using only two stacks. The
 * implemented queue should support {@code push}, {@code pop}, {@code peek},
 * and {@code empty}.
 *
 * <h2>Approach — two stacks: in / out</h2>
 * <ul>
 *   <li>{@code in} for new elements (push always goes here).</li>
 *   <li>{@code out} for the front (pop/peek). When {@code out} is empty,
 *       drain {@code in} into {@code out} by popping all of in and pushing
 *       onto out — that reverses the order so the front of the queue lands on
 *       the top of out.</li>
 * </ul>
 *
 * <h2>Amortised O(1)</h2>
 * Each element is pushed and popped on each stack at most once, so total work
 * across n operations is O(n). Individual {@code pop} can be O(k) when out is
 * empty and in has k elements, but amortised per-op cost is O(1).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>{@code peek} on empty — usually returns -1 or throws; check problem
 *       constraints. Here we follow LeetCode's "calls are always valid" rule.</li>
 *   <li>Interleaved pushes and pops — drain only when out is empty.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0232_ImplementQueueUsingStacks
 * </pre>
 */
public class LC0232_ImplementQueueUsingStacks {

    public static class MyQueue {
        private final Deque<Integer> in = new ArrayDeque<>();
        private final Deque<Integer> out = new ArrayDeque<>();

        public void push(int x) { in.push(x); }

        public int pop() {
            shuttle();
            return out.pop();
        }

        public int peek() {
            shuttle();
            return out.peek();
        }

        public boolean empty() {
            return in.isEmpty() && out.isEmpty();
        }

        private void shuttle() {
            if (out.isEmpty()) {
                while (!in.isEmpty()) out.push(in.pop());
            }
        }
    }

    public static void main(String[] args) {
        MyQueue q = new MyQueue();
        q.push(1); q.push(2);
        System.out.println(q.peek());     // 1
        System.out.println(q.pop());      // 1
        System.out.println(q.empty());    // false
    }
}
