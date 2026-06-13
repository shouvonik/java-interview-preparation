package dsa.hashing.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LeetCode 49 — Group Anagrams
 * Difficulty: Medium   Tags: Array, Hash Table, String, Sorting
 * URL: https://leetcode.com/problems/group-anagrams/
 *
 * <h2>Problem</h2>
 * Given an array of strings {@code strs}, group the anagrams together.
 * You can return the answer in any order.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; strs.length &le; 10^4</li>
 *   <li>0 &le; strs[i].length &le; 100</li>
 *   <li>strs[i] consists of lowercase English letters.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   ["eat","tea","tan","ate","nat","bat"]  ->  [["bat"], ["nat","tan"], ["ate","eat","tea"]]
 *   [""]                                     ->  [[""]]
 *   ["a"]                                    ->  [["a"]]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Sorted-letter key (canonical)</b> — O(n k log k) where k is max string length.
 * Two anagrams produce the same multiset of letters; sorting the letters gives
 * a canonical key. Group by that key in a HashMap.
 * <p>
 * <b>Count-array key</b> — O(n k) time.
 * Use a 26-long int array as the count signature; convert to a key string like
 * "1#0#0#...#2#". Slightly faster asymptotically.
 *
 * <h2>Why sorting works as a key</h2>
 * Sorting destroys the input order of letters but preserves the multiset. Two
 * strings are anagrams iff they have the same multiset iff their sorted forms
 * are equal. So {@code sorted(s)} canonicalises anagrams.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty string — sorted form is also "" — groups by itself.</li>
 *   <li>Single character — sorted is the character itself.</li>
 *   <li>All distinct lengths — every group has size 1.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0049_GroupAnagrams
 * </pre>
 */
public class LC0049_GroupAnagrams {

    /** Sorted-letter key — O(n k log k). */
    public static List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }

    /** Count-array key — O(n k). */
    public static List<List<String>> groupAnagramsCount(String[] strs) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String s : strs) {
            int[] count = new int[26];
            for (char c : s.toCharArray()) count[c - 'a']++;
            StringBuilder key = new StringBuilder();
            for (int n : count) key.append(n).append('#');
            groups.computeIfAbsent(key.toString(), k -> new ArrayList<>()).add(s);
        }
        return new ArrayList<>(groups.values());
    }

    public static void main(String[] args) {
        System.out.println(groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
        // [[eat, tea, ate], [tan, nat], [bat]] in some order

        System.out.println(groupAnagrams(new String[]{""}));        // [[]]
        System.out.println(groupAnagrams(new String[]{"a"}));       // [[a]]
    }
}
