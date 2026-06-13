package dsa.stacksqueues.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * LeetCode 20 — Valid Parentheses
 * Difficulty: Easy   Tags: String, Stack
 * URL: https://leetcode.com/problems/valid-parentheses/
 *
 * <h2>Problem</h2>
 * Given a string {@code s} containing just the characters '(', ')', '{', '}',
 * '[', ']', determine if the input string is valid. An input is valid if:
 * <ul>
 *   <li>Open brackets are closed by the same type of brackets.</li>
 *   <li>Open brackets are closed in the correct order.</li>
 *   <li>Every close bracket has a corresponding open bracket of the same type.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   "()"        -> true
 *   "()[]{}"    -> true
 *   "(]"        -> false
 *   "([)]"      -> false
 *   "{[]}"      -> true
 *   ""          -> true
 * </pre>
 *
 * <h2>Approach — stack of expected closers</h2>
 * Push the matching closing bracket when you see an opener. On a closer, check
 * that the stack top matches. The string is valid iff the stack is empty at the end.
 *
 * <h2>Why stack of CLOSERS rather than openers</h2>
 * Pushing closers means the comparison on the top-of-stack is a simple equality
 * with the current character — no lookup table needed at pop time.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty string — true.</li>
 *   <li>Only closers — stack underflow attempt — false.</li>
 *   <li>Unclosed openers — non-empty stack at end — false.</li>
 *   <li>Odd-length strings — necessarily invalid; can early-return false.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0020_ValidParentheses
 * </pre>
 */
public class LC0020_ValidParentheses {

    /** Stack of expected closers — O(n). */
    public static boolean isValid(String s) {
        if ((s.length() & 1) == 1) return false;
        Map<Character, Character> match = Map.of('(', ')', '[', ']', '{', '}');
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (match.containsKey(c)) {
                stack.push(match.get(c));
            } else {
                if (stack.isEmpty() || stack.pop() != c) return false;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));        // true
        System.out.println(isValid("()[]{}"));    // true
        System.out.println(isValid("(]"));        // false
        System.out.println(isValid("([)]"));      // false
        System.out.println(isValid("{[]}"));      // true
        System.out.println(isValid(""));          // true
    }
}
