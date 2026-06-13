package dsa.trees.leetcode;

/**
 * LeetCode 124 — Binary Tree Maximum Path Sum
 * Difficulty: Hard   Tags: Tree, DFS, Dynamic Programming, Binary Tree
 * URL: https://leetcode.com/problems/binary-tree-maximum-path-sum/
 *
 * <h2>Problem</h2>
 * A path in a binary tree is a sequence of nodes where each adjacent pair has
 * an edge connecting them. A node can appear at most once in the path. The
 * path does NOT need to pass through the root. Return the maximum path sum of
 * any (non-empty) path.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nodes &le; 3 * 10^4</li>
 *   <li>-1000 &le; Node.val &le; 1000</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1,2,3]               -> 6   (2 + 1 + 3)
 *   [-10,9,20,null,null,15,7] -> 42  (15 + 20 + 7)
 *   [-3]                  -> -3
 * </pre>
 *
 * <h2>Approach — diameter-style DFS with a global max</h2>
 * For each node, compute the best one-side gain (the max sum of a path that
 * starts at this node and goes downward through one child). That's the value
 * the node can pass UP to its parent. But the global answer at this node could
 * use BOTH sides — so update a global {@code best} with
 * {@code node.val + leftGain + rightGain}.
 *
 * <h2>Clamping negative gains</h2>
 * Take {@code max(0, childGain)} — if a child's best is negative, it's worse
 * than not including it. This lets paths "skip" bad subtrees by not entering them.
 *
 * <h2>Why the recursion can't return the two-sided sum upward</h2>
 * A path that bends at a node can't continue past that node (each node appears
 * at most once). So upward we return only the single-side gain.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single negative node — answer is its value (we must use at least one node).</li>
 *   <li>All positive — answer is the sum of the longest possible path.</li>
 *   <li>Mix — clamping handles it without special cases.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0124_BinaryTreeMaximumPathSum
 * </pre>
 */
public class LC0124_BinaryTreeMaximumPathSum {

    private static int best;

    /** DFS returning single-side gain, updating global best — O(n). */
    public static int maxPathSum(TreeNode root) {
        best = Integer.MIN_VALUE;
        gain(root);
        return best;
    }

    private static int gain(TreeNode node) {
        if (node == null) return 0;
        int l = Math.max(0, gain(node.left));
        int r = Math.max(0, gain(node.right));
        best = Math.max(best, node.val + l + r);
        return node.val + Math.max(l, r);
    }

    public static void main(String[] args) {
        // [1, 2, 3]
        TreeNode a = new TreeNode(1, new TreeNode(2), new TreeNode(3));
        System.out.println(maxPathSum(a));    // 6

        // [-10, 9, 20, null, null, 15, 7]
        TreeNode b = new TreeNode(-10,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println(maxPathSum(b));    // 42

        System.out.println(maxPathSum(new TreeNode(-3))); // -3
    }
}
