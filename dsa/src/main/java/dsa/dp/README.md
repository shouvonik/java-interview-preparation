# Dynamic Programming *(scaffolded — code coming next)*

DP is where most candidates lose points. The trick is to stop trying to "see" the recurrence and start asking three questions in order:

1. **What is a subproblem?** Usually `dp[i]` (1D) or `dp[i][j]` (2D) means "answer considering the first i elements / the range [i, j] / the substring ending at i".
2. **What's the recurrence?** How does `dp[i]` depend on previous states?
3. **What's the base case?** Where do we start?

Memoization (top-down) is usually easier to write; tabulation (bottom-up) is usually faster and uses less stack.

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| 1D linear (climb stairs, house robber) | 70, 198 |
| 1D with state (best time to buy/sell stock IV) | 188 |
| Coin change (unbounded knapsack) | 322 |
| 0/1 knapsack | classic |
| Longest Increasing Subsequence (LIS) | 300 |
| Longest Common Subsequence (LCS) | 1143 |
| Edit distance | 72 |
| Word break | 139 |
| Interval DP / matrix chain | 312 (balloons) |
| Tree DP | 124, 337 |
| Bitmask DP | TSP, 1125 |

## Templates

**Top-down memoization (recursion + cache):**
```java
Integer[] memo = new Integer[n + 1];
int solve(int i) {
    if (i <= 0) return baseCase;
    if (memo[i] != null) return memo[i];
    return memo[i] = combine(solve(i - 1), solve(i - 2), ...);
}
```

**Bottom-up tabulation:**
```java
int[] dp = new int[n + 1];
dp[0] = baseCase;
for (int i = 1; i <= n; i++) {
    dp[i] = combine(dp[i - 1], dp[i - 2], ...);
}
return dp[n];
```

**Space optimization** — when `dp[i]` depends only on `dp[i-1]` and `dp[i-2]`, you only need two variables, not the whole array. Classic for Fibonacci-shape problems.

## The "which DP is this" cheat sheet

| Problem shape | DP shape |
| --- | --- |
| "Number of ways to reach X" | `dp[i] = sum of dp[i-k]` for each step k |
| "Min/max of choosing items" | `dp[i] = min/max(skip, take)` |
| "Longest something subsequence" | `dp[i][j]` for two sequences, or `dp[i]` for one |
| "Can you partition / reach / make" | Boolean `dp[i][s]` |
| "Game theory, both play optimally" | `dp[i][j] = best of {moves}` |

## When NOT to use DP

- **Greedy works** — interval scheduling, gas station, jump game.
- **No overlapping subproblems** — divide and conquer.
- **n is small but answer space is exponential** — backtracking with pruning may beat DP if the recurrence's state space is too big.

## Curated LeetCode (in suggested order)

| # | Problem | Difficulty |
| --- | --- | --- |
| 70 | [Climbing Stairs](https://leetcode.com/problems/climbing-stairs/) | Easy |
| 198 | [House Robber](https://leetcode.com/problems/house-robber/) | Medium |
| 213 | [House Robber II](https://leetcode.com/problems/house-robber-ii/) | Medium |
| 322 | [Coin Change](https://leetcode.com/problems/coin-change/) | Medium |
| 300 | [Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/) | Medium |
| 1143 | [Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/) | Medium |
| 72 | [Edit Distance](https://leetcode.com/problems/edit-distance/) | Hard |
| 139 | [Word Break](https://leetcode.com/problems/word-break/) | Medium |
| 416 | [Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/) | Medium |
| 53 | [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) | Medium (DP view of Kadane's) |
| 121 | [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) | Easy |
| 309 | [Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/) | Medium |
| 5 | [Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/) | Medium |
| 152 | [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/) | Medium |
| 312 | [Burst Balloons](https://leetcode.com/problems/burst-balloons/) | Hard |
| 188 | [Best Time to Buy and Sell Stock IV](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iv/) | Hard |
