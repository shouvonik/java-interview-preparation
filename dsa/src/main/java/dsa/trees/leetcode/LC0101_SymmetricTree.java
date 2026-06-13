package dsa.trees.leetcode;

/**
 * LeetCode 101 — Symmetric Tree
 * Difficulty: Easy   Tags: Tree, DFS, BFS, Binary Tree
 * URL: https://leetcode.com/problems/symmetric-tree/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, check whether it is a mirror of itself
 * (i.e. symmetric around its center).
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,2,3,4,4,3]      -> true
 *   [1,2,2,null,3,null,3]-> false
 * </pre>
 *
 * <h2>Approach — pairwise recursion</h2>
 * A tree is symmetric iff its left subtree is the mirror of its right subtree.
 * Define {@code mirror(a, b)}: both null -> true; one null -> false; values
 * differ -> false; else recurse on {@code (a.left, b.right)} and {@code (a.right, b.left)}.
 *
 * <h2>Iterative variant</h2>
 * Use a queue, push {@code (root.left, root.right)} pairs. Pop and compare two
 * at a time; enqueue the cross pairs. Same logic.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0101_SymmetricTree
 * </pre>
 */
public class LC0101_SymmetricTree {

    /** Pairwise DFS — O(n). */
    public static boolean isSymmetric(TreeNode root) {
        return root == null || mirror(root.left, root.right);
    }

    private static boolean mirror(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val
            && mirror(a.left, b.right)
            && mirror(a.right, b.left);
    }

    public static void main(String[] args) {
        //       1
        //      / \
        //     2   2
        //    / \ / \
        //   3  4 4  3
        TreeNode sym = new TreeNode(1,
            new TreeNode(2, new TreeNode(3), new TreeNode(4)),
            new TreeNode(2, new TreeNode(4), new TreeNode(3)));
        System.out.println(isSymmetric(sym));    // true

        //       1
        //      / \
        //     2   2
        //      \   \
        //       3   3
        TreeNode asym = new TreeNode(1,
            new TreeNode(2, null, new TreeNode(3)),
            new TreeNode(2, null, new TreeNode(3)));
        System.out.println(isSymmetric(asym));   // false
    }
}
