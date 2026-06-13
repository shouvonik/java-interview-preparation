package dsa.linkedlists.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 138 — Copy List with Random Pointer
 * Difficulty: Medium   Tags: Hash Table, Linked List
 * URL: https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 * <h2>Problem</h2>
 * A linked list of length n where each node has {@code val}, {@code next}, and
 * {@code random}, which can point to any node in the list (or null). Construct
 * a deep copy of the list.
 *
 * <h2>Approach 1 — hash map: original -> clone</h2>
 * Pass 1: walk original; create a clone for each node and store the mapping.
 * Pass 2: walk again; set each clone's {@code next} and {@code random} via the
 * map. O(n) time, O(n) space.
 *
 * <h2>Approach 2 — interleave clones (O(1) extra)</h2>
 * Insert each clone right after its original ("A -> A' -> B -> B' -> ..."),
 * wire each clone's random by {@code orig.random.next} (which points to that
 * random's clone), then split the two interleaved lists. Trickier but uses
 * O(1) extra space.
 *
 * <h2>Why HashMap is the clearest answer</h2>
 * For interviews, prefer the HashMap version unless explicitly asked for O(1)
 * extra. It separates "create" and "wire" into clean passes.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty list — return null.</li>
 *   <li>random == null on some nodes — map.get(null) yields null naturally.</li>
 *   <li>random pointing to self — handled by the map.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0138_CopyListWithRandomPointer
 * </pre>
 */
public class LC0138_CopyListWithRandomPointer {

    public static class Node {
        public int val;
        public Node next;
        public Node random;
        public Node(int val) { this.val = val; }
    }

    /** HashMap-based deep copy — O(n) time, O(n) space. */
    public static Node copyRandomList(Node head) {
        if (head == null) return null;
        Map<Node, Node> map = new HashMap<>();
        for (Node cur = head; cur != null; cur = cur.next) {
            map.put(cur, new Node(cur.val));
        }
        for (Node cur = head; cur != null; cur = cur.next) {
            Node clone = map.get(cur);
            clone.next = map.get(cur.next);
            clone.random = map.get(cur.random);
        }
        return map.get(head);
    }

    /** Render as "val(random=X) -> ..." for verification. */
    private static String render(Node head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val).append("(r=")
              .append(head.random == null ? "null" : head.random.val).append(")");
            if (head.next != null) sb.append(" -> ");
            head = head.next;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // 7 -> 13(r=7) -> 11(r=null) -> 10(r=11) -> 1(r=7)
        Node a = new Node(7), b = new Node(13), c = new Node(11), d = new Node(10), e = new Node(1);
        a.next = b; b.next = c; c.next = d; d.next = e;
        a.random = null;
        b.random = a;
        c.random = e;
        d.random = c;
        e.random = a;

        Node clone = copyRandomList(a);
        System.out.println(render(a));        // original
        System.out.println(render(clone));    // deep copy

        System.out.println(copyRandomList(null));  // null
    }
}
