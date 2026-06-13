package dsa.trees.leetcode;

/**
 * LeetCode 226 — Invert Binary Tree
 * Difficulty: Easy   Tags: Tree, DFS, BFS, Binary Tree
 * URL: https://leetcode.com/problems/invert-binary-tree/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, invert the tree (mirror left/right at every
 * node), and return its root.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [4,2,7,1,3,6,9]  ->  [4,7,2,9,6,3,1]
 *   [2,1,3]           ->  [2,3,1]
 *   []                 ->  []
 * </pre>
 *
 * <h2>Approach — DFS, swap children at every node</h2>
 * Recurse on left, recurse on right, then swap. Order of the three steps
 * doesn't matter for correctness.
 *
 * <h2>Iterative variant</h2>
 * BFS with a queue; for each dequeued node, swap children then enqueue them.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Null — return null.</li>
 *   <li>Single node — itself.</li>
 *   <li>Already a mirror — inverting again gives the original.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0226_InvertBinaryTree
 * </pre>
 */
public class LC0226_InvertBinaryTree {

    /** DFS swap — O(n). */
    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    /** Pre-order printer for verification. */
    private static String preorder(TreeNode n) {
        if (n == null) return "null";
        return n.val + "," + preorder(n.left) + "," + preorder(n.right);
    }

    public static void main(String[] args) {
        //     4
        //    / \
        //   2   7
        //  / \ / \
        // 1  3 6  9
        TreeNode root = new TreeNode(4,
            new TreeNode(2, new TreeNode(1), new TreeNode(3)),
            new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        System.out.println(preorder(invertTree(root)));
        // 4,7,9,null,null,6,null,null,2,3,null,null,1,null,null

        System.out.println(preorder(invertTree(null)));   // null
    }
}
