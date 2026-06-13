package dsa.graphs.leetcode;

import java.util.*;

/**
 * LeetCode 127 — Word Ladder
 * Difficulty: Hard   Tags: Hash Table, String, BFS
 * URL: https://leetcode.com/problems/word-ladder/
 *
 * <h2>Problem</h2>
 * A transformation sequence from word {@code beginWord} to word {@code endWord}
 * using {@code wordList} is a sequence of words {@code beginWord -> s_1 -> ... -> s_k}
 * such that:
 * <ul>
 *   <li>Every adjacent pair of words differs by a single letter.</li>
 *   <li>Every {@code s_i} (1 &le; i &le; k) is in {@code wordList} ({@code beginWord}
 *       does NOT need to be).</li>
 *   <li>{@code s_k == endWord}.</li>
 * </ul>
 * Return the shortest such sequence's length (number of words including
 * beginWord), or 0 if no such sequence exists.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; beginWord.length &le; 10</li>
 *   <li>1 &le; wordList.length &le; 5000</li>
 *   <li>All words have the same length and consist of lowercase English letters.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   begin="hit", end="cog", list=["hot","dot","dog","lot","log","cog"]  -> 5
 *     hit -> hot -> dot -> dog -> cog
 *   begin="hit", end="cog", list=["hot","dot","dog","lot","log"]        -> 0
 * </pre>
 *
 * <h2>Approach — BFS on an implicit word graph</h2>
 * Two words are neighbours if they differ by exactly one letter. BFS from
 * beginWord; the first time we dequeue endWord, the BFS depth is the answer.
 *
 * <h2>Speedup: wildcard buckets</h2>
 * For each word of length L, generate L wildcard patterns: "h*t", "*ot", etc.
 * Any two words sharing a pattern are neighbours. Pre-bucket the word list by
 * pattern; each BFS expansion uses O(L) pattern lookups instead of O(N * L)
 * pairwise comparisons.
 *
 * <h2>Bidirectional BFS — further speedup</h2>
 * Expand from both ends simultaneously; meet in the middle. Cuts the worst case
 * from O(b^d) to O(b^(d/2)) where b is the branching factor. Implementation is
 * fiddlier; this file uses single-direction BFS for clarity.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>endWord not in wordList — return 0.</li>
 *   <li>beginWord == endWord — problem statement implies &gt;= 2 distinct;
 *       defensively return 1 if equal.</li>
 *   <li>Single-letter words — same logic; pattern is just "*".</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0127_WordLadder
 * </pre>
 */
public class LC0127_WordLadder {

    /** Wildcard-bucket BFS — O(N * L^2). */
    public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> dict = new HashSet<>(wordList);
        if (!dict.contains(endWord)) return 0;

        Map<String, List<String>> patterns = new HashMap<>();
        for (String w : dict) {
            for (int i = 0; i < w.length(); i++) {
                String pat = w.substring(0, i) + "*" + w.substring(i + 1);
                patterns.computeIfAbsent(pat, k -> new ArrayList<>()).add(w);
            }
        }

        Deque<String> q = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        q.offer(beginWord);
        visited.add(beginWord);
        int steps = 1;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                String w = q.poll();
                if (w.equals(endWord)) return steps;
                for (int j = 0; j < w.length(); j++) {
                    String pat = w.substring(0, j) + "*" + w.substring(j + 1);
                    for (String next : patterns.getOrDefault(pat, List.of())) {
                        if (visited.add(next)) q.offer(next);
                    }
                }
            }
            steps++;
        }
        return 0;
    }

    public static void main(String[] args) {
        System.out.println(ladderLength("hit", "cog",
            List.of("hot", "dot", "dog", "lot", "log", "cog")));   // 5
        System.out.println(ladderLength("hit", "cog",
            List.of("hot", "dot", "dog", "lot", "log")));           // 0
        System.out.println(ladderLength("a", "c", List.of("a", "b", "c")));   // 2
    }
}
