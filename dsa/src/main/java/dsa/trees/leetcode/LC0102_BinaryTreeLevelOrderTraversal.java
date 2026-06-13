package dsa.trees.leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * LeetCode 102 — Binary Tree Level Order Traversal
 * Difficulty: Medium   Tags: Tree, BFS, Binary Tree
 * URL: https://leetcode.com/problems/binary-tree-level-order-traversal/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, return the level order traversal of its
 * nodes' values (i.e., from left to right, level by level).
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3,9,20,null,null,15,7]  -> [[3], [9, 20], [15, 7]]
 *   [1]                       -> [[1]]
 *   []                         -> []
 * </pre>
 *
 * <h2>Approach — BFS with level size pinned</h2>
 * Standard BFS using a queue. At the start of each iteration, capture the
 * current queue size — that's exactly the number of nodes at the current
 * level. Pop that many nodes into a level list, enqueueing their children.
 *
 * <h2>Why pin the level size?</h2>
 * Without it, you can't tell when a level ends because the queue keeps mixing
 * the next level in. Snapshotting size at iteration start cleanly separates levels.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty tree — return empty list.</li>
 *   <li>Single node — one level with one element.</li>
 *   <li>Skewed tree — n levels, each with one element.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0102_BinaryTreeLevelOrderTraversal
 * </pre>
 */
public class LC0102_BinaryTreeLevelOrderTraversal {

    /** BFS, one level per outer iteration — O(n). */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                level.add(n.val);
                if (n.left != null)  q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
            out.add(level);
        }
        return out;
    }

    public static void main(String[] args) {
        //     3
        //    / \
        //   9  20
        //      / \
        //     15  7
        TreeNode root = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(levelOrder(root));   // [[3], [9, 20], [15, 7]]

        System.out.println(levelOrder(new TreeNode(1)));   // [[1]]
        System.out.println(levelOrder(null));               // []
    }
}
