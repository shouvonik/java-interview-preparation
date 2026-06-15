package dsa.stacksqueues.exercises;

/**
 * Stacks & Queues — exercises.
 */
public class StacksQueuesExercises {

    /**
     * Exercise 1 — Daily Temperatures (LeetCode 739).
     * For each day, how many days until a warmer one? 0 if none.
     * Example: [73,74,75,71,69,72,76,73] -> [1,1,4,2,1,1,0,0].
     */
    static int[] dailyTemperatures(int[] temperatures) {
        // TODO — monotonic stack of indices.
        return new int[0];
    }

    /**
     * Exercise 2 — Evaluate Reverse Polish Notation (LeetCode 150).
     * Tokens like {"2", "1", "+", "3", "*"} -> 9.
     */
    static int evalRPN(String[] tokens) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Implement Queue using Stacks (LeetCode 232).
     * Provide push, pop, peek, empty. Aim for amortised O(1) per op.
     */
    public static class MyQueue {
        // TODO — use two stacks (in / out).
        public void push(int x) {}
        public int pop()        { return -1; }
        public int peek()       { return -1; }
        public boolean empty()  { return true; }
    }

    /**
     * Exercise 4 — Largest Rectangle in Histogram (LeetCode 84, hard).
     * Monotonic increasing stack with a sentinel.
     * Example: [2,1,5,6,2,3] -> 10.
     */
    static int largestRectangleArea(int[] heights) {
        // TODO
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("dailyTemperatures = "
            + java.util.Arrays.toString(dailyTemperatures(new int[]{73, 74, 75, 71, 69, 72, 76, 73})));
        System.out.println("evalRPN           = " + evalRPN(new String[]{"2", "1", "+", "3", "*"}));
        MyQueue q = new MyQueue();
        q.push(1); q.push(2);
        System.out.println("queue.peek = " + q.peek() + ", pop = " + q.pop() + ", empty = " + q.empty());
        System.out.println("largestRect       = " + largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }
}
