package dsa.slidingwindow.leetcode;

/**
 * LeetCode 1004 — Max Consecutive Ones III
 * Difficulty: Medium   Tags: Array, Binary Search, Sliding Window, Prefix Sum
 * URL: https://leetcode.com/problems/max-consecutive-ones-iii/
 *
 * <h2>Problem</h2>
 * Given a binary array {@code nums} and an integer {@code k}, return the
 * maximum number of consecutive 1's in the array if you can flip at most
 * {@code k} 0's.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 10^5</li>
 *   <li>nums[i] is 0 or 1.</li>
 *   <li>0 &le; k &le; nums.length</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2  -> 6
 *     (flip two 0's at positions 3 and 4 -> 6 consecutive 1's)
 *   nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], k = 3  -> 10
 * </pre>
 *
 * <h2>Approach — variable window with zero count</h2>
 * Maintain a window {@code [left..right]} and the count of zeros inside it.
 * Expand right; if {@code zeros > k}, shrink left until {@code zeros <= k}.
 * The best window length seen is the answer.
 *
 * <h2>Why this works</h2>
 * The window represents "candidate run of 1's after flipping". It's valid iff
 * it contains at most k zeros (those we can flip). The variable-window pattern
 * gives the maximum valid window in O(n).
 *
 * <h2>Identical to "longest 1's after &le; k flips" framing</h2>
 * Same as: "longest subarray with at most k zeros". Variants of this question
 * appear under many names — once you spot the "at most k of something" shape,
 * the sliding window pattern follows.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == 0 — longest run of existing 1's.</li>
 *   <li>k &ge; count of 0's — answer is nums.length.</li>
 *   <li>All zeros, k == 0 — 0.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC1004_MaxConsecutiveOnesIII
 * </pre>
 */
public class LC1004_MaxConsecutiveOnesIII {

    /** Variable window with zero count — O(n). */
    public static int longestOnes(int[] nums, int k) {
        int left = 0, zeros = 0, best = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeros++;
            while (zeros > k) {
                if (nums[left] == 0) zeros--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    public static void main(String[] args) {
        System.out.println(longestOnes(new int[]{1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0}, 2));   // 6
        System.out.println(longestOnes(new int[]{0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 3)); // 10
        System.out.println(longestOnes(new int[]{0, 0, 0}, 0));                            // 0
        System.out.println(longestOnes(new int[]{1, 1, 1}, 0));                            // 3
    }
}
