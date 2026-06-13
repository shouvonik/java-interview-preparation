# Two Pointers

Two pointers turns an O(n²) brute force into O(n) by carrying state across iterations. The three flavours you'll meet in interviews:

| Flavour | Shape | Use |
| --- | --- | --- |
| Opposite ends | `left = 0`, `right = n-1`, move inward | Sorted array, pair/triplet sums, palindromes, container problems |
| Fast / slow | Same direction at different speeds | Linked-list cycle, finding the middle |
| Read / write | Same direction, write trails read | In-place partition, dedup, move-zeroes |

## Patterns covered in `TwoPointers.java`

| Pattern | Complexity |
| --- | --- |
| Opposite-ends — 2Sum sorted, reverse string | O(n) |
| Fast/slow — Floyd's cycle detection | O(n), O(1) space |
| Read/write — partition in-place | O(n) |
| 3Sum — outer loop + opposite-ends inner | O(n²) |
| Trapping rain water — opposite-ends with running max | O(n) |

## Real-life framing

- **Opposite ends** → matching warehouse orders with returns when both lists are sorted by amount.
- **Fast/slow on a linked list** → detecting an infinite loop in a redirect chain.
- **3Sum** → finding three transactions that net to zero (basic fraud heuristic).
- **Trapping rain water** → modelling backpressure between two streams at uneven rates.

## Key insight: why two pointers works on sorted data

For 2Sum sorted: if `arr[left] + arr[right] < target`, the only way to grow the sum is to advance `left` — no other choice can help. Symmetric for `>`. This monotone-collapse property is what makes it O(n).

For 3Sum: fix the smallest element with the outer loop; the remaining problem is 2Sum on the suffix — same trick.

## Pitfalls

- **Skipping duplicates** in 3Sum requires `while (left < right && arr[left] == arr[left+1]) left++;` AFTER processing a match. Easy to forget — interviewers do test for duplicates.
- **Floyd's cycle**: when the pointers meet, the cycle's start is found by resetting one pointer to head and walking both at speed 1.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 167 | [Two Sum II — Input array is sorted](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) | Easy | Opposite ends |
| 125 | [Valid Palindrome](https://leetcode.com/problems/valid-palindrome/) | Easy | Opposite ends |
| 283 | [Move Zeroes](https://leetcode.com/problems/move-zeroes/) | Easy | Read/write |
| 26 | [Remove Duplicates from Sorted Array](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) | Easy | Read/write |
| 141 | [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) | Easy | Fast/slow |
| 142 | [Linked List Cycle II](https://leetcode.com/problems/linked-list-cycle-ii/) | Medium | Fast/slow + reset |
| 15 | [3Sum](https://leetcode.com/problems/3sum/) | Medium | Sort + opposite ends |
| 11 | [Container With Most Water](https://leetcode.com/problems/container-with-most-water/) | Medium | Opposite ends |
| 75 | [Sort Colors](https://leetcode.com/problems/sort-colors/) | Medium | Dutch flag (3 pointers) |
| 42 | [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) | Hard | Opposite ends + running max |
| 18 | [4Sum](https://leetcode.com/problems/4sum/) | Medium | 2 outer loops + 2 pointers |

## Run

```bash
cd dsa/src/main/java
javac dsa/twopointers/TwoPointers.java
java dsa.twopointers.TwoPointers
```
