package dsa.linkedlists.leetcode;

/**
 * LeetCode 234 — Palindrome Linked List
 * Difficulty: Easy   Tags: Linked List, Two Pointers, Stack, Recursion
 * URL: https://leetcode.com/problems/palindrome-linked-list/
 *
 * <h2>Problem</h2>
 * Given the head of a singly linked list, return true if it is a palindrome
 * (reads the same front to back).
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,2,1]  -> true
 *   [1,2]      -> false
 *   [1]        -> true
 * </pre>
 *
 * <h2>Approach — find middle, reverse second half, compare</h2>
 * <ol>
 *   <li>Slow/fast to find the middle (slow ends at the start of the second half).</li>
 *   <li>Reverse from slow to the end.</li>
 *   <li>Walk both halves from the original head and the reversed tail; compare
 *       values until the second half runs out.</li>
 * </ol>
 * O(n) time, O(1) space.
 *
 * <h2>Why this approach</h2>
 * Copying values to an array and using two pointers is the obvious O(n) time,
 * O(n) space solution. The mid-reverse trick is the same time but O(1) space.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty or single node — palindrome by definition.</li>
 *   <li>Even length — first half = second half, no middle element.</li>
 *   <li>Odd length — middle element doesn't need to be compared (it's its own mirror).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0234_PalindromeLinkedList
 * </pre>
 */
public class LC0234_PalindromeLinkedList {

    /** Find middle, reverse second half, compare — O(n), O(1). */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 1) middle
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2) reverse second half (starting at slow)
        ListNode prev = null, curr = slow;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        // 3) walk both halves
        ListNode left = head, right = prev;
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPalindrome(ListNode.fromArray(1, 2, 2, 1)));    // true
        System.out.println(isPalindrome(ListNode.fromArray(1, 2)));          // false
        System.out.println(isPalindrome(ListNode.fromArray(1)));              // true
        System.out.println(isPalindrome(ListNode.fromArray(1, 2, 3, 2, 1)));  // true
    }
}
