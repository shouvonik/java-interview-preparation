package dsa.twopointers.exercises.solutions;

/**
 * Reference solutions for TwoPointersExercises.
 */
public class TwoPointersSolutions {

    // Exercise 1 — Skip non-alphanumeric, compare lowercase from both ends.
    static boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))  left++;
            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++; right--;
        }
        return true;
    }

    // Exercise 2 — Greedy two pointers; always move the shorter wall inward.
    // Moving the taller wall can never produce a bigger container.
    static int maxArea(int[] heights) {
        int left = 0, right = heights.length - 1, best = 0;
        while (left < right) {
            int h = Math.min(heights[left], heights[right]);
            best = Math.max(best, h * (right - left));
            if (heights[left] < heights[right]) left++;
            else                                 right--;
        }
        return best;
    }

    // Exercise 3 — Read/write index. Walk through, copy non-zeros forward.
    static void moveZeroes(int[] nums) {
        int write = 0;
        for (int n : nums) {
            if (n != 0) nums[write++] = n;
        }
        while (write < nums.length) nums[write++] = 0;
    }

    // Exercise 4 — Floyd's: find meeting point, then reset to head and walk
    // at speed 1. They meet at cycle entry.
    public static class Node {
        int val; Node next;
        Node(int val) { this.val = val; }
    }

    static Node detectCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {                                 // cycle confirmed
                Node ptr = head;
                while (ptr != slow) { ptr = ptr.next; slow = slow.next; }
                return ptr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("isPalindrome = " + isPalindrome("A man, a plan, a canal: Panama"));
        // true

        System.out.println("maxArea = " + maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        // 49

        int[] arr = {0, 1, 0, 3, 12};
        moveZeroes(arr);
        System.out.println("moveZeroes = " + java.util.Arrays.toString(arr));
        // [1, 3, 12, 0, 0]

        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        Node start = detectCycle(n1);
        System.out.println("detectCycle val = " + (start == null ? "null" : start.val));
        // 2
    }
}
