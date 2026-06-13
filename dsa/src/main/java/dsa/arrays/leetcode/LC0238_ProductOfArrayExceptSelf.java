package dsa.arrays.leetcode;

/**
 * LeetCode 238 — Product of Array Except Self
 * Difficulty: Medium   Tags: Array, Prefix Sum
 * URL: https://leetcode.com/problems/product-of-array-except-self/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return an array {@code result} where
 * {@code result[i]} is the product of all elements of {@code nums} except
 * {@code nums[i]}. You <b>must not use division</b>, and the algorithm must
 * run in O(n) time. (Follow-up: solve in O(1) extra space, where the output
 * array does not count.)
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>2 &le; nums.length &le; 10^5</li>
 *   <li>-30 &le; nums[i] &le; 30</li>
 *   <li>The product of any prefix or suffix fits in a 32-bit integer.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1, 2, 3, 4]            -> [24, 12, 8, 6]
 *   [-1, 1, 0, -3, 3]       -> [0, 0, 9, 0, 0]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * For each i, multiply all others. Slow.
 * <p>
 * <b>Division shortcut</b> — O(n) time, O(1) space, but ruled out by the
 * problem and breaks on zeros.
 * <p>
 * <b>Prefix &amp; suffix passes (canonical)</b> — O(n) time, O(1) extra space.
 * First pass: {@code result[i] = product of elements LEFT of i}.
 * Second pass (right-to-left): multiply each {@code result[i]} by the running
 * product of elements RIGHT of i.
 *
 * <h2>Why prefix * suffix gives "product except self"</h2>
 * For any index i:
 * <pre>
 *   product of all except nums[i]
 *     = (product of nums[0..i-1]) * (product of nums[i+1..n-1])
 *     = prefix[i] * suffix[i]
 * </pre>
 * Storing prefix products in the output, then multiplying in suffix on the
 * fly, uses no extra array.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li><b>One zero in input</b> — only its index has the product of the
 *       other elements; all other positions are 0.</li>
 *   <li><b>Two or more zeros</b> — every position is 0.</li>
 *   <li><b>Negative numbers</b> — no special case; sign flows through naturally.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0238_ProductOfArrayExceptSelf
 * </pre>
 */
public class LC0238_ProductOfArrayExceptSelf {

    /** Prefix * suffix — O(n) time, O(1) extra space (output not counted). */
    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];

        // Pass 1: result[i] = product of elements to the LEFT of i.
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // Pass 2: multiply in the running product of elements to the RIGHT of i.
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= right;
            right *= nums[i];
        }
        return result;
    }

    /** Brute force — O(n^2). */
    public static int[] productExceptSelfBrute(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                product *= nums[j];
            }
            result[i] = product;
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(productExceptSelf(new int[]{1, 2, 3, 4})));
        // [24, 12, 8, 6]

        System.out.println(java.util.Arrays.toString(productExceptSelf(new int[]{-1, 1, 0, -3, 3})));
        // [0, 0, 9, 0, 0]

        System.out.println(java.util.Arrays.toString(productExceptSelf(new int[]{2, 3})));
        // [3, 2]

        System.out.println(java.util.Arrays.toString(productExceptSelf(new int[]{0, 0, 1})));
        // [0, 0, 0]
    }
}
