package dsa.linkedlists.leetcode;

/**
 * LeetCode 21 — Merge Two Sorted Lists
 * Difficulty: Easy   Tags: Linked List, Recursion
 * URL: https://leetcode.com/problems/merge-two-sorted-lists/
 *
 * <h2>Problem</h2>
 * You are given the heads of two sorted linked lists. Merge them in-place
 * into a single sorted list by splicing nodes; return the head.
 *
 * <h2>Examples</h2>
 * <pre>
 *   l1 = [1,2,4], l2 = [1,3,4]  -> [1,1,2,3,4,4]
 *   l1 = [],      l2 = []       -> []
 *   l1 = [],      l2 = [0]      -> [0]
 * </pre>
 *
 * <h2>Approach — dummy head + walk both</h2>
 * Iterative: at each step, append whichever head is smaller; advance that
 * pointer. When one runs out, attach the rest of the other.
 *
 * <h2>Why no new nodes</h2>
 * Splice existing nodes by reassigning {@code next} pointers. Saves memory
 * and is the LeetCode-expected approach.
 *
 * <h2>Recursive variant</h2>
 * Pick the smaller head, recursively merge its tail with the other; O(m + n)
 * but recursion depth is O(m + n) — could stack-overflow on large inputs.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0021_MergeTwoSortedLists
 * </pre>
 */
public class LC0021_MergeTwoSortedLists {

    /** Iterative splice — O(m + n). */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) { cur.next = l1; l1 = l1.next; }
            else                    { cur.next = l2; l2 = l2.next; }
            cur = cur.next;
        }
        cur.next = l1 != null ? l1 : l2;
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println(ListNode.render(mergeTwoLists(ListNode.fromArray(1, 2, 4), ListNode.fromArray(1, 3, 4))));
        // 1 -> 1 -> 2 -> 3 -> 4 -> 4
        System.out.println(ListNode.render(mergeTwoLists(null, null)));                          // (empty)
        System.out.println(ListNode.render(mergeTwoLists(null, ListNode.fromArray(0))));         // 0
    }
}
