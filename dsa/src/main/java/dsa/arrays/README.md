# Arrays

The foundation pattern. Most array problems reduce to one of: **scan**, **prefix sum**, **two pointers**, **sliding window**, or **sort then linear**. Knowing which one a problem wants is half the battle.

## Patterns covered in `ArraysGuide.java`

| Pattern | When to use | Complexity |
| --- | --- | --- |
| Prefix sum | Many range-sum queries on a static array | O(n) build, O(1) per query |
| Kadane's algorithm | Max sum of a contiguous subarray | O(n) time, O(1) space |
| Product except self | Result depends on "everything but i" | O(n) time, O(1) extra (output array aside) |
| In-place rotation | Rotate without a second array | O(n) time, O(1) space |

## Real-life framing

- **Prefix sums** → daily-revenue array → "total revenue between day i and day j" answered in O(1).
- **Kadane's** → stock trading P&L stream → best consecutive winning streak.
- **Product except self** → leaderboard score scaling without revealing player's own score.
- **Rotation** → log files split by a deploy boundary — re-aligning them in place.

## Decision guide

> "I'm given an array and asked about a contiguous subarray with some property."
> → Sliding window if the property changes monotonically, **Kadane's** if it's a sum/max, two pointers if sorted.

> "I'm asked about ranges and queries."
> → **Prefix sum** (or Fenwick tree if updates are allowed).

> "The answer for index i depends on all other elements."
> → **Prefix/suffix arrays** (product-except-self pattern).

## Curated LeetCode

Work through these in order — easy → hard:

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 1 | [Two Sum](https://leetcode.com/problems/two-sum/) | Easy | Hash map |
| 121 | [Best Time to Buy and Sell Stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) | Easy | Single pass |
| 53 | [Maximum Subarray](https://leetcode.com/problems/maximum-subarray/) | Medium | Kadane's |
| 238 | [Product of Array Except Self](https://leetcode.com/problems/product-of-array-except-self/) | Medium | Prefix/suffix |
| 189 | [Rotate Array](https://leetcode.com/problems/rotate-array/) | Medium | Reverse trick |
| 152 | [Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/) | Medium | Kadane variant |
| 560 | [Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/) | Medium | Prefix sum + hash |
| 41 | [First Missing Positive](https://leetcode.com/problems/first-missing-positive/) | Hard | Cyclic sort / in-place |
| 4 | [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/) | Hard | Binary search on partition |

## Run

```bash
cd dsa/src/main/java
javac dsa/arrays/ArraysGuide.java
java dsa.arrays.ArraysGuide
```
