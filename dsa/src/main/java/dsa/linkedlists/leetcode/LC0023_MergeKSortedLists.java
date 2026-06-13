package dsa.linkedlists.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * LeetCode 23 — Merge k Sorted Lists
 * Difficulty: Hard   Tags: Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort
 * URL: https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * <h2>Problem</h2>
 * Given an array of {@code k} sorted linked lists, merge them into one sorted
 * linked list. Return the head.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; k &le; 10^4</li>
 *   <li>0 &le; lists[i].length &le; 500</li>
 *   <li>Sum of lengths &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[1,4,5],[1,3,4],[2,6]]  -> [1,1,2,3,4,4,5,6]
 *   []                        -> []
 *   [[]]                      -> []
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Min-heap of heads</b> — O(N log k) where N = total nodes.
 * Push the head of each list. Pop the smallest, append to result, push that
 * node's next if non-null. The heap holds at most k nodes at any time.
 * <p>
 * <b>Pairwise divide-and-conquer</b> — same complexity, no heap.
 * Merge lists pairwise (1+2, 3+4, ...) into k/2 lists; repeat until one
 * remains. log k rounds, each doing O(N) work.
 *
 * <h2>Why O(N log k)</h2>
 * Each of N nodes is inserted into and removed from the heap once, at O(log k)
 * per operation. Total O(N log k). Strictly better than naive O(N * k) merging.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty input array — return null.</li>
 *   <li>Lists with nulls — skip them when pushing to the heap.</li>
 *   <li>k == 1 — return that list directly.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.linkedlists.leetcode.LC0023_MergeKSortedLists
 * </pre>
 */
public class LC0023_MergeKSortedLists {

    /** Min-heap of head nodes — O(N log k). */
    public static ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.val));
        for (ListNode l : lists) if (l != null) pq.offer(l);

        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (!pq.isEmpty()) {
            ListNode min = pq.poll();
            cur.next = min;
            cur = cur.next;
            if (min.next != null) pq.offer(min.next);
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode a = ListNode.fromArray(1, 4, 5);
        ListNode b = ListNode.fromArray(1, 3, 4);
        ListNode c = ListNode.fromArray(2, 6);
        System.out.println(ListNode.render(mergeKLists(new ListNode[]{a, b, c})));
        // 1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6

        System.out.println(ListNode.render(mergeKLists(new ListNode[]{})));    // (empty)
        System.out.println(ListNode.render(mergeKLists(new ListNode[]{null})));// (empty)
    }
}
