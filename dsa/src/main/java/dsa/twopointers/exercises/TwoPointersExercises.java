package dsa.twopointers.exercises;

/**
 * Two Pointers — exercises.
 */
public class TwoPointersExercises {

    /**
     * Exercise 1 — Valid Palindrome (LeetCode 125).
     * Consider only alphanumeric characters, case-insensitive.
     * Example: "A man, a plan, a canal: Panama" -> true
     */
    static boolean isPalindrome(String s) {
        // TODO
        return false;
    }

    /**
     * Exercise 2 — Container With Most Water (LeetCode 11).
     * Given heights, find two lines that with x-axis form a container holding most water.
     * Example: [1,8,6,2,5,4,8,3,7] -> 49
     */
    static int maxArea(int[] heights) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Move Zeroes (LeetCode 283).
     * Same as the Arrays exercise — do it again here with read/write pointers.
     * Example: [0,1,0,3,12] -> [1,3,12,0,0]
     */
    static void moveZeroes(int[] nums) {
        // TODO
    }

    /**
     * Exercise 4 — Linked List Cycle II (LeetCode 142).
     * Return the node where the cycle begins, or null if no cycle.
     * Use Floyd's: when slow == fast, reset one pointer to head and walk both
     * at speed 1; they meet at cycle entry.
     */
    public static class Node {
        int val; Node next;
        Node(int val) { this.val = val; }
    }

    static Node detectCycle(Node head) {
        // TODO
        return null;
    }

    public static void main(String[] args) {
        System.out.println("isPalindrome = " + isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println("maxArea = " + maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));

        int[] arr = {0, 1, 0, 3, 12};
        moveZeroes(arr);
        System.out.println("moveZeroes = " + java.util.Arrays.toString(arr));

        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        Node start = detectCycle(n1);
        System.out.println("detectCycle val = " + (start == null ? "null" : start.val));
        // expected: 2
    }
}
