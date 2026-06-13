package dsa.graphs.leetcode;

import java.util.*;

/**
 * LeetCode 269 — Alien Dictionary
 * Difficulty: Hard   Tags: Array, String, DFS, BFS, Graph, Topological Sort
 * URL: https://leetcode.com/problems/alien-dictionary/  (premium)
 *
 * <h2>Problem</h2>
 * You're given a list of words from an alien language, sorted lexicographically
 * by the rules of THAT language. Return any letter ordering consistent with
 * the list. If there is no valid ordering (a contradiction), return "". If
 * multiple are valid, return any.
 *
 * <h2>Examples</h2>
 * <pre>
 *   ["wrt","wrf","er","ett","rftt"]  -> "wertf"
 *   ["z", "x"]                        -> "zx"
 *   ["z", "x", "z"]                   -> ""    (cycle: z &lt; x &lt; z)
 *   ["abc", "ab"]                     -> ""    (prefix violation)
 * </pre>
 *
 * <h2>Approach — extract pairwise constraints, then topo sort</h2>
 * <ol>
 *   <li>Collect the set of letters used. Each becomes a graph node.</li>
 *   <li>For each adjacent pair of words, find the first differing letter; that
 *       differ tells us a single directed edge in the letter order. If one is
 *       a proper prefix of the other AND the first is LONGER, it's an
 *       impossible ordering (return "").</li>
 *   <li>Run Kahn's. If the result has fewer than (#letters), a cycle exists —
 *       return "".</li>
 * </ol>
 *
 * <h2>Subtleties</h2>
 * <ul>
 *   <li>Only the FIRST differing letter gives a constraint. Subsequent letters
 *       are unconstrained by this word pair.</li>
 *   <li>Duplicate edges should be de-duplicated to avoid inflating indegrees.</li>
 *   <li>Letters that appear in no pair are still part of the alphabet — emit
 *       them in any order.</li>
 * </ul>
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single word — every letter is unconstrained; any permutation is valid.</li>
 *   <li>All distinct letters across words but no constraints — return them in any order.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.graphs.leetcode.LC0269_AlienDictionary
 * </pre>
 */
public class LC0269_AlienDictionary {

    /** Constraint extraction + Kahn's topological sort. */
    public static String alienOrder(String[] words) {
        Map<Character, Set<Character>> adj = new HashMap<>();
        Map<Character, Integer> indeg = new HashMap<>();
        for (String w : words) {
            for (char c : w.toCharArray()) {
                adj.putIfAbsent(c, new HashSet<>());
                indeg.putIfAbsent(c, 0);
            }
        }

        for (int i = 0; i + 1 < words.length; i++) {
            String a = words[i], b = words[i + 1];
            int len = Math.min(a.length(), b.length());
            int j = 0;
            while (j < len && a.charAt(j) == b.charAt(j)) j++;
            if (j == len) {
                // a is a prefix of b — fine. b prefix of a (and a longer) — invalid.
                if (a.length() > b.length()) return "";
                continue;
            }
            char from = a.charAt(j), to = b.charAt(j);
            if (adj.get(from).add(to)) indeg.merge(to, 1, Integer::sum);
        }

        Deque<Character> q = new ArrayDeque<>();
        for (var e : indeg.entrySet()) if (e.getValue() == 0) q.offer(e.getKey());

        StringBuilder out = new StringBuilder();
        while (!q.isEmpty()) {
            char u = q.poll();
            out.append(u);
            for (char v : adj.get(u)) {
                if (indeg.merge(v, -1, Integer::sum) == 0) q.offer(v);
            }
        }
        return out.length() == indeg.size() ? out.toString() : "";
    }

    public static void main(String[] args) {
        System.out.println(alienOrder(new String[]{"wrt", "wrf", "er", "ett", "rftt"}));    // wertf
        System.out.println(alienOrder(new String[]{"z", "x"}));                              // zx
        System.out.println(alienOrder(new String[]{"z", "x", "z"}));                         // "" (cycle)
        System.out.println(alienOrder(new String[]{"abc", "ab"}));                           // "" (prefix violation)
    }
}
