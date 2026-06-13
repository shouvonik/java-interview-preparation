package dsa.linkedlists.leetcode;

/**
 * LeetCode 19 — Remove Nth Node From End of List
 * Difficulty: Medium   Tags: Linked List, Two Pointers
 * URL: https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 *
 * <h2>Problem</h2>
 * Given the head of a linked list, remove the nth node from the end of the
 * list and return its head.
 *
 * <h2>Examples</h2>
 * <pre>
 *   head = [1,2,3,4,5], n = 2  -> [1,2,3,5]
 *   head = [1],        n = 1  -> []
 *   head = [1,2],      n = 1  -> [1]
 *   head = [1,2],      n = 2  -> [2]
 * </pre>
 *
 * <h2>Approach — two pointers with a head-start, single pass</h2>
 * <ol>
 *   <li>Use a dummy node before head to uniformly handle "remove the first node".</li>
 *   <li>Advance {@code fast} n + 1 steps from dummy.</li>
 *   <li>Walk {@code slow} and {@code fast} together until fast is null.</li>
 *   <li>{@code slow} now points at the node BEFORE the one to remove. Splice
 *       it out: {@code slow.next = slow.next.next}.</li>
 * </ol>
 *
 * <h2>Why use a dummy</h2>
 * If the target is the head itself, {@code slow.next = slow.next.next} would
 * be acting on the dummy, which is fine; without the dummy, you'd need a
 * special case.
 *
 * <h2>Alternative — two pass</h2>
 * Compute length L in pass 1, then walk L - n nodes in pass 2 and splice.
 * Same complexity but two passes.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Remove head — dummy handles it.</li>
 *   <li>n equals length — same as removing head.</li>
 *   <li>Single node, n=1 — return null.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0019_RemoveNthFromEnd
 * </pre>
 */
public class LC0019_RemoveNthFromEnd {

    /** Two pointers, single pass — O(L). */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy, slow = dummy;
        for (int i = 0; i <= n; i++) fast = fast.next;     // advance n + 1 steps
        while (fast != null) { fast = fast.next; slow = slow.next; }
        slow.next = slow.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        System.out.println(ListNode.render(removeNthFromEnd(ListNode.fromArray(1, 2, 3, 4, 5), 2))); // 1 -> 2 -> 3 -> 5
        System.out.println(ListNode.render(removeNthFromEnd(ListNode.fromArray(1), 1)));             // (empty)
        System.out.println(ListNode.render(removeNthFromEnd(ListNode.fromArray(1, 2), 1)));          // 1
        System.out.println(ListNode.render(removeNthFromEnd(ListNode.fromArray(1, 2), 2)));          // 2
    }
}
