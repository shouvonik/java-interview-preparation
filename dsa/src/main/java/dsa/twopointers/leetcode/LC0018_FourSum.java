package dsa.twopointers.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 18 — 4Sum
 * Difficulty: Medium   Tags: Array, Two Pointers, Sorting
 * URL: https://leetcode.com/problems/4sum/
 *
 * <h2>Problem</h2>
 * Given an array {@code nums} of n integers and an integer {@code target},
 * return all unique quadruplets {@code [a, b, c, d]} such that:
 * {@code a + b + c + d == target}, and they come from four distinct indices.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 200</li>
 *   <li>-10^9 &le; nums[i], target &le; 10^9</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1,0,-1,0,-2,2], target = 0  -> [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]
 *   nums = [2,2,2,2,2],     target = 8  -> [[2,2,2,2]]
 * </pre>
 *
 * <h2>Approach — generalised k-Sum (here k = 4)</h2>
 * Sort, then two nested outer loops, then a two-pointer inner for the remaining
 * 2Sum. O(n^3). Duplicate skipping at all three levels (i, j, and inside the
 * two-pointer phase).
 *
 * <h2>Watch for overflow</h2>
 * With {@code nums[i]} up to {@code 10^9}, four values can sum to ~{@code 4 * 10^9},
 * which overflows int. Accumulate the sum into a {@code long}.
 *
 * <h2>Pruning ideas</h2>
 * <ul>
 *   <li>If {@code nums[i] * 4 > target} when nums[i] is the smallest remaining,
 *       no 4 numbers can reach the target — break.</li>
 *   <li>If {@code (long) nums[i] + nums[n-1] * 3 < target}, advance i.</li>
 * </ul>
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>n &lt; 4 — return empty.</li>
 *   <li>All identical — at most one quadruplet (when 4*v == target).</li>
 *   <li>Mix of large positives and negatives — long sum protects from overflow.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0018_FourSum
 * </pre>
 */
public class LC0018_FourSum {

    /** Sort + two outer loops + 2-pointer inner — O(n^3). */
    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> out = new ArrayList<>();
        if (nums.length < 4) return out;
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n - 3; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < n - 2; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int left = j + 1, right = n - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        out.add(List.of(nums[i], nums[j], nums[left], nums[right]));
                        left++; right--;
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum < target) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println(fourSum(new int[]{1, 0, -1, 0, -2, 2}, 0));
        // [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]

        System.out.println(fourSum(new int[]{2, 2, 2, 2, 2}, 8));
        // [[2,2,2,2]]

        System.out.println(fourSum(new int[]{1, 2, 3, 4}, 10));
        // [[1,2,3,4]]

        System.out.println(fourSum(new int[]{1000000000, 1000000000, 1000000000, 1000000000}, -294967296));
        // []  (overflow-safe check)
    }
}
