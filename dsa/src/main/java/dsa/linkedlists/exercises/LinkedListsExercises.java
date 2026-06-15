package dsa.linkedlists.exercises;

/**
 * Linked Lists — exercises.
 */
public class LinkedListsExercises {

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) { this.val = val; }
    }

    /**
     * Exercise 1 — Add Two Numbers (LeetCode 2).
     * Two non-empty lists representing non-negative ints with digits in reverse order.
     * Return their sum as a list (also reversed).
     * Example: 342 + 465 -> 807, so [2,4,3] + [5,6,4] -> [7,0,8].
     */
    static Node addTwoNumbers(Node l1, Node l2) {
        // TODO
        return null;
    }

    /**
     * Exercise 2 — Remove Nth Node From End of List (LeetCode 19).
     * Use a dummy head and a two-pointer head start.
     */
    static Node removeNthFromEnd(Node head, int n) {
        // TODO
        return head;
    }

    /**
     * Exercise 3 — Palindrome Linked List (LeetCode 234).
     * Hint: find middle, reverse second half, walk both.
     */
    static boolean isPalindrome(Node head) {
        // TODO
        return false;
    }

    /**
     * Exercise 4 — Linked List Cycle II (LeetCode 142).
     * Return the node where the cycle begins, or null.
     */
    static Node detectCycle(Node head) {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        // Wire in your own test cases — see solutions/LinkedListsSolutions for examples.
        System.out.println("addTwoNumbers / removeNthFromEnd / isPalindrome / detectCycle — TODO");
    }
}
