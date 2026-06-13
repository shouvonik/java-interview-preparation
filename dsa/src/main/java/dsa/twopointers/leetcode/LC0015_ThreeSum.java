package dsa.twopointers.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 15 — 3Sum
 * Difficulty: Medium   Tags: Array, Two Pointers, Sorting
 * URL: https://leetcode.com/problems/3sum/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums}, return all the unique triplets
 * {@code [nums[i], nums[j], nums[k]]} such that {@code i != j, i != k, j != k}
 * and {@code nums[i] + nums[j] + nums[k] == 0}. The solution set must not
 * contain duplicate triplets.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>3 &le; nums.length &le; 3000</li>
 *   <li>-10^5 &le; nums[i] &le; 10^5</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [-1, 0, 1, 2, -1, -4]  ->  [[-1,-1,2], [-1,0,1]]
 *   [0, 1, 1]               ->  []
 *   [0, 0, 0]               ->  [[0,0,0]]
 * </pre>
 *
 * <h2>Approach — sort + outer loop + 2Sum two-pointer inner</h2>
 * <ol>
 *   <li>Sort the array. O(n log n).</li>
 *   <li>For each i (outer), use two pointers {@code left = i+1, right = n-1}
 *       to find pairs that sum to {@code -nums[i]}. O(n) per outer.</li>
 *   <li>Skip duplicates at both levels to avoid producing duplicate triplets.</li>
 * </ol>
 * Total: O(n^2).
 *
 * <h2>Duplicate handling</h2>
 * <ul>
 *   <li>Outer: if {@code i > 0 && nums[i] == nums[i-1]}, skip — the same triplet
 *       with this value as pivot was already produced.</li>
 *   <li>Inner: after recording a match, advance left while
 *       {@code nums[left] == nums[left-1]} and decrement right while
 *       {@code nums[right] == nums[right+1]}.</li>
 * </ul>
 *
 * <h2>Early termination</h2>
 * If {@code nums[i] > 0}, no sum of three sorted non-negative numbers can be
 * zero — we can break out of the outer loop. Speeds up runs with many positives.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All zeros — one triplet [0,0,0], regardless of count.</li>
 *   <li>No triple sums to 0 — empty list.</li>
 *   <li>Single zero present — fine, two-pointer logic still works.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0015_ThreeSum
 * </pre>
 */
public class LC0015_ThreeSum {

    /** Sort + 2-pointer inner — O(n^2). */
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) break;                              // no chance to sum to 0
            if (i > 0 && nums[i] == nums[i - 1]) continue;       // skip dup pivot
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    out.add(List.of(nums[i], nums[left], nums[right]));
                    left++; right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));   // [[-1,-1,2], [-1,0,1]]
        System.out.println(threeSum(new int[]{0, 1, 1}));                // []
        System.out.println(threeSum(new int[]{0, 0, 0}));                // [[0,0,0]]
        System.out.println(threeSum(new int[]{-2, 0, 1, 1, 2}));         // [[-2,0,2], [-2,1,1]]
    }
}
