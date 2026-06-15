package dsa.stacksqueues.exercises.solutions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Reference solutions for StacksQueuesExercises.
 */
public class StacksQueuesSolutions {

    // Exercise 1 — Daily Temperatures via monotonic decreasing stack of indices.
    static int[] dailyTemperatures(int[] temperatures) {
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

    // Exercise 2 — Eval RPN. Pop b then a so a is left, then push a op b.
    static int evalRPN(String[] tokens) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (String tok : tokens) {
            switch (tok) {
                case "+" -> { int b = stack.pop(), a = stack.pop(); stack.push(a + b); }
                case "-" -> { int b = stack.pop(), a = stack.pop(); stack.push(a - b); }
                case "*" -> { int b = stack.pop(), a = stack.pop(); stack.push(a * b); }
                case "/" -> { int b = stack.pop(), a = stack.pop(); stack.push(a / b); }
                default  -> stack.push(Integer.parseInt(tok));
            }
        }
        return stack.peek();
    }

    // Exercise 3 — Queue using two stacks. Amortised O(1).
    public static class MyQueue {
        private final Deque<Integer> in = new ArrayDeque<>();
        private final Deque<Integer> out = new ArrayDeque<>();

        public void push(int x) { in.push(x); }

        public int pop()  { shuttle(); return out.pop(); }
        public int peek() { shuttle(); return out.peek(); }
        public boolean empty() { return in.isEmpty() && out.isEmpty(); }

        private void shuttle() {
            if (out.isEmpty()) while (!in.isEmpty()) out.push(in.pop());
        }
    }

    // Exercise 4 — Largest Rectangle. Monotonic increasing stack with sentinel.
    static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        int best = 0;
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
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
        System.out.println("dailyTemperatures = "
            + Arrays.toString(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        // [1, 1, 4, 2, 1, 1, 0, 0]

        System.out.println("evalRPN           = " + evalRPN(new String[]{"2", "1", "+", "3", "*"})); // 9

        MyQueue q = new MyQueue();
        q.push(1); q.push(2);
        System.out.println("queue.peek = " + q.peek() + ", pop = " + q.pop() + ", empty = " + q.empty());
        // peek = 1, pop = 1, empty = false

        System.out.println("largestRect       = " + largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3})); // 10
    }
}
