package dsa.linkedlists;

/**
 * Linked Lists — pointer-manipulation patterns for coding interviews.
 *
 * Run: java dsa.linkedlists.LinkedLists
 *
 * Three idioms to memorise:
 *   - Dummy head (return dummy.next): uniform handling of edge-of-list cases.
 *   - Slow/fast: middle finding and Floyd's cycle detection.
 *   - Three pointers (prev, curr, next): in-place reverse.
 */
public class LinkedLists {

    public static class Node {
        int val;
        Node next;
        Node(int val) { this.val = val; }
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

    // ---------------------------------------------------------------------
    // 1) Reverse a linked list — three pointers.
    // ---------------------------------------------------------------------
    static Node reverse(Node head) {
        Node prev = null, curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    // ---------------------------------------------------------------------
    // 2) Find the middle — slow/fast.
    // ---------------------------------------------------------------------
    static Node middle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    // ---------------------------------------------------------------------
    // 3) Detect cycle — Floyd's tortoise and hare.
    // ---------------------------------------------------------------------
    static boolean hasCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // 4) Merge two sorted lists — dummy head + splice.
    // ---------------------------------------------------------------------
    static Node merge(Node a, Node b) {
        Node dummy = new Node(0);
        Node cur = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) { cur.next = a; a = a.next; }
            else                { cur.next = b; b = b.next; }
            cur = cur.next;
        }
        cur.next = (a != null) ? a : b;
        return dummy.next;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("reverse([1..5]) = " + render(reverse(fromArray(1, 2, 3, 4, 5))));
        // 5 -> 4 -> 3 -> 2 -> 1

        System.out.println("middle([1..5])  = " + middle(fromArray(1, 2, 3, 4, 5)).val);
        // 3

        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        System.out.println("hasCycle(cycle) = " + hasCycle(n1));    // true
        System.out.println("hasCycle(none)  = " + hasCycle(fromArray(1, 2, 3))); // false

        System.out.println("merge sorted    = " + render(merge(fromArray(1, 3, 5), fromArray(2, 4, 6))));
        // 1 -> 2 -> 3 -> 4 -> 5 -> 6
    }
}
