package dsa.twopointers.leetcode;

/**
 * LeetCode 141 — Linked List Cycle
 * Difficulty: Easy   Tags: Hash Table, Linked List, Two Pointers
 * URL: https://leetcode.com/problems/linked-list-cycle/
 *
 * <h2>Problem</h2>
 * Given the head of a linked list, determine if the list has a cycle in it.
 * There is a cycle in a linked list if there is some node in the list that can
 * be reached again by continuously following the next pointer.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; n &le; 10^4 nodes</li>
 *   <li>-10^5 &le; Node.val &le; 10^5</li>
 *   <li>pos is -1 (no cycle) or a valid node index.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   Tail of [3,2,0,-4] connected to index 1  -> true
 *   Tail of [1,2] connected to index 0       -> true
 *   [1] with no cycle                         -> false
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>HashSet of visited nodes</b> — O(n) time, O(n) space.
 * Traverse; if we revisit a node, cycle exists.
 * <p>
 * <b>Floyd's cycle detection (canonical)</b> — O(n) time, O(1) space.
 * Two pointers: slow moves one step, fast moves two. If there's a cycle, they
 * will eventually land on the same node (think two runners on a circular
 * track). If fast hits null, no cycle.
 *
 * <h2>Why fast/slow always meets in a cycle</h2>
 * Once both pointers are inside the cycle (the slow one will enter eventually),
 * the gap closes by 1 every step (fast gains 1). The gap is bounded by the
 * cycle length, so within cycle-length steps the gap becomes 0 — they meet.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty list — return false.</li>
 *   <li>Single node, no cycle — return false (fast.next is null).</li>
 *   <li>Single node that points to itself — return true after one step.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0141_LinkedListCycle
 * </pre>
 */
public class LC0141_LinkedListCycle {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /** Floyd's cycle detection — O(n) time, O(1) space. */
    public static boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        // [3,2,0,-4] with tail -> node at index 1 (value 2)
        ListNode n1 = new ListNode(3), n2 = new ListNode(2), n3 = new ListNode(0), n4 = new ListNode(-4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        System.out.println(hasCycle(n1));      // true

        // [1] no cycle
        ListNode solo = new ListNode(1);
        System.out.println(hasCycle(solo));    // false

        // Empty
        System.out.println(hasCycle(null));    // false
    }
}
