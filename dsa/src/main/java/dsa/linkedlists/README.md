# Linked Lists *(scaffolded — code coming next)*

Linked lists rarely appear in production Java code (`ArrayDeque` beats `LinkedList` almost always) but interviewers love them because they force you to think about **pointer manipulation without indices**.

## Patterns to cover

| Pattern | LeetCode hook |
| --- | --- |
| Reverse a linked list (iterative + recursive) | 206 |
| Detect cycle (Floyd's) | 141, 142 |
| Find middle of list | 876 |
| Merge two sorted lists | 21 |
| Merge K sorted lists (heap) | 23 |
| Reorder list / palindrome | 234, 143 |
| Remove Nth from end (two pass / one pass) | 19 |
| Add two numbers represented as lists | 2 |
| Copy list with random pointer | 138 |

## Key idioms

- **Dummy head** — return `dummy.next` to avoid edge cases for head-of-list operations.
- **Prev pointer** — when you need to splice in/out a node, you usually need the *previous* node, not the current.
- **Two pointers** — fast/slow for middle, cycle detection, Nth-from-end.

## Curated LeetCode

| # | Problem | Difficulty |
| --- | --- | --- |
| 206 | [Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/) | Easy |
| 21 | [Merge Two Sorted Lists](https://leetcode.com/problems/merge-two-sorted-lists/) | Easy |
| 141 | [Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/) | Easy |
| 876 | [Middle of the Linked List](https://leetcode.com/problems/middle-of-the-linked-list/) | Easy |
| 234 | [Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/) | Easy |
| 19 | [Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) | Medium |
| 143 | [Reorder List](https://leetcode.com/problems/reorder-list/) | Medium |
| 2 | [Add Two Numbers](https://leetcode.com/problems/add-two-numbers/) | Medium |
| 138 | [Copy List with Random Pointer](https://leetcode.com/problems/copy-list-with-random-pointer/) | Medium |
| 25 | [Reverse Nodes in k-Group](https://leetcode.com/problems/reverse-nodes-in-k-group/) | Hard |
| 23 | [Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/) | Hard |
