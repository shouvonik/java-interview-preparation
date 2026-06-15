package dsa.heaps.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * LeetCode 692 — Top K Frequent Words
 * Difficulty: Medium   Tags: Hash Table, String, Trie, Sorting, Heap, Bucket Sort, Counting
 * URL: https://leetcode.com/problems/top-k-frequent-words/
 *
 * <h2>Problem</h2>
 * Given an array of strings {@code words} and an integer {@code k}, return
 * the {@code k} most frequent strings. Sort the answer by frequency desc; for
 * ties, by lexicographic order asc.
 *
 * <h2>Examples</h2>
 * <pre>
 *   ["i","love","leetcode","i","love","coding"], k = 2  -> ["i","love"]
 *   ["the","day","is","sunny","the","the","the","sunny","is","is"], k = 4
 *      -> ["the","is","sunny","day"]
 * </pre>
 *
 * <h2>Approach — min-heap with custom comparator</h2>
 * Build a frequency map. Heap holds (word, count); ordering is:
 * <ul>
 *   <li>lower count first (so we evict the WORST candidate),</li>
 *   <li>tiebreak by lexicographically LARGER word first (so we evict the
 *       lexicographically-bigger string when counts tie).</li>
 * </ul>
 * Push all entries; if size &gt; k, poll. At the end, drain into a list and
 * reverse — the heap drains worst-first.
 *
 * <h2>Why the tie-breaker is inverted</h2>
 * The desired output sorts ties by ascending lexicographic order, which means
 * we want to KEEP smaller strings on ties — so the heap evicts the LARGER one.
 *
 * <h2>Complexity</h2>
 * O(n log k) for the heap, O(k log k) for the final reverse — overall
 * O(n log k).
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0692_TopKFrequentWords
 * </pre>
 */
public class LC0692_TopKFrequentWords {

    /** Min-heap of size k — O(n log k). */
    public static List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> counts = new HashMap<>();
        for (String w : words) counts.merge(w, 1, Integer::sum);

        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>((a, b) -> {
            if (!a.getValue().equals(b.getValue())) return a.getValue() - b.getValue();
            return b.getKey().compareTo(a.getKey());                 // larger string first => evict it
        });
        for (var e : counts.entrySet()) {
            pq.offer(e);
            if (pq.size() > k) pq.poll();
        }

        List<String> out = new ArrayList<>(k);
        while (!pq.isEmpty()) out.add(pq.poll().getKey());
        java.util.Collections.reverse(out);
        return out;
    }

    public static void main(String[] args) {
        System.out.println(topKFrequent(
            new String[]{"i", "love", "leetcode", "i", "love", "coding"}, 2));  // [i, love]
        System.out.println(topKFrequent(
            new String[]{"the","day","is","sunny","the","the","the","sunny","is","is"}, 4));
        // [the, is, sunny, day]
    }
}
