package dsa.trees.leetcode;

/**
 * LeetCode 98 — Validate Binary Search Tree
 * Difficulty: Medium   Tags: Tree, DFS, Binary Search Tree, Binary Tree
 * URL: https://leetcode.com/problems/validate-binary-search-tree/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, determine if it is a valid binary search tree.
 * A valid BST:
 * <ul>
 *   <li>The left subtree of a node contains only nodes with keys STRICTLY LESS than the node's key.</li>
 *   <li>The right subtree of a node contains only nodes with keys STRICTLY GREATER than the node's key.</li>
 *   <li>Both subtrees must also be BSTs.</li>
 * </ul>
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nodes &le; 10^4</li>
 *   <li>-2^31 &le; Node.val &le; 2^31 - 1</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [2,1,3]                   -> true
 *   [5,1,4,null,null,3,6]     -> false (3 is in right subtree of 5 but 3 &lt; 5)
 * </pre>
 *
 * <h2>Common WRONG approach</h2>
 * Comparing each node only with its left and right CHILD — this misses the
 * cross-subtree constraint (e.g. {@code [5,1,4,null,null,3,6]} would pass).
 *
 * <h2>Approach — carry (min, max) bounds down</h2>
 * Pass an open interval {@code (lo, hi)} down the recursion. Each node must
 * satisfy {@code lo < node.val < hi}. Recurse: left uses {@code (lo, node.val)},
 * right uses {@code (node.val, hi)}.
 * <p>
 * Use {@code Long} for bounds so {@code Integer.MIN_VALUE} and {@code MAX_VALUE}
 * as node values don't get clamped at the boundary.
 *
 * <h2>Alternative — inorder traversal must be strictly increasing</h2>
 * Inorder of a BST yields a sorted sequence. Walk inorder, tracking the
 * previous value; if any current &le; previous, fail. Equally O(n) and uses
 * the same insight.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single node — always true.</li>
 *   <li>Node value = MIN_VALUE / MAX_VALUE — Long bounds handle them.</li>
 *   <li>Duplicates — invalid (strict inequality required).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0098_ValidateBinarySearchTree
 * </pre>
 */
public class LC0098_ValidateBinarySearchTree {

    /** Bounds-DFS — O(n). */
    public static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long lo, long hi) {
        if (node == null) return true;
        if (node.val <= lo || node.val >= hi) return false;
        return validate(node.left, lo, node.val)
            && validate(node.right, node.val, hi);
    }

    public static void main(String[] args) {
        //   2
        //  / \
        // 1   3
        TreeNode a = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        System.out.println(isValidBST(a));    // true

        //   5
        //  / \
        // 1   4    <- invalid: 4 has children 3,6 with 3 < 5 violating BST
        //    / \
        //   3   6
        TreeNode b = new TreeNode(5,
            new TreeNode(1),
            new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        System.out.println(isValidBST(b));    // false

        // Boundary value test
        TreeNode c = new TreeNode(Integer.MIN_VALUE);
        System.out.println(isValidBST(c));    // true
    }
}
