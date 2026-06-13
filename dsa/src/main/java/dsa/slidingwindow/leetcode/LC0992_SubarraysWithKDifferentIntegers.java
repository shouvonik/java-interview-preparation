package dsa.slidingwindow.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 992 — Subarrays with K Different Integers
 * Difficulty: Hard   Tags: Array, Hash Table, Sliding Window, Counting
 * URL: https://leetcode.com/problems/subarrays-with-k-different-integers/
 *
 * <h2>Problem</h2>
 * Given an integer array {@code nums} and an integer {@code k}, return the
 * number of <b>good subarrays</b> of {@code nums}. A subarray is good if it
 * contains exactly {@code k} different integers.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; nums.length &le; 2 * 10^4</li>
 *   <li>1 &le; nums[i], k &le; nums.length</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   nums = [1,2,1,2,3], k = 2  -> 7
 *     ([1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2], [1,2,1,2])
 *   nums = [1,2,1,3,4], k = 3  -> 3
 *     ([1,2,1,3], [2,1,3], [1,3,4])
 * </pre>
 *
 * <h2>Approach — atMost(K) - atMost(K-1)</h2>
 * "Exactly K" is hard to slide directly because shrinking can both gain and
 * lose validity. The classic trick:
 * <p>
 * {@code exactly(K) = atMost(K) - atMost(K - 1)}
 * <p>
 * Both {@code atMost} calls are easy sliding windows: expand right; while
 * distinct count exceeds K, shrink left. After every right move, add
 * {@code right - left + 1} (the number of valid subarrays ending at right)
 * to the running total.
 *
 * <h2>Why "atMost" is sliding-friendly</h2>
 * Validity is monotone: once a window has at most K distinct, shrinking from
 * the left only removes distinct elements — never adds. So the window can
 * always be made smaller without losing validity, until the distinct count
 * goes above K (then we shrink to restore it).
 *
 * <h2>Why count {@code right - left + 1}</h2>
 * After settling left, every subarray ending exactly at right with start in
 * {@code [left..right]} is valid — that's {@code right - left + 1} subarrays.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>k == 1 — count of maximum-length runs of identical values.</li>
 *   <li>k &gt; distinct(nums) — 0.</li>
 *   <li>Single element with k == 1 — 1.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.slidingwindow.leetcode.LC0992_SubarraysWithKDifferentIntegers
 * </pre>
 */
public class LC0992_SubarraysWithKDifferentIntegers {

    /** exactly(K) = atMost(K) - atMost(K - 1). */
    public static int subarraysWithKDistinct(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    /** Count subarrays with at most K distinct integers — sliding window. */
    private static int atMost(int[] nums, int k) {
        if (k <= 0) return 0;
        Map<Integer, Integer> count = new HashMap<>();
        int left = 0, distinct = 0, total = 0;
        for (int right = 0; right < nums.length; right++) {
            int v = nums[right];
            if (count.merge(v, 1, Integer::sum) == 1) distinct++;
            while (distinct > k) {
                int lv = nums[left++];
                if (count.merge(lv, -1, Integer::sum) == 0) {
                    count.remove(lv);
                    distinct--;
                }
            }
            total += right - left + 1;                  // subarrays ending at right
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(subarraysWithKDistinct(new int[]{1, 2, 1, 2, 3}, 2));   // 7
        System.out.println(subarraysWithKDistinct(new int[]{1, 2, 1, 3, 4}, 3));   // 3
        System.out.println(subarraysWithKDistinct(new int[]{1, 1, 1, 1}, 1));      // 10
        System.out.println(subarraysWithKDistinct(new int[]{1}, 1));               // 1
    }
}
