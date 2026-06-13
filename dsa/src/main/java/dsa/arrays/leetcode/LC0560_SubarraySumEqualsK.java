package dsa.arrays.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 560 — Subarray Sum Equals K
 * Difficulty: Medium   Tags: Array, Hash Map, Prefix Sum
 * URL: https://leetcode.com/problems/subarray-sum-equals-k/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} and an integer {@code k}, return the
 * number of contiguous non-empty subarrays whose sum equals {@code k}.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 2 * 10^4</li>
 *   <li>-1000 &le; nums[i] &le; 1000</li>
 *   <li>-10^7 &le; k &le; 10^7</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1, 1, 1],  k = 2  ->  2   ([1,1] starting at 0; [1,1] starting at 1)
 *   nums = [1, 2, 3],  k = 3  ->  2   ([1,2] and [3])
 *   nums = [1],        k = 0  ->  0
 *   nums = [-1, -1, 1], k = 0 ->  1   ([-1, -1, 1, ... no wait] just [-1, 1])
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>Brute force</b> — O(n^2) time, O(1) space.
 * For each start, expand and check sum. Quadratic.
 * <p>
 * <b>Prefix sum + hash map (canonical)</b> — O(n) time, O(n) space.
 * Let {@code prefix[i] = nums[0] + ... + nums[i-1]}. A subarray {@code nums[l..r]}
 * has sum {@code prefix[r+1] - prefix[l]}. We want this to equal k, so for each
 * running prefix we count how many previous prefixes equal {@code prefix - k}.
 * Maintain a frequency map of prefixes seen so far.
 *
 * <h2>Why a hash of previous prefixes?</h2>
 * The number of subarrays ending at position r with sum k is exactly the number
 * of previous prefixes equal to {@code currentPrefix - k}. The map lets us answer
 * that in O(1) per step.
 *
 * <h2>Why not sliding window?</h2>
 * Sliding window relies on the property "expanding/shrinking the window changes
 * the sum monotonically". With negative numbers in the array, that property
 * breaks — adding more elements can decrease the sum. Hence prefix-sum + hash
 * is required.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Prefix-sum map MUST be seeded with {@code (0, 1)} for "empty prefix",
 *       otherwise subarrays starting at index 0 aren't counted.</li>
 *   <li>{@code k == 0} — counts subarrays of sum zero (still works).</li>
 *   <li>All negatives — same algorithm, no special case.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0560_SubarraySumEqualsK
 * </pre>
 */
public class LC0560_SubarraySumEqualsK {

    /** Prefix-sum + hash map — O(n) time, O(n) space. */
    public static int subarraySum(int[] nums, int k) {
        Map<Integer, Integer> seen = new HashMap<>();
        seen.put(0, 1);                                // empty prefix
        int prefix = 0;
        int count = 0;
        for (int n : nums) {
            prefix += n;
            count += seen.getOrDefault(prefix - k, 0);
            seen.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    /** Brute force — O(n^2). */
    public static int subarraySumBrute(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println(subarraySum(new int[]{1, 1, 1}, 2));     // 2
        System.out.println(subarraySum(new int[]{1, 2, 3}, 3));     // 2
        System.out.println(subarraySum(new int[]{1}, 0));           // 0
        System.out.println(subarraySum(new int[]{-1, -1, 1}, 0));   // 1
        System.out.println(subarraySum(new int[]{3, 4, 7, 2, -3, 1, 4, 2}, 7)); // 4
    }
}
