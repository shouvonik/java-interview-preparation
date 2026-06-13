package dsa.linkedlists.leetcode;

/**
 * LeetCode 876 — Middle of the Linked List
 * Difficulty: Easy   Tags: Linked List, Two Pointers
 * URL: https://leetcode.com/problems/middle-of-the-linked-list/
 *
 * <h2>Problem</h2>
 * Given the head of a singly linked list, return the middle node. If there are
 * two middle nodes (even length), return the SECOND middle node.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3,4,5]    -> 3 -> 4 -> 5
 *   [1,2,3,4,5,6]  -> 4 -> 5 -> 6  (second middle when even)
 * </pre>
 *
 * <h2>Approach — slow/fast pointers</h2>
 * {@code slow} steps once, {@code fast} steps twice per loop iteration. When
 * {@code fast} reaches the end (null or null.next), {@code slow} is at the
 * middle.
 *
 * <h2>"Second middle" detail</h2>
 * Using {@code while (fast != null && fast.next != null)} returns the second
 * middle for even length. Using {@code while (fast.next != null && fast.next.next != null)}
 * (without the initial nullcheck on fast) would return the first middle.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single node — middle is itself.</li>
 *   <li>Two nodes — second is the middle.</li>
 *   <li>Empty — return null.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0876_MiddleOfTheLinkedList
 * </pre>
 */
public class LC0876_MiddleOfTheLinkedList {

    /** Slow/fast — O(n) time, O(1) space. */
    public static ListNode middleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        System.out.println(ListNode.render(middleNode(ListNode.fromArray(1, 2, 3, 4, 5))));   // 3 -> 4 -> 5
        System.out.println(ListNode.render(middleNode(ListNode.fromArray(1, 2, 3, 4, 5, 6)))); // 4 -> 5 -> 6
        System.out.println(ListNode.render(middleNode(ListNode.fromArray(1))));                // 1
    }
}
