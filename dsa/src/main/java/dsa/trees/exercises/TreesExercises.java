package dsa.trees.exercises;

import java.util.List;

/**
 * Trees — exercises.
 */
public class TreesExercises {

    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) { this.val = val; }
    }

    /**
     * Exercise 1 — Balanced Binary Tree (LeetCode 110).
     * Return true if for every node, the heights of left/right subtrees differ
     * by at most 1.
     * Hint: post-order returning -1 as sentinel for "not balanced" short-circuits.
     */
    static boolean isBalanced(TreeNode root) {
        // TODO
        return false;
    }

    /**
     * Exercise 2 — Binary Tree Maximum Path Sum (LeetCode 124).
     * A path is a sequence of nodes connected by edges; path doesn't need to go through root.
     * Return the maximum sum over any path.
     * Hint: like diameter but tracks sum; clamp negative gains to 0.
     */
    static int maxPathSum(TreeNode root) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Symmetric Tree (LeetCode 101).
     * Return true if a tree is a mirror of itself.
     * Hint: recurse on (left.left, right.right) and (left.right, right.left).
     */
    static boolean isSymmetric(TreeNode root) {
        // TODO
        return false;
    }

    /**
     * Exercise 4 — Right Side View (LeetCode 199).
     * Return the values visible from the right side, top to bottom.
     * Hint: BFS, last node of each level.
     */
    static List<Integer> rightSideView(TreeNode root) {
        // TODO
        return List.of();
    }

    public static void main(String[] args) {
        //         1
        //        / \
        //       2   2
        //      / \ / \
        //     3  4 4  3
        TreeNode r = new TreeNode(1);
        r.left = new TreeNode(2); r.right = new TreeNode(2);
        r.left.left = new TreeNode(3); r.left.right = new TreeNode(4);
        r.right.left = new TreeNode(4); r.right.right = new TreeNode(3);

        System.out.println("isBalanced = " + isBalanced(r));
        System.out.println("maxPathSum = " + maxPathSum(r));
        System.out.println("isSymmetric = " + isSymmetric(r));
        System.out.println("rightSideView = " + rightSideView(r));
    }
}
