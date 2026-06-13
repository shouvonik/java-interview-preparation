package dsa.linkedlists.leetcode;

/**
 * Shared singly-linked-list node, matching the LeetCode-provided definition.
 * Kept once here to avoid redefining it in every LC file.
 */
public class ListNode {
    public int val;
    public ListNode next;

    public ListNode() {}
    public ListNode(int val) { this.val = val; }
    public ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    /** Build a list from values like fromArray(1, 2, 3) -> 1 -> 2 -> 3. */
    public static ListNode fromArray(int... vals) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int v : vals) {
            cur.next = new ListNode(v);
            cur = cur.next;
        }
        return dummy.next;
    }

    /** Render a list as "1 -> 2 -> 3". */
    public static String render(ListNode head) {
        StringBuilder sb = new StringBuilder();
        while (head != null) {
            sb.append(head.val);
            if (head.next != null) sb.append(" -> ");
            head = head.next;
        }
        return sb.toString();
    }
}
