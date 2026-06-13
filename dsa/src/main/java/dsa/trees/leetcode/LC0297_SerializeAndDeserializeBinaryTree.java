package dsa.trees.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode 297 — Serialize and Deserialize Binary Tree
 * Difficulty: Hard   Tags: Tree, DFS, BFS, Design, String, Binary Tree
 * URL: https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 *
 * <h2>Problem</h2>
 * Design an algorithm to serialize and deserialize a binary tree. Serialization
 * is the process of converting a data structure into a string; deserialization
 * is the reverse. The format is up to you — but
 * {@code deserialize(serialize(tree))} must equal the original tree.
 *
 * <h2>Approach — preorder with null markers</h2>
 * Walk the tree preorder; emit each value and use a sentinel (e.g. "#") for
 * null. Join with a separator.
 * <p>
 * Deserialise by tokenising and consuming one token at a time via a queue,
 * applying the same preorder shape recursively.
 *
 * <h2>Why preorder + null markers</h2>
 * The marker disambiguates structure; without it, "1 2 3" could represent
 * multiple tree shapes. With markers like "1 2 # # 3 # #", the structure is
 * uniquely determined.
 *
 * <h2>Alternative — level-order (BFS)</h2>
 * Matches LeetCode's own format (e.g. {@code [1,2,3,null,null,4,5]}). More
 * compact for sparse trees but a bit more careful to implement: enqueue parent,
 * dequeue, read two child tokens.
 *
 * <h2>Performance</h2>
 * Preorder approach: O(n) time for both operations, O(n) space for the string.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty tree — serialise to "#"; deserialise back to null.</li>
 *   <li>Single node — "v # #".</li>
 *   <li>Negative values — separator must NOT collide; use comma.</li>
 *   <li>Large trees with recursion depth — for skewed trees of millions, the
 *       recursive serialize can blow the stack. Use an iterative version if
 *       constraints permit.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.trees.leetcode.LC0297_SerializeAndDeserializeBinaryTree
 * </pre>
 */
public class LC0297_SerializeAndDeserializeBinaryTree {

    public static class Codec {

        public String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            writePreorder(root, sb);
            return sb.toString();
        }

        private void writePreorder(TreeNode node, StringBuilder sb) {
            if (node == null) { sb.append("#,"); return; }
            sb.append(node.val).append(',');
            writePreorder(node.left, sb);
            writePreorder(node.right, sb);
        }

        public TreeNode deserialize(String data) {
            Deque<String> tokens = new ArrayDeque<>();
            for (String tok : data.split(",")) tokens.offer(tok);
            return readPreorder(tokens);
        }

        private TreeNode readPreorder(Deque<String> tokens) {
            String tok = tokens.poll();
            if (tok == null || tok.equals("#")) return null;
            TreeNode node = new TreeNode(Integer.parseInt(tok));
            node.left = readPreorder(tokens);
            node.right = readPreorder(tokens);
            return node;
        }
    }

    /** Verify a round-trip by re-serialising and comparing strings. */
    public static void main(String[] args) {
        Codec codec = new Codec();

        //     1
        //    / \
        //   2   3
        //       / \
        //      4   5
        TreeNode tree = new TreeNode(1,
            new TreeNode(2),
            new TreeNode(3, new TreeNode(4), new TreeNode(5)));
        String s = codec.serialize(tree);
        System.out.println(s);                              // 1,2,#,#,3,4,#,#,5,#,#,
        TreeNode roundTrip = codec.deserialize(s);
        System.out.println(codec.serialize(roundTrip));     // should match s

        System.out.println(codec.serialize(null));          // #,
        System.out.println(codec.serialize(codec.deserialize("#,"))); // #,
    }
}
