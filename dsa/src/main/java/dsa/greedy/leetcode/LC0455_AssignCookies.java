package dsa.greedy.leetcode;

import java.util.Arrays;

/**
 * LeetCode 455 — Assign Cookies
 * Difficulty: Easy   Tags: Array, Two Pointers, Greedy, Sorting
 * URL: https://leetcode.com/problems/assign-cookies/
 *
 * <h2>Problem</h2>
 * Each child {@code i} has a greed factor {@code g[i]} (minimum cookie size to
 * satisfy them). Each cookie {@code j} has a size {@code s[j]}. Match each
 * child to at most one cookie. A child is content if their greed &le; the
 * cookie's size. Return the maximum number of content children.
 *
 * <h2>Approach — sort both + two pointers</h2>
 * Sort children and cookies ascending. Walk both; whenever the current cookie
 * satisfies the current child, count them and advance both. Otherwise advance
 * the cookie (try a larger one).
 *
 * <h2>Why greedy works</h2>
 * Pair the least greedy child with the smallest sufficient cookie. Larger
 * cookies are then preserved for greedier children — never wasted.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0455_AssignCookies
 * </pre>
 */
public class LC0455_AssignCookies {

    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0, j = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) i++;
            j++;
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println(findContentChildren(new int[]{1, 2, 3}, new int[]{1, 1}));   // 1
        System.out.println(findContentChildren(new int[]{1, 2}, new int[]{1, 2, 3}));   // 2
    }
}
