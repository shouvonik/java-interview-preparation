package dsa.twopointers.leetcode;

/**
 * LeetCode 167 — Two Sum II - Input Array Is Sorted
 * Difficulty: Medium   Tags: Array, Two Pointers, Binary Search
 * URL: https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * <h2>Problem</h2>
 * Given a 1-indexed array of integers {@code numbers} sorted in non-decreasing
 * order, find two numbers such that they add up to a specific {@code target}.
 * Return their 1-based indices {@code [index1, index2]}.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>2 &le; numbers.length &le; 3 * 10^4</li>
 *   <li>-1000 &le; numbers[i] &le; 1000</li>
 *   <li>numbers is sorted non-decreasing.</li>
 *   <li>Exactly one solution exists.</li>
 *   <li>You may not use the same element twice.</li>
 *   <li>Your solution must use O(1) extra space.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   numbers = [2,7,11,15], target = 9   -> [1, 2]
 *   numbers = [2,3,4],     target = 6   -> [1, 3]
 *   numbers = [-1,0],      target = -1  -> [1, 2]
 * </pre>
 *
 * <h2>Approach — opposite-ends two pointers</h2>
 * Compare {@code numbers[left] + numbers[right]} to {@code target}:
 * <ul>
 *   <li>If equal — return.</li>
 *   <li>If less — increment left (we need a larger sum).</li>
 *   <li>If greater — decrement right.</li>
 * </ul>
 *
 * <h2>Why this beats LC 1 (Two Sum) hashmap approach</h2>
 * Both run in O(n). But this version uses O(1) extra space (required by the
 * constraint), while the HashMap version uses O(n).
 *
 * <h2>Why the monotone shrink works</h2>
 * If the current sum is too small, only increasing the smaller of the two
 * candidates can help; advancing right would shrink the sum (since
 * {@code numbers[right - 1] <= numbers[right]}). Symmetric argument for "too big".
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All negative numbers — same logic works; the comparisons hold.</li>
 *   <li>Two elements only — single check.</li>
 *   <li>Duplicates — sorted order means duplicates are adjacent; algorithm
 *       handles them naturally.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0167_TwoSumII
 * </pre>
 */
public class LC0167_TwoSumII {

    /** Opposite-ends — O(n) time, O(1) space. 1-indexed return per LeetCode convention. */
    public static int[] twoSum(int[] numbers, int target) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int sum = numbers[left] + numbers[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            if (sum < target) left++;
            else              right--;
        }
        throw new IllegalArgumentException("no solution");
    }

    public static void main(String[] args) {
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));   // [1, 2]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{2, 3, 4}, 6)));         // [1, 3]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{-1, 0}, -1)));          // [1, 2]
        System.out.println(java.util.Arrays.toString(twoSum(new int[]{5, 25, 75}, 100)));     // [2, 3]
    }
}
