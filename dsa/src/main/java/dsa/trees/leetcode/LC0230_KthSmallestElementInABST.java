package dsa.trees.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 230 — Kth Smallest Element in a BST
 * Difficulty: Medium   Tags: Tree, DFS, Binary Search Tree, Binary Tree
 * URL: https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 *
 * <h2>Problem</h2>
 * Given the root of a BST and an integer k, return the k-th smallest value
 * (1-indexed) among all node values in the tree.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == number of nodes; 1 &le; k &le; n &le; 10^4</li>
 *   <li>0 &le; Node.val &le; 10^4</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   root = [3,1,4,null,2], k = 1  -> 1
 *   root = [5,3,6,2,4,null,null,1], k = 3  -> 3
 * </pre>
 *
 * <h2>Approach — iterative inorder with early exit</h2>
 * Inorder traversal of a BST visits values in ascending order. Count visits;
 * when count reaches k, return that value. Iterative form (stack + cur pointer)
 * lets us break out without traversing further.
 *
 * <h2>Follow-up — frequent updates</h2>
 * If the tree is modified often and you need many k-th queries, augment each
 * node with the size of its subtree. Then k-th queries take O(h) by walking
 * down based on the left subtree's size — preserving balance is the harder bit.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k = 1 — leftmost node.</li>
 *   <li>k = n — rightmost node.</li>
 *   <li>Right-skewed BST — inorder still works in O(n).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0230_KthSmallestElementInABST
 * </pre>
 */
public class LC0230_KthSmallestElementInABST {

    /** Iterative inorder, early-exit at k — O(h + k). */
    public static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) { stack.push(cur); cur = cur.left; }
            cur = stack.pop();
            if (--k == 0) return cur.val;
            cur = cur.right;
        }
        throw new IllegalArgumentException("k out of range");
    }

    public static void main(String[] args) {
        //     3
        //    / \
        //   1   4
        //    \
        //     2
        TreeNode a = new TreeNode(3,
            new TreeNode(1, null, new TreeNode(2)),
            new TreeNode(4));
        System.out.println(kthSmallest(a, 1));   // 1

        //          5
        //         / \
        //        3   6
        //       / \
        //      2   4
        //     /
        //    1
        TreeNode b = new TreeNode(5,
            new TreeNode(3,
                new TreeNode(2, new TreeNode(1), null),
                new TreeNode(4)),
            new TreeNode(6));
        System.out.println(kthSmallest(b, 3));   // 3
    }
}
