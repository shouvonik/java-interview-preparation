package dsa.stacksqueues;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Stacks & Queues — idioms and the monotonic-stack pattern.
 *
 * Run: java dsa.stacksqueues.StacksQueues
 *
 * Use ArrayDeque for both stack (push/pop/peek) and queue (offer/poll/peek).
 * It beats both Stack (synchronized) and LinkedList (cache-unfriendly).
 */
public class StacksQueues {

    // ---------------------------------------------------------------------
    // 1) Stack basics — parentheses validation.
    // ---------------------------------------------------------------------
    static boolean validParens(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '(' -> stack.push(')');
                case '[' -> stack.push(']');
                case '{' -> stack.push('}');
                default -> {
                    if (stack.isEmpty() || stack.pop() != c) return false;
                }
            }
        }
        return stack.isEmpty();
    }

    // ---------------------------------------------------------------------
    // 2) Queue basics — using ArrayDeque as FIFO.
    // ---------------------------------------------------------------------
    static int[] roundRobin(int[] items, int passes) {
        Deque<Integer> q = new ArrayDeque<>();
        for (int x : items) q.offer(x);
        for (int p = 0; p < passes; p++) {
            for (int i = 0, n = q.size(); i < n; i++) q.offer(q.poll());
        }
        int[] out = new int[q.size()];
        int idx = 0;
        for (int x : q) out[idx++] = x;
        return out;
    }

    // ---------------------------------------------------------------------
    // 3) Monotonic stack — next greater element (LeetCode 496-style).
    // ---------------------------------------------------------------------
    // Stack holds INDICES whose corresponding values are decreasing top-to-bottom.
    // When a new value larger than the top arrives, pop and record.
    static int[] nextGreater(int[] nums) {
        int n = nums.length;
        int[] out = new int[n];
        Arrays.fill(out, -1);
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                out[stack.pop()] = nums[i];
            }
            stack.push(i);
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // 4) Min stack — auxiliary stack of running mins.
    // ---------------------------------------------------------------------
    public static class MinStack {
        private final Deque<Integer> values = new ArrayDeque<>();
        private final Deque<Integer> mins = new ArrayDeque<>();

        public void push(int v) {
            values.push(v);
            mins.push(mins.isEmpty() ? v : Math.min(v, mins.peek()));
        }

        public void pop()       { values.pop(); mins.pop(); }
        public int top()        { return values.peek(); }
        public int getMin()     { return mins.peek(); }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("validParens(\"(){}[]\") = " + validParens("(){}[]")); // true
        System.out.println("validParens(\"([)]\")  = " + validParens("([)]"));    // false

        System.out.println("roundRobin([1,2,3], 1) = "
            + Arrays.toString(roundRobin(new int[]{1, 2, 3}, 1)));                  // [1, 2, 3]

        System.out.println("nextGreater([2,1,3,1]) = "
            + Arrays.toString(nextGreater(new int[]{2, 1, 3, 1})));                  // [3, 3, -1, -1]

        MinStack ms = new MinStack();
        ms.push(-2); ms.push(0); ms.push(-3);
        System.out.println("minStack.getMin = " + ms.getMin());                       // -3
        ms.pop();
        System.out.println("minStack.top    = " + ms.top());                          // 0
        System.out.println("minStack.getMin = " + ms.getMin());                       // -2
    }
}
