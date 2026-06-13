package dsa.arrays.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 1 — Two Sum
 * Difficulty: Easy   Tags: Array, Hash Map
 * URL: https://leetcode.com/problems/two-sum/
 *
 * <h2>Problem</h2>
 * Given an array of integers {@code nums} and an integer {@code target}, return
 * the indices of the two numbers that add up to {@code target}.
 * <p>
 * You may assume that each input has <b>exactly one solution</b>, and you may
 * not use the same element twice. Return the answer in any order.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>2 &le; nums.length &le; 10^4</li>
 *   <li>-10^9 &le; nums[i] &le; 10^9</li>
 *   <li>-10^9 &le; target &le; 10^9</li>
 *   <li>Exactly one valid answer exists.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [2, 7, 11, 15], target = 9  ->  [0, 1]   (nums[0] + nums[1] == 9)
 *   nums = [3, 2, 4],      target = 6  ->  [1, 2]
 *   nums = [3, 3],         target = 6  ->  [0, 1]
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * For each i, scan all j &gt; i and check the sum. Simple but quadratic.
 * <p>
 * <b>Hash map (canonical)</b> — O(n) time, O(n) space.
 * For each element, compute {@code complement = target - nums[i]}. If we've
 * already seen the complement (stored in a map of value -&gt; index), we have
 * the answer. Otherwise, record nums[i] and continue.
 *
 * <h2>Edge cases worth thinking about</h2>
 * <ul>
 *   <li>Duplicate values (e.g. {@code [3,3], target=6}) — store the first
 *       occurrence; when we hit the second, complement lookup hits.</li>
 *   <li>Negative numbers — same logic; no sign assumptions.</li>
 *   <li>Target larger than any element — solution still exists by problem
 *       guarantee.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0001_TwoSum
 * </pre>
 */
public class LC0001_TwoSum {

    /** Canonical hash-map solution — O(n) time, O(n) space. */
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two-sum solution exists");
    }

    /** Brute force — kept for comparison; not the answer you give in an interview. */
    public static int[] twoSumBrute(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) return new int[]{i, j};
            }
        }
        throw new IllegalArgumentException("No two-sum solution exists");
    }

    public static void main(String[] args) {
        // Official examples
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));      // [0, 1]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{3, 2, 4}, 6)));           // [1, 2]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{3, 3}, 6)));              // [0, 1]

        // Edge cases
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{-1, -2, -3, -4, -5}, -8))); // [2, 4]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{0, 4, 3, 0}, 0)));          // [0, 3]
    }
}
