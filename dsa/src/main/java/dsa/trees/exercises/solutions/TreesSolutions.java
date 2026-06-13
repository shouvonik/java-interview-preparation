package dsa.trees.exercises.solutions;

import java.util.*;

/**
 * Reference solutions for TreesExercises.
 */
public class TreesSolutions {

    public static class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int val) { this.val = val; }
    }

    // Exercise 1 — Sentinel -1 means "unbalanced" so we early-exit up the stack.
    static boolean isBalanced(TreeNode root) {
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

    // Exercise 2 — Path can take both sides at any node (in which case it
    // can't continue past that node). Return only the single-side gain upward.
    private static int maxSum;

    static int maxPathSum(TreeNode root) {
        maxSum = Integer.MIN_VALUE;
        gain(root);
        return maxSum;
    }

    private static int gain(TreeNode node) {
        if (node == null) return 0;
        int l = Math.max(0, gain(node.left));     // clamp negative
        int r = Math.max(0, gain(node.right));
        maxSum = Math.max(maxSum, node.val + l + r);
        return node.val + Math.max(l, r);          // can only pass one side up
    }

    // Exercise 3 — Two-arg recursion mirrors the tree against itself.
    static boolean isSymmetric(TreeNode root) {
        return root == null || mirror(root.left, root.right);
    }

    private static boolean mirror(TreeNode a, TreeNode b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.val == b.val
            && mirror(a.left, b.right)
            && mirror(a.right, b.left);
    }

    // Exercise 4 — BFS, last node of each level.
    static List<Integer> rightSideView(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode n = q.poll();
                if (i == size - 1) out.add(n.val);
                if (n.left != null) q.offer(n.left);
                if (n.right != null) q.offer(n.right);
            }
        }
        return out;
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

        System.out.println("isBalanced = " + isBalanced(r));        // true
        System.out.println("maxPathSum = " + maxPathSum(r));        // 13 (4+2+1+2+4)
        System.out.println("isSymmetric = " + isSymmetric(r));      // true
        System.out.println("rightSideView = " + rightSideView(r));  // [1, 2, 3]
    }
}
