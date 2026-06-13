package dsa.hashing.leetcode;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * LeetCode 460 — LFU Cache
 * Difficulty: Hard   Tags: Hash Table, Linked List, Design, Doubly-Linked List
 * URL: https://leetcode.com/problems/lfu-cache/
 *
 * <h2>Problem</h2>
 * Design and implement a Least Frequently Used cache supporting O(1):
 * <ul>
 *   <li>{@code get(key)} — return value or -1.</li>
 *   <li>{@code put(key, value)} — insert/update. When capacity is reached,
 *       evict the LFU key. If multiple keys tie on frequency, evict the LRU
 *       among them.</li>
 * </ul>
 * Both {@code get} and {@code put} count as a use (incrementing frequency).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; capacity &le; 10^4</li>
 *   <li>0 &le; key &le; 10^5</li>
 *   <li>0 &le; value &le; 10^9</li>
 *   <li>At most 2 * 10^5 calls to get and put combined.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   LFUCache c = new LFUCache(2);
 *   c.put(1, 1);   // {1=1, freq{1}}
 *   c.put(2, 2);   // {1=1, 2=2, freq{1}}
 *   c.get(1);      // 1; freq(1) becomes 2 -> {2=2 freq1, 1=1 freq2}
 *   c.put(3, 3);   // full; evict LFU (2). -> {1=1 freq2, 3=3 freq1}
 *   c.get(2);      // -1
 *   c.get(3);      // 3; freq(3) becomes 2
 *   c.put(4, 4);   // full; both at freq 2 -> tie -> evict LRU among them (1)
 *   c.get(1);      // -1
 * </pre>
 *
 * <h2>Approach — two maps plus minFreq</h2>
 * <ul>
 *   <li>{@code Map<Integer, Node> keyToNode} for O(1) lookup. Each Node stores
 *       {key, value, freq}.</li>
 *   <li>{@code Map<Integer, LinkedHashSet<Integer>> freqToKeys} groups keys
 *       by frequency. LinkedHashSet preserves insertion order, giving O(1)
 *       LRU-within-tie eviction.</li>
 *   <li>{@code int minFreq} tracks the current minimum frequency present.</li>
 * </ul>
 *
 * <h2>Operations</h2>
 * <b>get(key):</b>
 * <ol>
 *   <li>Lookup node; return -1 if missing.</li>
 *   <li>Remove key from {@code freqToKeys[node.freq]}; if empty AND it was
 *       minFreq, increment minFreq.</li>
 *   <li>Increment node.freq; add to {@code freqToKeys[node.freq]} (creating the
 *       bucket if absent).</li>
 *   <li>Return node.value.</li>
 * </ol>
 *
 * <b>put(key, value):</b>
 * <ol>
 *   <li>If key exists: update value, then trigger the same recency bump as
 *       {@code get}.</li>
 *   <li>Else if cache full: evict the first key from {@code freqToKeys[minFreq]}
 *       (the LRU among the LFUs).</li>
 *   <li>Insert new node with freq = 1; set {@code minFreq = 1}.</li>
 * </ol>
 *
 * <h2>Why minFreq doesn't need to scan</h2>
 * Frequencies only ever go up (by 1) or get inserted at 1. So minFreq is set
 * to 1 on every new insert, and incremented only when the current minFreq
 * bucket becomes empty during a get/promote.
 *
 * <h2>Capacity 0 edge case</h2>
 * If capacity is 0, every {@code put} is effectively a no-op and every {@code get}
 * returns -1. Guard at the start of both methods.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0460_LFUCache
 * </pre>
 */
public class LC0460_LFUCache {

    public static class LFUCache {
        private static final class Node {
            int key, value, freq;
            Node(int k, int v) { key = k; value = v; freq = 1; }
        }

        private final int capacity;
        private final Map<Integer, Node> keyToNode = new HashMap<>();
        private final Map<Integer, LinkedHashSet<Integer>> freqToKeys = new HashMap<>();
        private int minFreq = 0;

        public LFUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (capacity == 0) return -1;
            Node node = keyToNode.get(key);
            if (node == null) return -1;
            bumpFreq(node);
            return node.value;
        }

        public void put(int key, int value) {
            if (capacity == 0) return;
            Node node = keyToNode.get(key);
            if (node != null) {
                node.value = value;
                bumpFreq(node);
                return;
            }
            if (keyToNode.size() == capacity) evictLfu();

            Node fresh = new Node(key, value);
            keyToNode.put(key, fresh);
            freqToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFreq = 1;
        }

        private void bumpFreq(Node node) {
            LinkedHashSet<Integer> oldBucket = freqToKeys.get(node.freq);
            oldBucket.remove(node.key);
            if (oldBucket.isEmpty()) {
                freqToKeys.remove(node.freq);
                if (node.freq == minFreq) minFreq++;
            }
            node.freq++;
            freqToKeys.computeIfAbsent(node.freq, k -> new LinkedHashSet<>()).add(node.key);
        }

        private void evictLfu() {
            LinkedHashSet<Integer> bucket = freqToKeys.get(minFreq);
            int evictKey = bucket.iterator().next();    // LRU within the LFU bucket
            bucket.remove(evictKey);
            if (bucket.isEmpty()) freqToKeys.remove(minFreq);
            keyToNode.remove(evictKey);
        }
    }

    public static void main(String[] args) {
        LFUCache c = new LFUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        System.out.println(c.get(1));   // 1
        c.put(3, 3);                     // evicts 2 (LFU)
        System.out.println(c.get(2));   // -1
        System.out.println(c.get(3));   // 3
        c.put(4, 4);                     // tie at freq 2 -> evict 1 (LRU among LFUs)
        System.out.println(c.get(1));   // -1
        System.out.println(c.get(3));   // 3
        System.out.println(c.get(4));   // 4
    }
}
