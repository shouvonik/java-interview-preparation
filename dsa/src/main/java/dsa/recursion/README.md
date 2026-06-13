# Recursion & Backtracking *(scaffolded — code coming next)*

Recursion = a function calling itself. Backtracking = recursion that tries a choice, recurses, and **undoes the choice** if it doesn't work. The hallmark is the `path.add(...); recurse(...); path.remove(path.size() - 1);` pattern.

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| Subsets / power set | 78, 90 |
| Permutations | 46, 47 |
| Combinations (k-of-n) | 77, 39, 40 |
| Letter combinations of a phone number | 17 |
| Generate parentheses | 22 |
| Word search in grid | 79 |
| N-Queens | 51 |
| Sudoku solver | 37 |
| Palindrome partitioning | 131 |

## Backtracking template

```java
void backtrack(State state, List<Choice> path, List<List<Choice>> out) {
    if (isComplete(state)) {
        out.add(new ArrayList<>(path));   // snapshot!
        return;
    }
    for (Choice c : candidates(state)) {
        if (!isValid(c, state)) continue;
        path.add(c);                       // make choice
        backtrack(applied(state, c), path, out);
        path.remove(path.size() - 1);     // UNDO choice
    }
}
```

## Pruning matters

The naive complexity is the product of branching factors — exponential. Pruning is what makes backtracking actually finish in interviews:

- **Sort + skip duplicates** when generating subsets/permutations with duplicates (sort the array, then `if (i > start && nums[i] == nums[i-1]) continue;`).
- **Constraint propagation** — Sudoku/N-Queens precompute "which positions are still legal".
- **Memoization** — when paths re-converge, you're really in DP territory.

## Pitfalls

- **Forgetting to snapshot** — `out.add(path)` adds the *reference*; you need `new ArrayList<>(path)` because you mutate `path` after.
- **Backtracking with strings** — building strings via `+` in the loop is O(n²). Use a `StringBuilder` and `sb.setLength(sb.length() - 1)` to undo.

## Recursion vs iteration

Tail-recursive functions can always be converted to loops, but interviewers expect you to feel comfortable with recursion for tree/graph/backtracking problems. JVM doesn't do tail-call optimization, so deep recursion (depth > ~10k) risks StackOverflow — flag this in interviews.

## Curated LeetCode

| # | Problem | Difficulty |
| --- | --- | --- |
| 78 | [Subsets](https://leetcode.com/problems/subsets/) | Medium |
| 90 | [Subsets II](https://leetcode.com/problems/subsets-ii/) | Medium |
| 46 | [Permutations](https://leetcode.com/problems/permutations/) | Medium |
| 47 | [Permutations II](https://leetcode.com/problems/permutations-ii/) | Medium |
| 77 | [Combinations](https://leetcode.com/problems/combinations/) | Medium |
| 39 | [Combination Sum](https://leetcode.com/problems/combination-sum/) | Medium |
| 17 | [Letter Combinations of a Phone Number](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) | Medium |
| 22 | [Generate Parentheses](https://leetcode.com/problems/generate-parentheses/) | Medium |
| 79 | [Word Search](https://leetcode.com/problems/word-search/) | Medium |
| 131 | [Palindrome Partitioning](https://leetcode.com/problems/palindrome-partitioning/) | Medium |
| 51 | [N-Queens](https://leetcode.com/problems/n-queens/) | Hard |
| 37 | [Sudoku Solver](https://leetcode.com/problems/sudoku-solver/) | Hard |
