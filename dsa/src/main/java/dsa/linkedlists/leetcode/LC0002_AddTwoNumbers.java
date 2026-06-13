package dsa.linkedlists.leetcode;

/**
 * LeetCode 2 — Add Two Numbers
 * Difficulty: Medium   Tags: Linked List, Math, Recursion
 * URL: https://leetcode.com/problems/add-two-numbers/
 *
 * <h2>Problem</h2>
 * You are given two non-empty linked lists representing two non-negative
 * integers. The digits are stored in <b>reverse order</b>, and each node
 * contains a single digit. Add the two numbers and return the sum as a linked
 * list (also in reverse order). No leading zeros except the number 0 itself.
 *
 * <h2>Examples</h2>
 * <pre>
 *   l1 = 2->4->3 (342), l2 = 5->6->4 (465)  ->  7->0->8 (807)
 *   l1 = 0,        l2 = 0                    ->  0
 *   l1 = 9->9->9->9->9->9->9, l2 = 9->9->9->9  ->  8->9->9->9->0->0->0->1
 * </pre>
 *
 * <h2>Approach — single pass with a dummy head and carry</h2>
 * Walk both lists in lockstep, summing digits + carry. Use a dummy head so
 * appending to the result list is uniform regardless of position. Continue
 * while either list has digits left OR carry is non-zero.
 *
 * <h2>Why reversed digit order is helpful</h2>
 * Reversed lists give the least-significant digit first, matching the
 * direction addition naturally propagates (right-to-left carry).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Different lengths — keep advancing the longer one with the other side's
 *       digit treated as 0.</li>
 *   <li>Final carry — don't forget to append a node if carry is 1 at the end.</li>
 *   <li>Both lists are 0 — result is a single 0 node.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0002_AddTwoNumbers
 * </pre>
 */
public class LC0002_AddTwoNumbers {

    /** Dummy head + carry — O(max(m, n)). */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int sum = carry
                + (l1 == null ? 0 : l1.val)
                + (l2 == null ? 0 : l2.val);
            carry = sum / 10;
            cur.next = new ListNode(sum % 10);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        // 342 + 465 = 807
        ListNode a = ListNode.fromArray(2, 4, 3);
        ListNode b = ListNode.fromArray(5, 6, 4);
        System.out.println(ListNode.render(addTwoNumbers(a, b)));   // 7 -> 0 -> 8

        // 0 + 0 = 0
        System.out.println(ListNode.render(addTwoNumbers(ListNode.fromArray(0), ListNode.fromArray(0)))); // 0

        // 9999999 + 9999 = 10009998
        ListNode big1 = ListNode.fromArray(9, 9, 9, 9, 9, 9, 9);
        ListNode big2 = ListNode.fromArray(9, 9, 9, 9);
        System.out.println(ListNode.render(addTwoNumbers(big1, big2))); // 8 -> 9 -> 9 -> 9 -> 0 -> 0 -> 0 -> 1
    }
}
