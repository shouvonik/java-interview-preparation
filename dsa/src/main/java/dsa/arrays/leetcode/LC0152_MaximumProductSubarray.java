package dsa.arrays.leetcode;

/**
 * LeetCode 152 — Maximum Product Subarray
 * Difficulty: Medium   Tags: Array, Dynamic Programming
 * URL: https://leetcode.com/problems/maximum-product-subarray/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, find a contiguous non-empty subarray
 * with the largest product, and return that product. The answer is guaranteed
 * to fit in a 32-bit integer.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 2 * 10^4</li>
 *   <li>-10 &le; nums[i] &le; 10</li>
 *   <li>The product of any prefix or suffix is guaranteed to fit in 32 bits.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [2, 3, -2, 4]    -> 6     (subarray [2, 3])
 *   [-2, 0, -1]      -> 0     (best non-empty subarray is [0])
 *   [-2, 3, -4]      -> 24    ((-2) * 3 * (-4))
 *   [0, 2]           -> 2
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * Try every starting index, multiply forward, track the best. Slow.
 * <p>
 * <b>Track min &amp; max ending here (canonical)</b> — O(n) time, O(1) space.
 * Unlike sum-Kadane's, the max product ending at i can come from a negative
 * extending a previous negative min — a sign flip. So we track BOTH the max
 * and min product ending at the current index, and recompute both at every
 * step from {@code (nums[i], max * nums[i], min * nums[i])}.
 *
 * <h2>Why track min as well as max?</h2>
 * Multiplying a previously-tracked minimum (a very negative number) by a new
 * negative can produce a new maximum. Vanilla Kadane's would miss this. The
 * key insight: the optimal product ending at i is one of the three candidates
 * above, and they cover sign-flips automatically.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Zeros — they reset both min and max to {@code nums[i]} (which is 0
 *       times anything). Subarrays cleanly partition around zeros.</li>
 *   <li>Single element — return it (even if negative or zero).</li>
 *   <li>All negatives, odd count — best leaves out the smallest-magnitude negative.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0152_MaximumProductSubarray
 * </pre>
 */
public class LC0152_MaximumProductSubarray {

    /** Track running min and max — O(n) time, O(1) space. */
    public static int maxProduct(int[] nums) {
        int maxEnding = nums[0];
        int minEnding = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            int candMax = Math.max(x, Math.max(x * maxEnding, x * minEnding));
            int candMin = Math.min(x, Math.min(x * maxEnding, x * minEnding));
            maxEnding = candMax;
            minEnding = candMin;
            best = Math.max(best, maxEnding);
        }
        return best;
    }

    /** Brute force — O(n^2). */
    public static int maxProductBrute(int[] nums) {
        int best = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int product = 1;
            for (int j = i; j < nums.length; j++) {
                product *= nums[j];
                best = Math.max(best, product);
            }
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(maxProduct(new int[]{2, 3, -2, 4}));  // 6
        System.out.println(maxProduct(new int[]{-2, 0, -1}));    // 0
        System.out.println(maxProduct(new int[]{-2, 3, -4}));    // 24
        System.out.println(maxProduct(new int[]{0, 2}));         // 2
        System.out.println(maxProduct(new int[]{-2}));           // -2
    }
}
