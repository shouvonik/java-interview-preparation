package dsa.twopointers.leetcode;

/**
 * LeetCode 142 — Linked List Cycle II
 * Difficulty: Medium   Tags: Hash Table, Linked List, Two Pointers
 * URL: https://leetcode.com/problems/linked-list-cycle-ii/
 *
 * <h2>Problem</h2>
 * Given the head of a linked list, return the node where the cycle begins.
 * If there is no cycle, return null.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; n &le; 10^4 nodes</li>
 *   <li>-10^5 &le; Node.val &le; 10^5</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3,2,0,-4] with pos = 1  -> node with value 2
 *   [1,2] with pos = 0        -> node with value 1
 *   [1] no cycle              -> null
 * </pre>
 *
 * <h2>Approach — Floyd's tortoise and hare, then reset</h2>
 * <ol>
 *   <li>Detect the cycle using slow/fast as in LC 141.</li>
 *   <li>When they meet, reset {@code slow} to head; advance both pointers
 *       one step at a time. They meet at the cycle's entry point.</li>
 * </ol>
 *
 * <h2>The arithmetic</h2>
 * Let:
 * <ul>
 *   <li>{@code a} = distance from head to cycle entry.</li>
 *   <li>{@code b} = distance from cycle entry to the meeting point.</li>
 *   <li>{@code c} = remaining cycle distance back to entry.</li>
 * </ul>
 * Slow has walked {@code a + b}. Fast has walked {@code 2(a + b)}. They meet
 * inside the cycle, so fast's distance equals slow's plus some multiple of the
 * cycle length L = b + c. Solving:
 * <p>
 * {@code 2(a + b) = (a + b) + k * L  =>  a + b = k * L  =>  a = k * L - b = (k - 1) * L + c}
 * <p>
 * So if we walk {@code a} from head AND simultaneously walk {@code a} from the
 * meeting point — going through some number of full loops plus the remaining
 * {@code c} — we land on the cycle entry. Both pointers at speed 1 meet there.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Cycle entry is the head — algorithm correctly returns the head.</li>
 *   <li>No cycle — return null (fast hits the end first).</li>
 *   <li>Single node pointing to itself — both meet at the node immediately;
 *       reset gives back the head.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0142_LinkedListCycleII
 * </pre>
 */
public class LC0142_LinkedListCycleII {

    public static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /** Floyd's + reset — O(n) time, O(1) space. */
    public static ListNode detectCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                ListNode ptr = head;
                while (ptr != slow) { ptr = ptr.next; slow = slow.next; }
                return ptr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // [3,2,0,-4] with pos = 1 -> entry is node with val 2
        ListNode n1 = new ListNode(3), n2 = new ListNode(2), n3 = new ListNode(0), n4 = new ListNode(-4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        ListNode entry = detectCycle(n1);
        System.out.println(entry == null ? "null" : entry.val);     // 2

        // No cycle
        ListNode m1 = new ListNode(1), m2 = new ListNode(2);
        m1.next = m2;
        System.out.println(detectCycle(m1));                          // null

        // Self-cycle
        ListNode s = new ListNode(7);
        s.next = s;
        System.out.println(detectCycle(s).val);                       // 7
    }
}
