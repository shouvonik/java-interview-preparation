package dsa.linkedlists.leetcode;

/**
 * LeetCode 206 — Reverse Linked List
 * Difficulty: Easy   Tags: Linked List, Recursion
 * URL: https://leetcode.com/problems/reverse-linked-list/
 *
 * <h2>Problem</h2>
 * Given the head of a singly linked list, reverse the list and return its head.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3,4,5]  -> [5,4,3,2,1]
 *   [1,2]         -> [2,1]
 *   []            -> []
 * </pre>
 *
 * <h2>Approach 1 — iterative with three pointers (canonical)</h2>
 * {@code prev = null}, {@code curr = head}. Repeat: save {@code next}, flip
 * {@code curr.next = prev}, shift {@code prev = curr, curr = next}. Return prev.
 * O(n) time, O(1) space.
 *
 * <h2>Approach 2 — recursive</h2>
 * Recurse to the end; on the way back, set {@code curr.next.next = curr;
 * curr.next = null}. O(n) time, O(n) stack space.
 *
 * <h2>Why the iterative version is preferred</h2>
 * Linear stack depth is fine for n &le; 5000 but feels brittle vs. a clean
 * O(1) iterative solution.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0206_ReverseLinkedList
 * </pre>
 */
public class LC0206_ReverseLinkedList {

    /** Iterative — O(n) time, O(1) space. */
    public static ListNode reverseList(ListNode head) {
        ListNode prev = null, curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /** Recursive — O(n) time, O(n) stack. */
    public static ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseListRecursive(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        System.out.println(ListNode.render(reverseList(ListNode.fromArray(1, 2, 3, 4, 5))));  // 5 -> 4 -> 3 -> 2 -> 1
        System.out.println(ListNode.render(reverseList(ListNode.fromArray(1, 2))));            // 2 -> 1
        System.out.println(ListNode.render(reverseList(null)));                                 // (empty)
        System.out.println(ListNode.render(reverseListRecursive(ListNode.fromArray(1, 2, 3)))); // 3 -> 2 -> 1
    }
}
