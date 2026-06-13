package dsa.trees.leetcode;

/**
 * LeetCode 104 — Maximum Depth of Binary Tree
 * Difficulty: Easy   Tags: Tree, DFS, BFS, Binary Tree
 * URL: https://leetcode.com/problems/maximum-depth-of-binary-tree/
 *
 * <h2>Problem</h2>
 * Given the root of a binary tree, return its maximum depth.
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 *
 * <h2>Examples</h2>
 * <pre>
 *   [3,9,20,null,null,15,7]  -> 3
 *   [1,null,2]                -> 2
 *   []                         -> 0
 * </pre>
 *
 * <h2>Approach — post-order DFS</h2>
 * Depth of a node = 1 + max(depth(left), depth(right)). Base case: null node
 * has depth 0. Single line.
 *
 * <h2>Alternative — BFS by level</h2>
 * Run level-order BFS counting levels visited; final count is the depth.
 * Useful when you'd OOM the call stack (10^4 nodes is fine; pathological
 * skewed trees in millions would matter).
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0104_MaximumDepthOfBinaryTree
 * </pre>
 */
public class LC0104_MaximumDepthOfBinaryTree {

    /** Post-order DFS — O(n). */
    public static int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(maxDepth(root));                       // 3
        System.out.println(maxDepth(new TreeNode(1, null, new TreeNode(2)))); // 2
        System.out.println(maxDepth(null));                       // 0
    }
}
