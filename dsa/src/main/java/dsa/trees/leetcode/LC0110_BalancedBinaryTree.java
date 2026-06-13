package dsa.trees.leetcode;

/**
 * LeetCode 110 — Balanced Binary Tree
 * Difficulty: Easy   Tags: Tree, DFS, Binary Tree
 * URL: https://leetcode.com/problems/balanced-binary-tree/
 *
 * <h2>Problem</h2>
 * Given a binary tree, determine if it is height-balanced.
 * A tree is height-balanced when, for every node, the heights of its left and
 * right subtrees differ by at most 1.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3,9,20,null,null,15,7]  -> true
 *   [1,2,2,3,3,null,null,4,4] -> false
 *   []                         -> true
 * </pre>
 *
 * <h2>Naive approach — O(n log n) worst case</h2>
 * For each node, compute left/right heights and compare. Each height call is
 * O(n) worst case; total O(n^2) for skewed trees.
 *
 * <h2>Better — post-order with sentinel — O(n)</h2>
 * One recursion that returns either the height (if subtree is balanced) or a
 * sentinel like -1 (if unbalanced). Once any subtree returns -1, the whole
 * recursion short-circuits up.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0110_BalancedBinaryTree
 * </pre>
 */
public class LC0110_BalancedBinaryTree {

    /** Post-order with -1 sentinel — O(n). */
    public static boolean isBalanced(TreeNode root) {
        return height(root) != -1;
    }

    private static int height(TreeNode node) {
        if (node == null) return 0;
        int l = height(node.left);
        if (l == -1) return -1;
        int r = height(node.right);
        if (r == -1) return -1;
        if (Math.abs(l - r) > 1) return -1;
        return 1 + Math.max(l, r);
    }

    public static void main(String[] args) {
        //     3
        //    / \
        //   9  20
        //      / \
        //     15  7
        TreeNode balanced = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(isBalanced(balanced));    // true

        //          1
        //         / \
        //        2   2
        //       /     \
        //      3       3
        //     /         \
        //    4           4
        TreeNode unbalanced = new TreeNode(1,
            new TreeNode(2, new TreeNode(3, new TreeNode(4), null), null),
            new TreeNode(2, null, new TreeNode(3, null, new TreeNode(4))));
        System.out.println(isBalanced(unbalanced));  // false

        System.out.println(isBalanced(null));         // true
    }
}
