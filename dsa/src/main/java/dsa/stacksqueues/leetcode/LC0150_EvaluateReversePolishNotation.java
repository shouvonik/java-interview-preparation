package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 150 — Evaluate Reverse Polish Notation
 * Difficulty: Medium   Tags: Array, Math, Stack
 * URL: https://leetcode.com/problems/evaluate-reverse-polish-notation/
 *
 * <h2>Problem</h2>
 * Evaluate an arithmetic expression given in Reverse Polish Notation. Valid
 * operators are +, -, *, /. Each operand may be an integer or another
 * expression. Division between two integers should truncate toward zero.
 *
 * <h2>Examples</h2>
 * <pre>
 *   ["2","1","+","3","*"]                      -> 9    ((2 + 1) * 3)
 *   ["4","13","5","/","+"]                     -> 6    (4 + (13 / 5))
 *   ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]  -> 22
 * </pre>
 *
 * <h2>Approach — single-pass stack</h2>
 * Walk the tokens. If it's a number, push. If it's an operator, pop two
 * operands (b then a, so a is the left), compute {@code a op b}, push the
 * result. Final value on the stack is the answer.
 *
 * <h2>Watch the operand order</h2>
 * For non-commutative operators (- and /), the first popped value is the
 * RIGHT-hand side, the second popped is the LEFT-hand side. Reversing them
 * causes subtle bugs that only show up on certain inputs.
 *
 * <h2>Division semantics</h2>
 * Java's {@code int / int} truncates toward zero (e.g. {@code -7 / 2 == -3}),
 * which matches the problem statement.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0150_EvaluateReversePolishNotation
 * </pre>
 */
public class LC0150_EvaluateReversePolishNotation {

    /** Single-pass stack — O(n). */
    public static int evalRPN(String[] tokens) {
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

    public static void main(String[] args) {
        System.out.println(evalRPN(new String[]{"2", "1", "+", "3", "*"}));     // 9
        System.out.println(evalRPN(new String[]{"4", "13", "5", "/", "+"}));    // 6
        System.out.println(evalRPN(new String[]{
            "10","6","9","3","+","-11","*","/","*","17","+","5","+"}));         // 22
    }
}
