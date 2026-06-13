# DSA Prep

Topic-by-topic preparation for pair-programming assessments and coding screens. Each topic ships a concept guide, a runnable Java example with hand-traced output, exercise stubs, and reference solutions.

## Progress

| # | Topic | Status | Notes |
| --- | --- | --- | --- |
| 1 | [Arrays](src/main/java/dsa/arrays/) | ☑ built | prefix sums, Kadane's, product-except-self, rotation |
| 2 | [Searching](src/main/java/dsa/searching/) | ☑ built | binary search templates, rotated array, BS-on-answer |
| 3 | [Sorting](src/main/java/dsa/sorting/) | ☑ built | merge/quick/quickselect, Dutch flag, Comparator |
| 4 | [Hashing](src/main/java/dsa/hashing/) | ☑ built | dedup, anagram groups, longest-consecutive, LRU |
| 5 | [Sliding Window](src/main/java/dsa/slidingwindow/) | ☑ built | fixed + variable windows, monotonic deque |
| 6 | [Two Pointers](src/main/java/dsa/twopointers/) | ☑ built | opposite-ends, fast/slow, partition, 3Sum |
| 7 | [Trees](src/main/java/dsa/trees/) | ☑ built | traversals, BST validate, LCA, diameter |
| 8 | [Graphs](src/main/java/dsa/graphs/) | ☑ built | DFS/BFS, topo sort, union-find, Dijkstra |
| 9 | [Linked Lists](src/main/java/dsa/linkedlists/) | ✎ scaffolded | reverse, cycle, merge — to be built |
| 10 | [Stacks & Queues](src/main/java/dsa/stacksqueues/) | ✎ scaffolded | monotonic stack, deque tricks — to be built |
| 11 | [Heaps](src/main/java/dsa/heaps/) | ✎ scaffolded | top-K, k-way merge, scheduling — to be built |
| 12 | [Recursion & Backtracking](src/main/java/dsa/recursion/) | ✎ scaffolded | subsets, permutations, N-Queens — to be built |
| 13 | [Dynamic Programming](src/main/java/dsa/dp/) | ✎ scaffolded | memo→tab, knapsack, LIS, edit distance — to be built |
| 14 | [Greedy](src/main/java/dsa/greedy/) | ✎ scaffolded | intervals, scheduling, exchange argument — to be built |

Tick each box (`☐` → `☑`) as you complete a topic.

## Complexity cheat sheet

| Pattern | Time | Space | When to reach for it |
| --- | --- | --- | --- |
| Linear scan | O(n) | O(1) | Single pass over an array/list |
| Two pointers | O(n) | O(1) | Sorted array, pair/triplet sums, in-place partition |
| Sliding window | O(n) | O(k) | Contiguous subarray/substring with a constraint |
| Binary search | O(log n) | O(1) | Sorted array, or "minimum value that satisfies P" |
| Sorting | O(n log n) | O(n) or O(log n) | When order unlocks linear post-processing |
| Hash map | O(n) avg | O(n) | Lookups, counting, dedup, group-by |
| BFS/DFS on graph | O(V+E) | O(V) | Connectivity, shortest path (unweighted), topo |
| Dijkstra | O((V+E) log V) | O(V) | Shortest path with non-negative weights |
| Union-Find | ~O(α(n)) per op | O(n) | Connected components, cycle detection in undirected |
| DP (memo/tab) | usually O(states × transitions) | O(states) | Overlapping subproblems + optimal substructure |
| Backtracking | O(branching ^ depth) | O(depth) | Enumerate combinatorial choices, prune early |

## How each topic is organised

```
<topic>/
├── README.md                 # concept, complexity, LeetCode list
├── <Topic>.java              # worked guide — run main() to see traces
└── exercises/
    ├── <Topic>Exercises.java     # try yourself
    └── solutions/
        └── <Topic>Solutions.java # reference solutions
```

## Running code

From `dsa/src/main/java/`:

```bash
javac dsa/arrays/Arrays.java
java dsa.arrays.Arrays
```

To compile everything at once:

```bash
find dsa -name "*.java" | xargs javac -d /tmp/dsa-out
```
