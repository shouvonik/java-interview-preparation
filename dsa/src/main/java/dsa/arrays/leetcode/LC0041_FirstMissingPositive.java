package dsa.arrays.leetcode;

/**
 * LeetCode 41 — First Missing Positive
 * Difficulty: Hard   Tags: Array, Hash Table, Cyclic Sort
 * URL: https://leetcode.com/problems/first-missing-positive/
 *
 * <h2>Problem</h2>
 * Given an unsorted array {@code nums}, return the smallest positive integer
 * that is not present in the array. The solution must run in O(n) time and
 * use O(1) extra space.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>-2^31 &le; nums[i] &le; 2^31 - 1</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [1, 2, 0]       -> 3
 *   [3, 4, -1, 1]   -> 2
 *   [7, 8, 9, 11, 12] -> 1
 *   [1]             -> 2
 * </pre>
 *
 * <h2>Approaches</h2>
 * <b>HashSet</b> — O(n) time, O(n) space.
 * Put all positives in a set, then scan i = 1, 2, ... until missing.
 * Violates the O(1) extra-space requirement.
 * <p>
 * <b>Cyclic sort (canonical)</b> — O(n) time, O(1) space.
 * Observation: the answer is in {@code [1, n+1]} (since with n slots and the
 * answer being positive, the worst case is {@code [1, 2, ..., n]} -> answer
 * {@code n+1}). So values outside that range don't matter — we can use the
 * array itself as a hash table by placing each value v at index v-1.
 * After the placement pass, the first index i where {@code nums[i] != i+1}
 * gives the answer {@code i+1}.
 *
 * <h2>Why cyclic sort works</h2>
 * Each swap puts at least one value in its final position (or discards an
 * out-of-range value). Since there are n positions, total swaps across the
 * whole array are bounded by n — hence O(n) despite the inner while loop.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>All present {@code [1..n]} — answer is {@code n+1}.</li>
 *   <li>No positives at all — answer is {@code 1}.</li>
 *   <li>Duplicates — second occurrence is out of place and stays; algorithm
 *       avoids infinite swaps by checking {@code nums[nums[i]-1] != nums[i]}.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.arrays.leetcode.LC0041_FirstMissingPositive
 * </pre>
 */
public class LC0041_FirstMissingPositive {

    /** Cyclic sort — O(n) time, O(1) space. */
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // Park nums[i] at its correct slot if possible, otherwise leave it.
            while (nums[i] >= 1 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int j = nums[i] - 1;
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) return i + 1;
        }
        return n + 1;
    }

    /** HashSet variant — O(n) time, O(n) space. Kept for comparison. */
    public static int firstMissingPositiveSet(int[] nums) {
        java.util.Set<Integer> positives = new java.util.HashSet<>();
        for (int v : nums) if (v > 0) positives.add(v);
        for (int i = 1; i <= nums.length + 1; i++) {
            if (!positives.contains(i)) return i;
        }
        throw new IllegalStateException("unreachable");
    }

    public static void main(String[] args) {
        System.out.println(firstMissingPositive(new int[]{1, 2, 0}));            // 3
        System.out.println(firstMissingPositive(new int[]{3, 4, -1, 1}));        // 2
        System.out.println(firstMissingPositive(new int[]{7, 8, 9, 11, 12}));    // 1
        System.out.println(firstMissingPositive(new int[]{1}));                  // 2
        System.out.println(firstMissingPositive(new int[]{1, 1}));               // 2
    }
}
