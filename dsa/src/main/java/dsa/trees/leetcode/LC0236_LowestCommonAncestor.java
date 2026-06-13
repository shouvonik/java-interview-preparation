package dsa.trees.leetcode;

/**
 * LeetCode 236 — Lowest Common Ancestor of a Binary Tree
 * Difficulty: Medium   Tags: Tree, DFS, Binary Tree
 * URL: https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 *
 * <h2>Problem</h2>
 * Given a binary tree, find the lowest common ancestor (LCA) of two given
 * nodes p and q. The LCA is the lowest node in the tree that has both p and q
 * as descendants (where a node is also considered a descendant of itself).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>2 &le; nodes &le; 10^5</li>
 *   <li>All node values are unique.</li>
 *   <li>p != q.</li>
 *   <li>Both p and q exist in the tree.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1  -> 3
 *   same root, p = 5, q = 4  -> 5   (a node can be its own descendant)
 *   [1,2], p = 1, q = 2  -> 1
 * </pre>
 *
 * <h2>Approach — bubble up</h2>
 * Recurse on root:
 * <ul>
 *   <li>If root is null or matches p or q, return root.</li>
 *   <li>Recurse on left and right.</li>
 *   <li>If both halves return non-null, root is the LCA (p in one half, q in
 *       the other).</li>
 *   <li>Otherwise return whichever half found something.</li>
 * </ul>
 *
 * <h2>Why this works</h2>
 * Each leaf reports back its match (or null). At every node, "I found one of
 * them on the left AND one on the right" means I'm the meeting point. If both
 * are on the same side, the recursion in that side already identified the LCA
 * — we just pass it up.
 *
 * <h2>BST shortcut (not applicable here)</h2>
 * In a BST, LCA is the first node whose value lies between p and q (LeetCode 235).
 * General binary trees lack that shortcut and need the bubble-up recursion.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>p == root or q == root — root is the LCA.</li>
 *   <li>p ancestor of q (or vice versa) — p (or q) is the LCA.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0236_LowestCommonAncestor
 * </pre>
 */
public class LC0236_LowestCommonAncestor {

    /** Bubble-up DFS — O(n). */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    public static void main(String[] args) {
        //         3
        //        / \
        //       5   1
        //      / \ / \
        //     6  2 0  8
        //       / \
        //      7   4
        TreeNode n7 = new TreeNode(7), n4 = new TreeNode(4);
        TreeNode n6 = new TreeNode(6), n2 = new TreeNode(2, n7, n4);
        TreeNode n0 = new TreeNode(0), n8 = new TreeNode(8);
        TreeNode n5 = new TreeNode(5, n6, n2);
        TreeNode n1 = new TreeNode(1, n0, n8);
        TreeNode root = new TreeNode(3, n5, n1);

        System.out.println(lowestCommonAncestor(root, n5, n1).val);    // 3
        System.out.println(lowestCommonAncestor(root, n5, n4).val);    // 5
        System.out.println(lowestCommonAncestor(root, n7, n4).val);    // 2
    }
}
