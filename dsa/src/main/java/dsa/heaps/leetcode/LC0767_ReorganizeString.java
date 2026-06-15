package dsa.heaps.leetcode;

import java.util.PriorityQueue;

/**
 * LeetCode 767 — Reorganize String
 * Difficulty: Medium   Tags: Hash Table, String, Greedy, Sorting, Heap, Counting
 * URL: https://leetcode.com/problems/reorganize-string/
 *
 * <h2>Problem</h2>
 * Given a string {@code s}, rearrange the characters so that no two adjacent
 * characters are the same. Return any valid rearrangement, or "" if impossible.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "aab"  -> "aba"
 *   "aaab" -> ""
 *   "vvvlo" -> "vlvov" (any valid arrangement)
 * </pre>
 *
 * <h2>Approach — greedy with a max-heap of (count, char)</h2>
 * Repeatedly pop the two most-frequent letters, append one of each to the
 * output, decrement their counts, push them back if still &gt; 0. This greedy
 * choice prevents the most-common letter from ever clustering.
 *
 * <h2>When is it impossible?</h2>
 * If the most-frequent letter's count exceeds {@code (n + 1) / 2}, return "".
 * Such a letter can't be spaced out with only n - maxCount slots between
 * its occurrences.
 *
 * <h2>Why pop TWO at a time</h2>
 * Popping one and immediately pushing it back would let the same letter come
 * out twice in a row. Popping two ensures the second letter "separates" the
 * top one from itself.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single character — return it.</li>
 *   <li>Already valid — algorithm still produces a valid (possibly different)
 *       rearrangement.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0767_ReorganizeString
 * </pre>
 */
public class LC0767_ReorganizeString {

    /** Max-heap greedy — O(n log k) where k = 26. */
    public static String reorganizeString(String s) {
        int n = s.length();
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;
        int maxC = 0;
        for (int c : count) maxC = Math.max(maxC, c);
        if (maxC > (n + 1) / 2) return "";

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);  // (count, char)
        for (int i = 0; i < 26; i++) if (count[i] > 0) pq.offer(new int[]{count[i], i});

        StringBuilder sb = new StringBuilder();
        while (pq.size() >= 2) {
            int[] a = pq.poll();
            int[] b = pq.poll();
            sb.append((char) ('a' + a[1])).append((char) ('a' + b[1]));
            if (--a[0] > 0) pq.offer(a);
            if (--b[0] > 0) pq.offer(b);
        }
        if (!pq.isEmpty()) sb.append((char) ('a' + pq.peek()[1]));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(reorganizeString("aab"));      // aba
        System.out.println(reorganizeString("aaab"));     // ""
        System.out.println(reorganizeString("vvvlo"));    // some valid arrangement
        System.out.println(reorganizeString("a"));        // a
    }
}
