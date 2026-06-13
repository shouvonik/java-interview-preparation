package dsa.twopointers.leetcode;

/**
 * LeetCode 26 — Remove Duplicates from Sorted Array
 * Difficulty: Easy   Tags: Array, Two Pointers
 * URL: https://leetcode.com/problems/remove-duplicates-from-sorted-array/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} sorted in non-decreasing order, remove
 * the duplicates in-place such that each unique element appears only once.
 * Return the number of unique elements; the first k positions of {@code nums}
 * must hold the unique elements in their original order.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 3 * 10^4</li>
 *   <li>-100 &le; nums[i] &le; 100</li>
 *   <li>nums is sorted in non-decreasing order.</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1, 1, 2]              -> 2, nums = [1, 2, _]
 *   [0,0,1,1,1,2,2,3,3,4]  -> 5, nums = [0, 1, 2, 3, 4, _, _, _, _, _]
 *   [1]                     -> 1
 * </pre>
 *
 * <h2>Approach — read / write pointers</h2>
 * {@code write} marks the next position to fill in the deduped prefix.
 * {@code read} scans through the array. When {@code nums[read] != nums[read-1]}
 * (a fresh value), copy it to {@code nums[write++]}.
 *
 * <h2>Why this works on sorted input</h2>
 * Sorted means equal values are contiguous. The "is this a new unique value?"
 * test reduces to "is it different from the previous element?" — O(1) per check.
 *
 * <h2>Common bug</h2>
 * Starting both pointers at 0 and forgetting to handle the first element. Start
 * {@code write = 1} (the first element is automatically unique) and read from
 * index 1.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty array — return 0 (not in constraint, but handle defensively).</li>
 *   <li>Single element — return 1.</li>
 *   <li>All identical — return 1.</li>
 *   <li>All distinct — return n.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.twopointers.leetcode.LC0026_RemoveDuplicatesFromSortedArray
 * </pre>
 */
public class LC0026_RemoveDuplicatesFromSortedArray {

    /** Read / write pointers — O(n). */
    public static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int write = 1;
        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[read - 1]) nums[write++] = nums[read];
        }
        return write;
    }

    public static void main(String[] args) {
        int[] a = {1, 1, 2};
        int k = removeDuplicates(a);
        System.out.println(k + " " + java.util.Arrays.toString(java.util.Arrays.copyOf(a, k))); // 2 [1, 2]

        int[] b = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        k = removeDuplicates(b);
        System.out.println(k + " " + java.util.Arrays.toString(java.util.Arrays.copyOf(b, k))); // 5 [0, 1, 2, 3, 4]

        int[] c = {1};
        k = removeDuplicates(c);
        System.out.println(k + " " + java.util.Arrays.toString(java.util.Arrays.copyOf(c, k))); // 1 [1]
    }
}
