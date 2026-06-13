package dsa.searching.leetcode;

/**
 * LeetCode 410 — Split Array Largest Sum
 * Difficulty: Hard   Tags: Array, Binary Search, Greedy, Dynamic Programming
 * URL: https://leetcode.com/problems/split-array-largest-sum/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} of non-negative integers and an integer
 * {@code k}, split the array into {@code k} non-empty contiguous subarrays so
 * that the largest sum among these subarrays is minimised. Return that minimum.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 1000</li>
 *   <li>0 &le; nums[i] &le; 10^6</li>
 *   <li>1 &le; k &le; min(50, nums.length)</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [7, 2, 5, 10, 8], k = 2  -> 18   (split: [7,2,5] | [10,8])
 *   nums = [1, 2, 3, 4, 5], k = 2   -> 9    (split: [1,2,3] | [4,5])
 *   nums = [1, 4, 4],       k = 3   -> 4    (split: [1] | [4] | [4])
 * </pre>
 *
 * <h2>Approach — binary search on the answer</h2>
 * The answer is a value in {@code [max(nums), sum(nums)]}:
 * <ul>
 *   <li>Lower bound: the maximum single element — every subarray must contain
 *       at least one number, and that number's value is a lower bound on the
 *       max subarray sum.</li>
 *   <li>Upper bound: the total sum (k = 1 case).</li>
 * </ul>
 * For a candidate cap {@code mid}, ask: "Can we greedily slice the array
 * into at most k chunks each summing &le; mid?" If yes, try a smaller cap; else
 * raise it. Binary search converges in O(n log(sum)) time.
 *
 * <h2>Why the feasibility check is greedy</h2>
 * Once the cap is fixed, growing a piece as much as possible before starting
 * a new one minimises piece count. Any non-greedy split would also need &ge; the
 * greedy count of pieces.
 *
 * <h2>Why DP is overkill here</h2>
 * The classic DP {@code dp[i][j]} = "min of max-sum splitting first i elements
 * into j parts" works in O(n^2 k) but is dominated by binary search on the
 * answer ({@code O(n log sum)}) for the given constraints.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>{@code k == nums.length} — each element is its own group; answer = max.</li>
 *   <li>{@code k == 1} — answer = sum.</li>
 *   <li>Single element — answer = that element.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0410_SplitArrayLargestSum
 * </pre>
 */
public class LC0410_SplitArrayLargestSum {

    /** Binary search on the answer — O(n log sum). */
    public static int splitArray(int[] nums, int k) {
        int lo = 0, hi = 0;
        for (int n : nums) { lo = Math.max(lo, n); hi += n; }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canSplit(nums, mid, k)) hi = mid;       // mid works, try smaller
            else                        lo = mid + 1;   // need larger cap
        }
        return lo;
    }

    /** Greedy feasibility: can we split into at most k chunks, each &le; cap? */
    private static boolean canSplit(int[] nums, int cap, int k) {
        int pieces = 1, sum = 0;
        for (int n : nums) {
            if (sum + n > cap) {
                pieces++;
                sum = 0;
            }
            sum += n;
            if (pieces > k) return false;                // can short-circuit
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(splitArray(new int[]{7, 2, 5, 10, 8}, 2));    // 18
        System.out.println(splitArray(new int[]{1, 2, 3, 4, 5}, 2));     // 9
        System.out.println(splitArray(new int[]{1, 4, 4}, 3));           // 4
        System.out.println(splitArray(new int[]{1, 2, 3, 4, 5}, 5));     // 5  (each its own)
        System.out.println(splitArray(new int[]{10}, 1));                 // 10
    }
}
