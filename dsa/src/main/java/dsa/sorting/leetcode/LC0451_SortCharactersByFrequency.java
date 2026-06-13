package dsa.sorting.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 451 — Sort Characters By Frequency
 * Difficulty: Medium   Tags: Hash Table, String, Sorting, Heap, Bucket Sort
 * URL: https://leetcode.com/problems/sort-characters-by-frequency/
 *
 * <h2>Problem</h2>
 * Given a string {@code s}, sort it in decreasing order based on the frequency
 * of the characters. Characters with the same frequency may appear in any order.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; s.length &le; 5 * 10^5</li>
 *   <li>s consists of uppercase, lowercase letters and digits.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   "tree"      -> "eert"   (or "eetr")
 *   "cccaaa"    -> "aaaccc" (or "cccaaa")
 *   "Aabb"      -> "bbAa"   (or "bbaA"; case sensitive)
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Hash count + sort entries</b> — O(n + k log k) where k = distinct chars.
 * Count, then sort entries by count desc, then build the output.
 * <p>
 * <b>Bucket sort (canonical)</b> — O(n) time.
 * Count, then place chars in buckets indexed by frequency. Walk buckets from
 * high to low, appending {@code freq} copies of each char.
 *
 * <h2>Why bucket sort fits</h2>
 * Frequency is bounded by string length; buckets indexed [0..n] cover all
 * cases. Linear scan over buckets is O(n) — no comparison sort needed.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single character — return it as-is.</li>
 *   <li>All distinct — any permutation is valid; canonical output sorts by char.</li>
 *   <li>Case sensitivity — treat 'A' and 'a' as different characters.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0451_SortCharactersByFrequency
 * </pre>
 */
public class LC0451_SortCharactersByFrequency {

    /** Bucket sort — O(n) time. */
    public static String frequencySort(String s) {
        Map<Character, Integer> count = new HashMap<>();
        for (char c : s.toCharArray()) count.merge(c, 1, Integer::sum);

        @SuppressWarnings("unchecked")
        StringBuilder[] buckets = new StringBuilder[s.length() + 1];
        for (var e : count.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new StringBuilder();
            // Append (f copies of) the character to its frequency bucket.
            for (int i = 0; i < f; i++) buckets[f].append(e.getKey());
        }

        StringBuilder out = new StringBuilder(s.length());
        for (int f = buckets.length - 1; f >= 1; f--) {
            if (buckets[f] != null) out.append(buckets[f]);
        }
        return out.toString();
    }

    public static void main(String[] args) {
        // Note: equal-frequency chars may appear in any order — verify by length & content.
        System.out.println(frequencySort("tree"));      // e.g. "eert" or "eetr"
        System.out.println(frequencySort("cccaaa"));    // "aaaccc" or "cccaaa"
        System.out.println(frequencySort("Aabb"));      // "bbAa" / "bbaA"
        System.out.println(frequencySort("a"));         // "a"
    }
}
