package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 22 — Generate Parentheses
 * Difficulty: Medium   Tags: String, Dynamic Programming, Backtracking
 * URL: https://leetcode.com/problems/generate-parentheses/
 *
 * <h2>Problem</h2>
 * Given {@code n} pairs of parentheses, write a function to generate all
 * combinations of well-formed parentheses.
 *
 * <h2>Examples</h2>
 * <pre>
 *   n = 3  -> ["((()))","(()())","(())()","()(())","()()()"]
 *   n = 1  -> ["()"]
 * </pre>
 *
 * <h2>Approach — backtracking with two counters</h2>
 * Track {@code open} and {@code close} counts used so far. We can:
 * <ul>
 *   <li>Append '(' if {@code open &lt; n}.</li>
 *   <li>Append ')' if {@code close &lt; open} (never close more than opened).</li>
 * </ul>
 * Stop when both reach n.
 *
 * <h2>Why this pruning works</h2>
 * Both rules together guarantee at any point the prefix is a valid (incomplete)
 * parenthesisation. We never explore branches that are doomed to invalid.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0022_GenerateParentheses
 * </pre>
 */
public class LC0022_GenerateParentheses {

    public static List<String> generateParenthesis(int n) {
        List<String> out = new ArrayList<>();
        backtrack(n, 0, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(int n, int open, int close, StringBuilder path, List<String> out) {
        if (path.length() == 2 * n) { out.add(path.toString()); return; }
        if (open < n) {
            path.append('(');
            backtrack(n, open + 1, close, path, out);
            path.setLength(path.length() - 1);
        }
        if (close < open) {
            path.append(')');
            backtrack(n, open, close + 1, path, out);
            path.setLength(path.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));  // [((())), (()()), (())(), ()(()), ()()()]
        System.out.println(generateParenthesis(1));  // [()]
    }
}
