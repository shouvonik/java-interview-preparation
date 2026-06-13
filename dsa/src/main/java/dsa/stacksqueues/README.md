# Stacks & Queues *(scaffolded — code coming next)*

Two of the simplest data structures, but they enable some clever patterns. In Java, **prefer `ArrayDeque`** for both stack (`push`/`pop`) and queue (`offer`/`poll`) — it beats both `Stack` (synchronized, slow) and `LinkedList` (cache-unfriendly).

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| Valid parentheses (stack match) | 20 |
| Min stack (auxiliary stack of mins) | 155 |
| Evaluate reverse polish notation | 150 |
| Daily temperatures / next greater (monotonic stack) | 739, 496 |
| Largest rectangle in histogram | 84 |
| Implement queue with two stacks | 232 |
| Sliding window max (monotonic deque) | 239 (also in slidingwindow/) |

## When to reach for a monotonic stack

When the question asks **"next greater"**, **"next smaller"**, **"span until..."**, or **"how far back was the last bigger element"** — that's a monotonic stack signal.

```java
// Next greater element template
Deque<Integer> stack = new ArrayDeque<>();   // stores INDICES
int[] result = new int[n];
Arrays.fill(result, -1);
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
        result[stack.pop()] = nums[i];
    }
    stack.push(i);
}
```

## Pitfalls

- `Stack` (the class) is legacy — extends `Vector`, every method is synchronized. Don't use it.
- `LinkedList` works as a `Deque`, but `ArrayDeque` is faster.
- `Queue.add` throws on full bounded queues; `Queue.offer` returns false. Same for `remove` vs `poll`.

## Curated LeetCode

| # | Problem | Difficulty |
| --- | --- | --- |
| 20 | [Valid Parentheses](https://leetcode.com/problems/valid-parentheses/) | Easy |
| 155 | [Min Stack](https://leetcode.com/problems/min-stack/) | Medium |
| 150 | [Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/) | Medium |
| 232 | [Implement Queue using Stacks](https://leetcode.com/problems/implement-queue-using-stacks/) | Easy |
| 739 | [Daily Temperatures](https://leetcode.com/problems/daily-temperatures/) | Medium |
| 496 | [Next Greater Element I](https://leetcode.com/problems/next-greater-element-i/) | Easy |
| 503 | [Next Greater Element II](https://leetcode.com/problems/next-greater-element-ii/) | Medium |
| 853 | [Car Fleet](https://leetcode.com/problems/car-fleet/) | Medium |
| 84 | [Largest Rectangle in Histogram](https://leetcode.com/problems/largest-rectangle-in-histogram/) | Hard |
| 42 | [Trapping Rain Water](https://leetcode.com/problems/trapping-rain-water/) | Hard (alt. stack solution) |
