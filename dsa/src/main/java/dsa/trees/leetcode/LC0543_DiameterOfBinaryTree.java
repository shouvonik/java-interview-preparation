package dsa.trees.leetcode;

/**
 * LeetCode 543 — Diameter of Binary Tree
 * Difficulty: Easy   Tags: Tree, DFS, Binary Tree
 * URL: https://leetcode.com/problems/diameter-of-binary-tree/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, return the length of the diameter of the
 * tree. The diameter is the length of the longest path between any two nodes
 * in a tree, measured in EDGES. This path may or may not pass through the root.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3,4,5]  -> 3   (4->2->1->3 or 5->2->1->3, 3 edges)
 *   [1, 2]       -> 1
 *   [1]          -> 0
 * </pre>
 *
 * <h2>Approach — depth-returning DFS with a global max</h2>
 * At each node, the longest path THROUGH it is
 * {@code depth(left) + depth(right)} edges. Compute that at every node and
 * update a global best. Return the depth ({@code 1 + max(left, right)}) upward
 * so the parent can do the same calculation.
 *
 * <h2>Why we can't return diameter and depth in one number</h2>
 * The diameter at a subtree is "best path that fits inside this subtree". The
 * value the parent needs is "best DOWNWARD path from this subtree's root".
 * They differ. Either return a pair, or use a global variable for the diameter.
 * Global is simpler.
 *
 * <h2>Edges vs nodes</h2>
 * If asked for the length in NODES instead, add 1 to the answer (or maintain
 * node count throughout — but edges is the common convention).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single node — 0 edges.</li>
 *   <li>Skewed tree — diameter = depth - 1.</li>
 *   <li>Diameter not through root — the global update at the deepest joint
 *       captures it.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0543_DiameterOfBinaryTree
 * </pre>
 */
public class LC0543_DiameterOfBinaryTree {

    private static int diameter;

    /** DFS returning depth, updating global diameter — O(n). */
    public static int diameterOfBinaryTree(TreeNode root) {
        diameter = 0;
        depth(root);
        return diameter;
    }

    private static int depth(TreeNode node) {
        if (node == null) return 0;
        int l = depth(node.left);
        int r = depth(node.right);
        diameter = Math.max(diameter, l + r);
        return 1 + Math.max(l, r);
    }

    public static void main(String[] args) {
        //     1
        //    / \
        //   2   3
        //  / \
        // 4   5
        TreeNode root = new TreeNode(1,
            new TreeNode(2, new TreeNode(4), new TreeNode(5)),
            new TreeNode(3));
        System.out.println(diameterOfBinaryTree(root));    // 3

        System.out.println(diameterOfBinaryTree(new TreeNode(1, new TreeNode(2), null))); // 1
        System.out.println(diameterOfBinaryTree(new TreeNode(1)));                         // 0
    }
}
