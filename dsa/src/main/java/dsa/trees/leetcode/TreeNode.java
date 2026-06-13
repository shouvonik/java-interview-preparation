package dsa.trees.leetcode;

/**
 * Shared binary tree node — matches the LeetCode-provided definition.
 * Kept once here to avoid redefining it in every LC file.
 */
public class TreeNode {
    public int val;
    public TreeNode left, right;

    public TreeNode() {}
    public TreeNode(int val) { this.val = val; }
    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
