package dsa.linkedlists.leetcode;

/**
 * LeetCode 143 — Reorder List
 * Difficulty: Medium   Tags: Linked List, Two Pointers, Stack, Recursion
 * URL: https://leetcode.com/problems/reorder-list/
 *
 * <h2>Problem</h2>
 * You are given the head of a singly linked list. Reorder it so that:
 * <pre>L_0 -> L_n -> L_1 -> L_{n-1} -> L_2 -> L_{n-2} -> ...</pre>
 * Do it in place without altering node values.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3,4]    -> [1,4,2,3]
 *   [1,2,3,4,5]  -> [1,5,2,4,3]
 * </pre>
 *
 * <h2>Approach — three steps</h2>
 * <ol>
 *   <li>Find the middle (slow/fast).</li>
 *   <li>Reverse the second half from middle.next.</li>
 *   <li>Merge the two halves alternately.</li>
 * </ol>
 *
 * <h2>Why this combination</h2>
 * Each step is a well-known O(n) primitive. Composed, they give an in-place
 * O(n) reorder without extra storage.
 *
 * <h2>Splitting the middle</h2>
 * For odd n, the first half should be one longer than the second so the
 * alternation lands the final element in the middle. Use the standard slow/fast
 * pattern: when fast.next or fast.next.next is null, stop; slow is the middle.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>n &le; 2 — already reordered (single or pair).</li>
 *   <li>Even length — merge ends on a node from the (reversed) second half.</li>
 *   <li>Odd length — first half has one more, ending on its tail.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0143_ReorderList
 * </pre>
 */
public class LC0143_ReorderList {

    /** Find middle, reverse second half, merge — O(n) time, O(1) space. */
    public static void reorderList(ListNode head) {
        if (head == null || head.next == null) return;

        // 1) middle
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2) reverse second half from slow.next
        ListNode second = reverse(slow.next);
        slow.next = null;

        // 3) merge alternately
        ListNode first = head;
        while (second != null) {
            ListNode t1 = first.next, t2 = second.next;
            first.next = second;
            second.next = t1;
            first = t1;
            second = t2;
        }
    }

    private static ListNode reverse(ListNode head) {
        ListNode prev = null, cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }

    public static void main(String[] args) {
        ListNode a = ListNode.fromArray(1, 2, 3, 4);
        reorderList(a);
        System.out.println(ListNode.render(a));    // 1 -> 4 -> 2 -> 3

        ListNode b = ListNode.fromArray(1, 2, 3, 4, 5);
        reorderList(b);
        System.out.println(ListNode.render(b));    // 1 -> 5 -> 2 -> 4 -> 3

        ListNode c = ListNode.fromArray(1);
        reorderList(c);
        System.out.println(ListNode.render(c));    // 1
    }
}
