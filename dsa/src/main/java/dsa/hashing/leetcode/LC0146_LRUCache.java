package dsa.hashing.leetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LeetCode 146 — LRU Cache
 * Difficulty: Medium   Tags: Hash Table, Linked List, Design
 * URL: https://leetcode.com/problems/lru-cache/
 *
 * <h2>Problem</h2>
 * Design a data structure that follows the constraints of a Least Recently
 * Used cache. Operations must be O(1):
 * <ul>
 *   <li>{@code get(key)} — return the value if the key exists, otherwise -1.</li>
 *   <li>{@code put(key, value)} — update the value if the key exists; otherwise
 *       insert. When the cache reaches its capacity, evict the least recently
 *       used key.</li>
 * </ul>
 * Both {@code get} and {@code put} count as "use" for recency.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; capacity &le; 3000</li>
 *   <li>0 &le; key &le; 10^4</li>
 *   <li>0 &le; value &le; 10^5</li>
 *   <li>At most 2 * 10^5 calls to get and put combined.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   LRUCache c = new LRUCache(2);
 *   c.put(1, 1);              //  cache = {1=1}
 *   c.put(2, 2);              //  cache = {1=1, 2=2}
 *   c.get(1);    // 1         //  cache = {2=2, 1=1}  (touched 1, moved to MRU)
 *   c.put(3, 3); // evicts 2  //  cache = {1=1, 3=3}
 *   c.get(2);    // -1
 *   c.put(4, 4); // evicts 1  //  cache = {3=3, 4=4}
 *   c.get(1);    // -1
 *   c.get(3);    // 3
 *   c.get(4);    // 4
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>LinkedHashMap shortcut</b> — minimal code.
 * Java's {@link LinkedHashMap} can be configured in <i>access order</i> mode
 * by passing {@code accessOrder=true} to its constructor. Override
 * {@code removeEldestEntry} to evict on overflow. O(1) for all ops.
 * <p>
 * <b>HashMap + doubly-linked list (canonical)</b> — interviewer favourite.
 * Two structures:
 * <ul>
 *   <li>{@code HashMap<Integer, Node>} for O(1) lookups.</li>
 *   <li>Doubly-linked list with head (MRU) and tail (LRU) sentinels for O(1)
 *       moves.</li>
 * </ul>
 * On {@code get}: lookup, unlink, push to head. On {@code put}: same pattern,
 * adding eviction at tail when full.
 *
 * <h2>Why doubly-linked, not singly?</h2>
 * To remove an arbitrary node (during {@code get}) in O(1) without scanning,
 * you need both prev and next pointers. Singly-linked forces O(n) search.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0146_LRUCache
 * </pre>
 */
public class LC0146_LRUCache {

    // ====================================================================
    // Canonical implementation: HashMap + doubly-linked list
    // ====================================================================
    public static class LRUCache {
        private static final class Node {
            int key, value;
            Node prev, next;
            Node(int key, int value) { this.key = key; this.value = value; }
        }

        private final int capacity;
        private final Map<Integer, Node> index = new HashMap<>();
        private final Node head = new Node(0, 0);                  // MRU side (head.next = MRU)
        private final Node tail = new Node(0, 0);                  // LRU side (tail.prev = LRU)

        public LRUCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            Node node = index.get(key);
            if (node == null) return -1;
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            Node node = index.get(key);
            if (node != null) {
                node.value = value;
                moveToHead(node);
                return;
            }
            if (index.size() == capacity) {
                Node evict = tail.prev;
                unlink(evict);
                index.remove(evict.key);
            }
            Node fresh = new Node(key, value);
            index.put(key, fresh);
            linkAtHead(fresh);
        }

        private void unlink(Node n) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }

        private void linkAtHead(Node n) {
            n.next = head.next;
            n.prev = head;
            head.next.prev = n;
            head.next = n;
        }

        private void moveToHead(Node n) {
            unlink(n);
            linkAtHead(n);
        }
    }

    // ====================================================================
    // Shortcut: LinkedHashMap in access-order mode
    // ====================================================================
    public static class LRUCacheShortcut extends LinkedHashMap<Integer, Integer> {
        private final int capacity;
        public LRUCacheShortcut(int capacity) {
            super(capacity, 0.75f, /*accessOrder=*/true);
            this.capacity = capacity;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
        public int get(int key) {
            Integer v = super.get(key);
            return v == null ? -1 : v;
        }
        public void put2(int key, int value) {
            super.put(key, value);
        }
    }

    public static void main(String[] args) {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        System.out.println(c.get(1));   // 1
        c.put(3, 3);                     // evicts 2
        System.out.println(c.get(2));   // -1
        c.put(4, 4);                     // evicts 1
        System.out.println(c.get(1));   // -1
        System.out.println(c.get(3));   // 3
        System.out.println(c.get(4));   // 4

        // Shortcut form
        LRUCacheShortcut s = new LRUCacheShortcut(2);
        s.put2(1, 1); s.put2(2, 2);
        System.out.println(s.get(1));   // 1
        s.put2(3, 3);
        System.out.println(s.get(2));   // -1
    }
}
