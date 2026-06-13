package dsa.sorting.leetcode;

/**
 * LeetCode 75 — Sort Colors
 * Difficulty: Medium   Tags: Array, Two Pointers, Sorting
 * URL: https://leetcode.com/problems/sort-colors/
 *
 * <h2>Problem</h2>
 * Given an array {@code nums} with n objects coloured 0 (red), 1 (white), or
 * 2 (blue), sort them in-place so that objects of the same colour are adjacent
 * with the order red, white, blue. You must not use any standard sort.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>n == nums.length</li>
 *   <li>1 &le; n &le; 300</li>
 *   <li>nums[i] is 0, 1, or 2.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [2, 0, 2, 1, 1, 0]  ->  [0, 0, 1, 1, 2, 2]
 *   [2, 0, 1]           ->  [0, 1, 2]
 *   [0]                 ->  [0]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Counting sort</b> — O(n) time, two passes.
 * Count 0s, 1s, 2s; overwrite the array. Simple and works.
 * <p>
 * <b>Dutch National Flag (canonical)</b> — O(n) time, one pass.
 * Maintain three regions: {@code [0..lo)} = 0s, {@code [lo..mid)} = 1s,
 * {@code [mid..hi]} = unknown, {@code (hi..n)} = 2s. Walk {@code mid}:
 * <ul>
 *   <li>{@code nums[mid] == 0} -> swap with {@code nums[lo]}, advance lo and mid.</li>
 *   <li>{@code nums[mid] == 1} -> advance mid only.</li>
 *   <li>{@code nums[mid] == 2} -> swap with {@code nums[hi]}, decrement hi (don't advance mid; the swapped-in value is unprocessed).</li>
 * </ul>
 *
 * <h2>Why single-pass works</h2>
 * Every iteration either places a known-correct value (cases 0 and 1) or moves
 * a 2 to the back without examining it twice. Total work O(n).
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All same colour — algorithm still runs through; no swaps needed for 1s.</li>
 *   <li>Already sorted — early exit isn't needed; cost is still O(n).</li>
 *   <li>Empty array — handled by initial {@code lo &le; hi} guard.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0075_SortColors
 * </pre>
 */
public class LC0075_SortColors {

    /** Dutch flag — O(n) time, O(1) space, single pass. */
    public static void sortColors(int[] nums) {
        int lo = 0, mid = 0, hi = nums.length - 1;
        while (mid <= hi) {
            switch (nums[mid]) {
                case 0 -> { swap(nums, lo++, mid++); }
                case 1 -> mid++;
                case 2 -> swap(nums, mid, hi--);
                default -> throw new IllegalArgumentException("only 0/1/2 allowed");
            }
        }
    }

    /** Counting sort — O(n) time, two passes. */
    public static void sortColorsCounting(int[] nums) {
        int[] count = new int[3];
        for (int n : nums) count[n]++;
        int i = 0;
        for (int v = 0; v < 3; v++) {
            for (int c = 0; c < count[v]; c++) nums[i++] = v;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    public static void main(String[] args) {
        int[] a = {2, 0, 2, 1, 1, 0};
        sortColors(a);
        System.out.println(java.util.Arrays.toString(a));  // [0, 0, 1, 1, 2, 2]

        int[] b = {2, 0, 1};
        sortColors(b);
        System.out.println(java.util.Arrays.toString(b));  // [0, 1, 2]

        int[] c = {0};
        sortColors(c);
        System.out.println(java.util.Arrays.toString(c));  // [0]

        int[] d = {1, 1, 1, 2, 2, 0, 0};
        sortColors(d);
        System.out.println(java.util.Arrays.toString(d));  // [0, 0, 1, 1, 1, 2, 2]
    }
}
