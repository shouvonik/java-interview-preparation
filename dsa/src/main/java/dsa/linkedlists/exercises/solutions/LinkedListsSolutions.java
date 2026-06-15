package dsa.linkedlists.exercises.solutions;

/**
 * Reference solutions for LinkedListsExercises.
 */
public class LinkedListsSolutions {

    public static class Node {
        public int val;
        public Node next;
        public Node(int val) { this.val = val; }
    }

    static Node fromArray(int... vals) {
        Node dummy = new Node(0);
        Node cur = dummy;
        for (int v : vals) { cur.next = new Node(v); cur = cur.next; }
        return dummy.next;
    }

    static String render(Node head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(" -> ");
            head = head.next;
        }
        return sb.toString();
    }

    // Exercise 1 — Add Two Numbers. Single pass with dummy head and carry.
    static Node addTwoNumbers(Node l1, Node l2) {
        Node dummy = new Node(0), cur = dummy;
        int carry = 0;
        while (l1 != null || l2 != null || carry > 0) {
            int sum = carry + (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val);
            carry = sum / 10;
            cur.next = new Node(sum % 10);
            cur = cur.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        return dummy.next;
    }

    // Exercise 2 — Remove Nth from end. Dummy + fast head-start.
    static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node fast = dummy, slow = dummy;
        for (int i = 0; i <= n; i++) fast = fast.next;
        while (fast != null) { fast = fast.next; slow = slow.next; }
        slow.next = slow.next.next;
        return dummy.next;
    }

    // Exercise 3 — Palindrome via middle + reverse + walk.
    static boolean isPalindrome(Node head) {
        if (head == null || head.next == null) return true;
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node prev = null, curr = slow;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        Node left = head, right = prev;
        while (right != null) {
            if (left.val != right.val) return false;
            left = left.next;
            right = right.next;
        }
        return true;
    }

    // Exercise 4 — Floyd's: find meeting point, reset, walk together.
    static Node detectCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                Node ptr = head;
                while (ptr != slow) { ptr = ptr.next; slow = slow.next; }
                return ptr;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        // 342 + 465
        System.out.println("addTwoNumbers = " + render(addTwoNumbers(fromArray(2, 4, 3), fromArray(5, 6, 4))));
        // 7 -> 0 -> 8

        // Remove 2nd from end of [1..5] -> [1,2,3,5]
        System.out.println("removeNth     = " + render(removeNthFromEnd(fromArray(1, 2, 3, 4, 5), 2)));
        // 1 -> 2 -> 3 -> 5

        System.out.println("isPalindrome([1,2,2,1]) = " + isPalindrome(fromArray(1, 2, 2, 1))); // true
        System.out.println("isPalindrome([1,2,3])   = " + isPalindrome(fromArray(1, 2, 3)));     // false

        Node n1 = new Node(3), n2 = new Node(2), n3 = new Node(0), n4 = new Node(-4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        System.out.println("detectCycle val = " + detectCycle(n1).val);   // 2
    }
}
