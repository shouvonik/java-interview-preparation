package dsa.linkedlists.leetcode;

/**
 * LeetCode 25 — Reverse Nodes in k-Group
 * Difficulty: Hard   Tags: Linked List, Recursion
 * URL: https://leetcode.com/problems/reverse-nodes-in-k-group/
 *
 * <h2>Problem</h2>
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list. k is positive and at most the length. If the
 * number of nodes is not a multiple of k, the trailing leftover nodes remain
 * in order. You may not change the values, only the node connections.
 *
 * <h2>Examples</h2>
 * <pre>
 *   head = [1,2,3,4,5], k = 2  -> [2,1,4,3,5]
 *   head = [1,2,3,4,5], k = 3  -> [3,2,1,4,5]
 *   head = [1],         k = 1  -> [1]
 * </pre>
 *
 * <h2>Approach — iterative, group by group</h2>
 * <ol>
 *   <li>Use a dummy node. Pointer {@code groupPrev} marks the node before the
 *       current group.</li>
 *   <li>Walk k nodes from {@code groupPrev.next} to find {@code groupEnd}; if
 *       there are fewer than k, stop — leave the tail as-is.</li>
 *   <li>Reverse the segment {@code [groupPrev.next .. groupEnd]} in place;
 *       reconnect properly.</li>
 *   <li>Advance {@code groupPrev} to the (new) end of the reversed group.</li>
 * </ol>
 *
 * <h2>The reverse helper</h2>
 * Standard in-place reverse with three pointers: prev, curr, next. Stop after
 * exactly k nodes.
 *
 * <h2>Why it's harder than it looks</h2>
 * The "stitch the reversed segment back" step has to maintain links to the
 * BEFORE group and the AFTER group. Using {@code groupPrev} and saving
 * {@code groupEnd.next} before reversing is the clean way.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == 1 — list unchanged.</li>
 *   <li>k == length — fully reversed (one group).</li>
 *   <li>length % k != 0 — trailing nodes stay as-is.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0025_ReverseNodesInKGroup
 * </pre>
 */
public class LC0025_ReverseNodesInKGroup {

    /** Iterative, group by group — O(n). */
    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;
        while (true) {
            ListNode kth = nodeAt(groupPrev, k);
            if (kth == null) break;
            ListNode groupNext = kth.next;
            // reverse [groupPrev.next .. kth]
            ListNode prev = groupNext, curr = groupPrev.next;
            while (curr != groupNext) {
                ListNode next = curr.next;
                curr.next = prev;
                prev = curr;
                curr = next;
            }
            ListNode tmp = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = tmp;
        }
        return dummy.next;
    }

    /** Returns the kth node after {@code from}, or null if fewer than k exist. */
    private static ListNode nodeAt(ListNode from, int k) {
        ListNode cur = from;
        while (k > 0 && cur != null) { cur = cur.next; k--; }
        return cur;
    }

    public static void main(String[] args) {
        System.out.println(ListNode.render(reverseKGroup(ListNode.fromArray(1, 2, 3, 4, 5), 2))); // 2 -> 1 -> 4 -> 3 -> 5
        System.out.println(ListNode.render(reverseKGroup(ListNode.fromArray(1, 2, 3, 4, 5), 3))); // 3 -> 2 -> 1 -> 4 -> 5
        System.out.println(ListNode.render(reverseKGroup(ListNode.fromArray(1), 1)));             // 1
        System.out.println(ListNode.render(reverseKGroup(ListNode.fromArray(1, 2, 3, 4, 5), 5))); // 5 -> 4 -> 3 -> 2 -> 1
    }
}
