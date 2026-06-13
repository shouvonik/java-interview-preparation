package dsa.slidingwindow;

import java.util.*;

/**
 * Sliding Window — fixed, variable, and monotonic-deque patterns.
 *
 * Run: java dsa.slidingwindow.SlidingWindow
 */
public class SlidingWindow {

    // ---------------------------------------------------------------------
    // 1) Fixed-size window — max sum of any subarray of size k.
    // ---------------------------------------------------------------------
    // Real-life: rolling sum over the last k samples (e.g. avg latency, sales).
    static int maxSumFixed(int[] nums, int k) {
        int windowSum = 0;
        for (int i = 0; i < k; i++) windowSum += nums[i];
        int best = windowSum;
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right] - nums[right - k];
            best = Math.max(best, windowSum);
        }
        return best;
    }

    // ---------------------------------------------------------------------
    // 2) Variable-size window — longest substring without repeating chars.
    // ---------------------------------------------------------------------
    // Expand right; if the new char is already in the window, shrink from the
    // left until it's gone. Update `best` AFTER the while loop.
    static int longestUniqueSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int left = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            while (window.contains(c)) {
                window.remove(s.charAt(left++));
            }
            window.add(c);
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    // ---------------------------------------------------------------------
    // 3) Variable-size window — shortest subarray with sum >= target.
    // ---------------------------------------------------------------------
    // Expand right adding to sum; once sum >= target, shrink left while still
    // valid, updating `best` INSIDE the while loop because we want minimum.
    static int minLengthSubarraySum(int[] nums, int target) {
        int left = 0, sum = 0, best = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= target) {
                best = Math.min(best, right - left + 1);
                sum -= nums[left++];
            }
        }
        return best == Integer.MAX_VALUE ? 0 : best;
    }

    // ---------------------------------------------------------------------
    // 4) Monotonic deque — sliding window maximum (LeetCode 239).
    // ---------------------------------------------------------------------
    // Deque holds INDICES whose values are strictly decreasing from front to back.
    // - Front index has the max value of the current window.
    // - Pop from front if it has expired (index <= right - k).
    // - Pop from back while the back's value <= current value (it will never be max).
    static int[] maxInWindow(int[] nums, int k) {
        int n = nums.length;
        int[] out = new int[n - k + 1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (!dq.isEmpty() && dq.peekFirst() <= i - k) dq.pollFirst();
            while (!dq.isEmpty() && nums[dq.peekLast()] <= nums[i]) dq.pollLast();
            dq.offerLast(i);
            if (i >= k - 1) out[i - k + 1] = nums[dq.peekFirst()];
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        int[] samples = {1, 4, 2, 10, 2, 3, 1, 0, 20};
        System.out.println("maxSumFixed(k=3) = " + maxSumFixed(samples, 3));
        // 21  (window [1,0,20])

        System.out.println("longestUniqueSubstring(\"abcabcbb\") = "
            + longestUniqueSubstring("abcabcbb"));
        // 3  (abc)

        int[] arr = {2, 3, 1, 2, 4, 3};
        System.out.println("minLengthSubarraySum(target=7) = "
            + minLengthSubarraySum(arr, 7));
        // 2  ([4,3])

        int[] window = {1, 3, -1, -3, 5, 3, 6, 7};
        System.out.println("maxInWindow(k=3) = "
            + Arrays.toString(maxInWindow(window, 3)));
        // [3, 3, 5, 5, 6, 7]
    }
}
