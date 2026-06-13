# Sliding Window

When a problem asks about a **contiguous subarray** or **substring** with some constraint, sliding window usually turns O(n × k) brute force into O(n). The window expands by moving `right`, and contracts by moving `left` whenever the constraint is violated.

## Patterns covered in `SlidingWindow.java`

| Pattern | When to use | Complexity |
| --- | --- | --- |
| Fixed-size window | Window is exactly size k | O(n) |
| Variable-size window | Window grows until invalid, shrinks until valid | O(n) — each element enters/leaves once |
| Monotonic deque | Need max/min over the window in O(1) amortised | O(n) |

## Real-life framing

- **Fixed window** → moving average over a time series (last N samples).
- **Variable window** → rate limiter ("max R requests per T seconds"), longest unique-IP burst.
- **Monotonic deque** → peak-load monitor: rolling max latency over the last K seconds.

## Templates — memorise these

**Fixed-size window:**
```java
int windowSum = 0;
for (int i = 0; i < k; i++) windowSum += nums[i];      // build first window
int best = windowSum;
for (int right = k; right < nums.length; right++) {
    windowSum += nums[right] - nums[right - k];        // slide
    best = Math.max(best, windowSum);
}
```

**Variable-size window (longest valid):**
```java
int left = 0, best = 0;
for (int right = 0; right < nums.length; right++) {
    // add nums[right] to window state
    while (windowIsInvalid()) {
        // remove nums[left] from window state
        left++;
    }
    best = Math.max(best, right - left + 1);
}
```

**Monotonic deque for max in window:**
```java
Deque<Integer> dq = new ArrayDeque<>();   // stores INDICES, values strictly decreasing
for (int i = 0; i < n; i++) {
    while (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();   // expired
    while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
    dq.offerLast(i);
    if (i >= k - 1) result.add(nums[dq.peekFirst()]);
}
```

## Pitfalls

- **Off-by-one on window size** — `right - left + 1` is the number of elements in `[left, right]`.
- **State updates** — make sure you remove `nums[left]` from your map/counter *before* incrementing `left`, otherwise you leak the value.
- **When to update `best`** — for longest-valid problems, update *after* the while loop. For shortest-valid problems, update *inside* the while loop.

## Curated LeetCode

| # | Problem | Difficulty | Pattern |
| --- | --- | --- | --- |
| 643 | [Maximum Average Subarray I](https://leetcode.com/problems/maximum-average-subarray-i/) | Easy | Fixed window |
| 3 | [Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/) | Medium | Variable window + set |
| 567 | [Permutation in String](https://leetcode.com/problems/permutation-in-string/) | Medium | Fixed window + counter |
| 209 | [Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/) | Medium | Variable window (shrink) |
| 424 | [Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/) | Medium | Variable window |
| 1004 | [Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/) | Medium | Variable window |
| 76 | [Minimum Window Substring](https://leetcode.com/problems/minimum-window-substring/) | Hard | Variable window + counters |
| 239 | [Sliding Window Maximum](https://leetcode.com/problems/sliding-window-maximum/) | Hard | Monotonic deque |
| 992 | [Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers/) | Hard | atMost(K) - atMost(K-1) |

## Run

```bash
cd dsa/src/main/java
javac dsa/slidingwindow/SlidingWindow.java
java dsa.slidingwindow.SlidingWindow
```
