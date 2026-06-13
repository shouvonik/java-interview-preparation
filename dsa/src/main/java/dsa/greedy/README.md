# Greedy *(scaffolded — code coming next)*

Greedy = make the locally optimal choice at each step and hope it leads to the global optimum. The hard part is **proving** that the local choice is safe — the "exchange argument" is the standard proof technique.

## When greedy works

A problem is amenable to greedy when:

1. **Greedy choice property** — a globally optimal solution can always include the locally optimal choice.
2. **Optimal substructure** — after the choice, the remaining problem is the same shape.

Most interval / scheduling problems fit this. Most "min cost to do X with choices" problems do *not* — they're DP.

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| Interval scheduling / non-overlapping intervals | 435 |
| Activity selection (greedy by end time) | classic |
| Jump game / jump game II | 55, 45 |
| Gas station | 134 |
| Minimum number of platforms / meeting rooms | 253 |
| Task scheduler (greedy + heap) | 621 |
| Reorganize string | 767 |
| Huffman coding (heap) | 1167 |
| Minimum cost to connect ropes (heap) | 1167 / classic |

## Templates

**Interval scheduling (max non-overlapping):**
```java
Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));   // sort by END
int end = Integer.MIN_VALUE, count = 0;
for (int[] iv : intervals) {
    if (iv[0] >= end) {
        count++;
        end = iv[1];
    }
}
```

**Why sort by end and not by start?** Because once you pick an interval ending at e, the soonest valid next interval must start at ≥ e. Sorting by end minimises e at each step, leaving the most room.

## The exchange argument

When you suspect greedy works, prove it by:
1. Assume there's an optimal solution OPT that does NOT make the greedy choice.
2. Show you can swap one element of OPT with the greedy choice without making the answer worse.
3. By induction, the all-greedy solution is also optimal.

If you can't construct that argument, greedy probably doesn't work — fall back to DP.

## Pitfalls

- **Sorting key matters** — sort by start vs. end vs. (end - start) can all look equally "greedy" but only one is correct. Always verify with a small counterexample.
- **Greedy fails for coin change** with arbitrary denominations: `coins=[1,3,4], target=6` — greedy picks 4+1+1 = 3 coins; optimal is 3+3 = 2 coins. That's why coin change is DP, not greedy.

## Curated LeetCode

| # | Problem | Difficulty |
| --- | --- | --- |
| 455 | [Assign Cookies](https://leetcode.com/problems/assign-cookies/) | Easy |
| 122 | [Best Time to Buy and Sell Stock II](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/) | Medium |
| 55 | [Jump Game](https://leetcode.com/problems/jump-game/) | Medium |
| 45 | [Jump Game II](https://leetcode.com/problems/jump-game-ii/) | Medium |
| 134 | [Gas Station](https://leetcode.com/problems/gas-station/) | Medium |
| 435 | [Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/) | Medium |
| 763 | [Partition Labels](https://leetcode.com/problems/partition-labels/) | Medium |
| 621 | [Task Scheduler](https://leetcode.com/problems/task-scheduler/) | Medium |
| 767 | [Reorganize String](https://leetcode.com/problems/reorganize-string/) | Medium |
| 253 | [Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) | Medium |
| 406 | [Queue Reconstruction by Height](https://leetcode.com/problems/queue-reconstruction-by-height/) | Medium |
| 135 | [Candy](https://leetcode.com/problems/candy/) | Hard |
