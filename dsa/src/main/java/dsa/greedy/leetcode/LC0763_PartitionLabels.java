package dsa.greedy.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 763 — Partition Labels
 * Difficulty: Medium   Tags: Hash Table, Two Pointers, String, Greedy
 * URL: https://leetcode.com/problems/partition-labels/
 *
 * <h2>Problem</h2>
 * You are given a string {@code s}. Partition the string into as many parts
 * as possible such that each letter appears in at most one part. Return a
 * list of integers representing the size of each part.
 *
 * <h2>Examples</h2>
 * <pre>
 *   "ababcbacadefegdehijhklij"  -> [9, 7, 8]
 *   "eccbbbbdec"                 -> [10]
 * </pre>
 *
 * <h2>Approach — last-occurrence sweep</h2>
 * Pass 1: for each letter, record its last index. Pass 2: walk the string,
 * tracking {@code end = max(end, lastIndex[char])}. When {@code i == end},
 * we've reached a "closed" partition — emit its length and start the next.
 *
 * <h2>Why this is correct</h2>
 * The current chunk must extend at least up to the LAST occurrence of every
 * character that has appeared so far. Once {@code i} catches up to that
 * accumulated end, the chunk is self-contained.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0763_PartitionLabels
 * </pre>
 */
public class LC0763_PartitionLabels {

    public static List<Integer> partitionLabels(String s) {
        int[] last = new int[26];
        for (int i = 0; i < s.length(); i++) last[s.charAt(i) - 'a'] = i;

        List<Integer> out = new ArrayList<>();
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            end = Math.max(end, last[s.charAt(i) - 'a']);
            if (i == end) {
                out.add(end - start + 1);
                start = i + 1;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(partitionLabels("ababcbacadefegdehijhklij"));   // [9, 7, 8]
        System.out.println(partitionLabels("eccbbbbdec"));                  // [10]
    }
}
