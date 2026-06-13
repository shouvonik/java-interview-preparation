package dsa.twopointers.leetcode;

/**
 * LeetCode 283 — Move Zeroes
 * Difficulty: Easy   Tags: Array, Two Pointers
 * URL: https://leetcode.com/problems/move-zeroes/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, move all 0's to the end of it while
 * maintaining the relative order of the non-zero elements. Must be in-place
 * and minimise total operations.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^4</li>
 *   <li>-2^31 &le; nums[i] &le; 2^31 - 1</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [0, 1, 0, 3, 12]  ->  [1, 3, 12, 0, 0]
 *   [0]                ->  [0]
 *   [1, 0]             ->  [1, 0]
 * </pre>
 *
 * <h2>Approach — read / write pointers</h2>
 * Pass 1: walk through with a {@code write} index. For each non-zero value,
 * copy it to {@code nums[write++]}.
 * Pass 2: fill the remaining positions ({@code [write..n)}) with zeros.
 *
 * <h2>One-pass variant — swap-in-place</h2>
 * Walk with {@code i}; whenever {@code nums[i] != 0}, swap {@code nums[write++]}
 * with {@code nums[i]}. Same result with the same complexity; slightly fewer
 * writes when there are few zeros.
 *
 * <h2>Why this preserves order</h2>
 * Non-zero values are processed left-to-right, so they land in {@code write}
 * positions in the same order. The trailing zero-fill doesn't disturb them.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All zeros — write stays at 0; pass 2 fills the entire array (no-op).</li>
 *   <li>No zeros — write advances to n; pass 2 does nothing.</li>
 *   <li>Single element — return as-is.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0283_MoveZeroes
 * </pre>
 */
public class LC0283_MoveZeroes {

    /** Read / write pointers, two-pass — O(n). */
    public static void moveZeroes(int[] nums) {
        int write = 0;
        for (int n : nums) {
            if (n != 0) nums[write++] = n;
        }
        while (write < nums.length) nums[write++] = 0;
    }

    /** Swap-in-place, one pass. Slightly fewer writes when few zeros. */
    public static void moveZeroesSwap(int[] nums) {
        int write = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                int t = nums[write]; nums[write] = nums[i]; nums[i] = t;
                write++;
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {0, 1, 0, 3, 12};
        moveZeroes(a);
        System.out.println(java.util.Arrays.toString(a));   // [1, 3, 12, 0, 0]

        int[] b = {0};
        moveZeroes(b);
        System.out.println(java.util.Arrays.toString(b));   // [0]

        int[] c = {1, 0};
        moveZeroes(c);
        System.out.println(java.util.Arrays.toString(c));   // [1, 0]

        int[] d = {2, 1};
        moveZeroes(d);
        System.out.println(java.util.Arrays.toString(d));   // [2, 1]
    }
}
