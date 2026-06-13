# Trees

Trees show up everywhere — DOM, AST, file systems, B-trees, decision trees, org charts. Most interview problems reduce to one of three patterns:

1. **DFS** (recursive) — when the answer for a node depends on its subtree.
2. **BFS** (level order) — when you care about depth/levels or shortest path.
3. **BST-specific** — exploiting the left < node < right invariant for O(log n) operations.

## Patterns covered in `Trees.java`

| Pattern | When to use | Complexity |
| --- | --- | --- |
| Max depth (post-order DFS) | "How tall is this subtree?" | O(n) |
| Preorder / inorder / postorder | Visit nodes in specific order | O(n) |
| Iterative inorder with stack | DFS without recursion | O(n) time, O(h) space |
| Level-order BFS | Per-level processing, shortest path | O(n) |
| Invert tree | Mirror left/right at every node | O(n) |
| Validate BST | Carry min/max bounds down | O(n) |
| Kth smallest in BST | Inorder gives sorted order — count nodes | O(h + k) |
| Lowest common ancestor | Two targets, find their deepest shared ancestor | O(n) |
| Diameter | Longest path through any node | O(n) |

## Real-life framing

- **Org chart** → "Who is the lowest common manager of two employees?" → LCA.
- **Folder depth limit** → "Reject any path deeper than N levels" → max depth.
- **Reporting span** → "Longest chain of reports through any single manager" → diameter.
- **Permissions hierarchy** → "All managers up to the root for this employee" → ancestor path.

## TreeNode

```java
public static class TreeNode {
    int val;
    TreeNode left, right;
    TreeNode(int val) { this.val = val; }
}
```

## Recursion templates

**Post-order pattern — "ask each subtree, then combine":**
```java
int solve(TreeNode node) {
    if (node == null) return baseCase;
    int leftAns  = solve(node.left);
    int rightAns = solve(node.right);
    return combine(leftAns, rightAns, node);
}
```

**Bounds pattern — for BST-style invariants:**
```java
boolean valid(TreeNode node, Long min, Long max) {
    if (node == null) return true;
    if (node.val <= min || node.val >= max) return false;
    return valid(node.left, min, (long) node.val)
        && valid(node.right, (long) node.val, max);
}
```

## Pitfalls

- **Validate BST with parent comparison only** — fails on cases like `[5,1,4,null,null,3,6]` where `3 < 5` but appears in the right subtree. Use min/max bounds OR inorder + monotonicity.
- **Diameter using just a single return value** — won't work; you need to track *global* diameter while returning *depth* from recursion. Standard trick: instance field or single-element array.
- **Iterative inorder** — push left until null, pop, visit, then move to `cur = popped.right`. Forgetting `cur = ...` is the classic bug.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 104 | [Maximum Depth of Binary Tree](https://leetcode.com/problems/maximum-depth-of-binary-tree/) | Easy | Post-order DFS |
| 226 | [Invert Binary Tree](https://leetcode.com/problems/invert-binary-tree/) | Easy | Recursion |
| 102 | [Binary Tree Level Order Traversal](https://leetcode.com/problems/binary-tree-level-order-traversal/) | Medium | BFS |
| 98 | [Validate Binary Search Tree](https://leetcode.com/problems/validate-binary-search-tree/) | Medium | Bounds DFS |
| 230 | [Kth Smallest Element in a BST](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) | Medium | Inorder + count |
| 236 | [Lowest Common Ancestor of a Binary Tree](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) | Medium | DFS bubbling |
| 543 | [Diameter of Binary Tree](https://leetcode.com/problems/diameter-of-binary-tree/) | Easy | Depth + global max |
| 110 | [Balanced Binary Tree](https://leetcode.com/problems/balanced-binary-tree/) | Easy | Post-order with sentinel |
| 124 | [Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/) | Hard | Diameter variant |
| 297 | [Serialize and Deserialize Binary Tree](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) | Hard | Preorder + null markers |

## Run

```bash
cd dsa/src/main/java
javac dsa/trees/Trees.java
java dsa.trees.Trees
```
