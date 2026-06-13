package dsa.trees;

import java.util.*;

/**
 * Trees — DFS patterns, BFS, BST tricks. Framed as an org chart.
 *
 * Run: java dsa.trees.Trees
 */
public class Trees {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    // ---------------------------------------------------------------------
    // 1) Max depth — post-order DFS.
    // ---------------------------------------------------------------------
    // Real-life: "What's the deepest path in this reporting hierarchy?"
    static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    // ---------------------------------------------------------------------
    // 2) Traversals — preorder, inorder, postorder (recursive).
    // ---------------------------------------------------------------------
    static void preorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);          // visit FIRST
        preorder(node.left, out);
        preorder(node.right, out);
    }

    static void inorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        inorder(node.left, out);
        out.add(node.val);          // visit IN BETWEEN
        inorder(node.right, out);
    }

    static void postorder(TreeNode node, List<Integer> out) {
        if (node == null) return;
        postorder(node.left, out);
        postorder(node.right, out);
        out.add(node.val);          // visit LAST
    }

    // Iterative inorder — stack pushes left spine, pops and goes right.
    static List<Integer> inorderIterative(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            out.add(cur.val);
            cur = cur.right;
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // 3) Level-order BFS.
    // ---------------------------------------------------------------------
    // Pin the level size at the start of each iteration to keep levels separate.
    static List<List<Integer>> levelOrder(TreeNode root) {
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

    // ---------------------------------------------------------------------
    // 4) Invert tree — swap left/right everywhere.
    // ---------------------------------------------------------------------
    static TreeNode invert(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invert(root.left);
        TreeNode right = invert(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // ---------------------------------------------------------------------
    // 5) Validate BST — carry (min, max) bounds down.
    // ---------------------------------------------------------------------
    // Use Long bounds so int values at the boundary don't cause false negatives.
    static boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private static boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val)
            && validate(node.right, node.val, max);
    }

    // ---------------------------------------------------------------------
    // 6) Kth smallest in a BST — inorder counter.
    // ---------------------------------------------------------------------
    // Iterative inorder so we can short-circuit on the kth pop.
    static int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) { stack.push(cur); cur = cur.left; }
            cur = stack.pop();
            if (--k == 0) return cur.val;
            cur = cur.right;
        }
        throw new IllegalArgumentException("k out of bounds");
    }

    // ---------------------------------------------------------------------
    // 7) Lowest common ancestor (LeetCode 236).
    // ---------------------------------------------------------------------
    // Bubble up: if both sides find a target, the current node is the LCA.
    // Else, return whichever side found something.
    static TreeNode lca(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) return root;
        TreeNode l = lca(root.left, p, q);
        TreeNode r = lca(root.right, p, q);
        if (l != null && r != null) return root;
        return l != null ? l : r;
    }

    // ---------------------------------------------------------------------
    // 8) Diameter — longest path (in edges) between any two nodes.
    // ---------------------------------------------------------------------
    // Each recursion returns DEPTH from its subtree, while updating GLOBAL diameter.
    private static int diameterEdges;

    static int diameter(TreeNode root) {
        diameterEdges = 0;
        depthForDiameter(root);
        return diameterEdges;
    }

    private static int depthForDiameter(TreeNode node) {
        if (node == null) return 0;
        int ld = depthForDiameter(node.left);
        int rd = depthForDiameter(node.right);
        diameterEdges = Math.max(diameterEdges, ld + rd);  // path through node
        return 1 + Math.max(ld, rd);
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        //         5
        //        / \
        //       3   8
        //      / \   \
        //     1   4   9
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(9);

        System.out.println("maxDepth = " + maxDepth(root));         // 3

        List<Integer> pre = new ArrayList<>(); preorder(root, pre);
        List<Integer> in  = new ArrayList<>(); inorder(root, in);
        List<Integer> post= new ArrayList<>(); postorder(root, post);
        System.out.println("preorder  = " + pre);                   // [5, 3, 1, 4, 8, 9]
        System.out.println("inorder   = " + in);                    // [1, 3, 4, 5, 8, 9]
        System.out.println("postorder = " + post);                  // [1, 4, 3, 9, 8, 5]
        System.out.println("inorderIt = " + inorderIterative(root));// [1, 3, 4, 5, 8, 9]

        System.out.println("levelOrder = " + levelOrder(root));     // [[5], [3, 8], [1, 4, 9]]

        System.out.println("isValidBST = " + isValidBST(root));     // true
        System.out.println("kthSmallest k=3 = " + kthSmallest(root, 3));   // 4

        TreeNode p = root.left.left;     // 1
        TreeNode q = root.left.right;    // 4
        System.out.println("lca(1, 4).val = " + lca(root, p, q).val); // 3

        System.out.println("diameter (edges) = " + diameter(root));   // 4
    }
}
