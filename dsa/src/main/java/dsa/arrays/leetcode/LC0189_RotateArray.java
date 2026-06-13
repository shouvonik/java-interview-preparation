package dsa.arrays.leetcode;

/**
 * LeetCode 189 — Rotate Array
 * Difficulty: Medium   Tags: Array, Math, Two Pointers
 * URL: https://leetcode.com/problems/rotate-array/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, rotate the array to the right by k steps,
 * where k is non-negative. Do it in-place.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-2^31 &le; nums[i] &le; 2^31 - 1</li>
 *   <li>0 &le; k &le; 10^5</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1, 2, 3, 4, 5, 6, 7], k = 3  ->  [5, 6, 7, 1, 2, 3, 4]
 *   nums = [-1, -100, 3, 99],    k = 2  ->  [3, 99, -1, -100]
 *   k larger than length: k %= n first.
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Extra array</b> — O(n) time, O(n) space.
 * Build a new array placing each element at {@code (i + k) % n}.
 * Simple, but doesn't satisfy the in-place requirement.
 * <p>
 * <b>Three-reverse trick (canonical)</b> — O(n) time, O(1) space.
 * Reverse the whole array, then reverse the first k elements, then reverse the
 * remaining {@code n - k}. The combined effect is a right rotation by k.
 * <p>
 * <b>Cyclic replacements</b> — O(n) time, O(1) space.
 * Walk cycles of indices using {@code (i + k) % n}; harder to implement, same
 * complexity, less commonly asked. Skipping in favour of the reverse trick.
 *
 * <h2>Why three reversals work</h2>
 * <pre>
 *   [1,2,3,4,5,6,7], k = 3
 *   Reverse all       ->  [7,6,5,4,3,2,1]
 *   Reverse first 3   ->  [5,6,7,4,3,2,1]
 *   Reverse last 4    ->  [5,6,7,1,2,3,4]
 * </pre>
 * Each operation moves a block of elements into the right region while keeping
 * the order within blocks correct.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>{@code k >= n} — always reduce {@code k %= n} first.</li>
 *   <li>{@code k == 0} or array length 1 — no-op.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0189_RotateArray
 * </pre>
 */
public class LC0189_RotateArray {

    /** Three-reverse trick — O(n) time, O(1) space. */
    public static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int lo, int hi) {
        while (lo < hi) {
            int tmp = nums[lo];
            nums[lo++] = nums[hi];
            nums[hi--] = tmp;
        }
    }

    /** Extra-array variant — O(n) time, O(n) space. */
    public static void rotateExtra(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        int[] out = new int[n];
        for (int i = 0; i < n; i++) out[(i + k) % n] = nums[i];
        System.arraycopy(out, 0, nums, 0, n);
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        rotate(a, 3);
        System.out.println(java.util.Arrays.toString(a));  // [5, 6, 7, 1, 2, 3, 4]

        int[] b = {-1, -100, 3, 99};
        rotate(b, 2);
        System.out.println(java.util.Arrays.toString(b));  // [3, 99, -1, -100]

        int[] c = {1, 2};
        rotate(c, 3);                                       // k=3 % n=2 -> 1
        System.out.println(java.util.Arrays.toString(c));  // [2, 1]
    }
}
