package dsa.recursion.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 17 — Letter Combinations of a Phone Number
 * Difficulty: Medium   Tags: Hash Table, String, Backtracking
 * URL: https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 *
 * <h2>Problem</h2>
 * Given a string containing digits 2-9, return all possible letter combinations
 * that the number could represent. Mapping is the classic phone keypad
 * (2 -&gt; abc, 3 -&gt; def, ...). Return order doesn't matter.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "23"  -> ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 *   ""    -> []
 *   "2"   -> ["a","b","c"]
 * </pre>
 *
 * <h2>Approach — backtracking</h2>
 * For each digit, append each of its letters and recurse to the next digit.
 * Pop the letter after recursion to try the next option (use a StringBuilder
 * and setLength to keep allocations low).
 *
 * <h2>Why backtracking, not DP</h2>
 * The answer space is enumeration of all 3^n / 4^n combinations — there are
 * no overlapping subproblems to memoise.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.recursion.leetcode.LC0017_LetterCombinations
 * </pre>
 */
public class LC0017_LetterCombinations {

    private static final String[] MAP = {
        "",     "",     "abc",  "def",  "ghi",
        "jkl",  "mno",  "pqrs", "tuv",  "wxyz"
    };

    public static List<String> letterCombinations(String digits) {
        List<String> out = new ArrayList<>();
        if (digits.isEmpty()) return out;
        backtrack(digits, 0, new StringBuilder(), out);
        return out;
    }

    private static void backtrack(String digits, int idx, StringBuilder path, List<String> out) {
        if (idx == digits.length()) { out.add(path.toString()); return; }
        for (char c : MAP[digits.charAt(idx) - '0'].toCharArray()) {
            path.append(c);
            backtrack(digits, idx + 1, path, out);
            path.setLength(path.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(letterCombinations("23"));     // [ad, ae, af, bd, be, bf, cd, ce, cf]
        System.out.println(letterCombinations(""));        // []
        System.out.println(letterCombinations("2"));       // [a, b, c]
    }
}
