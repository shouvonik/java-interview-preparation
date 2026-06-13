package dsa.arrays;

/**
 * Arrays — foundation patterns for coding interviews.
 *
 * Run: java dsa.arrays.ArraysGuide
 */
public class ArraysGuide {

    // ---------------------------------------------------------------------
    // 1) Prefix sums
    // ---------------------------------------------------------------------
    // prefix[i] = sum of nums[0..i-1]. Then sum(l, r) inclusive = prefix[r+1] - prefix[l].
    // Useful when you answer many range-sum queries on a static array.
    static int[] buildPrefix(int[] nums) {
        int[] prefix = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        return prefix;
    }

    static int rangeSum(int[] prefix, int l, int r) {
        return prefix[r + 1] - prefix[l];
    }

    // ---------------------------------------------------------------------
    // 2) Kadane's algorithm — max sum of a contiguous subarray.
    // ---------------------------------------------------------------------
    // Invariant: `best` = max subarray ending at i.
    // Either extend the previous best, or start fresh at nums[i].
    static int maxSubarray(int[] nums) {
        int best = nums[0];
        int current = nums[0];
        for (int i = 1; i < nums.length; i++) {
            current = Math.max(nums[i], current + nums[i]);
            best = Math.max(best, current);
        }
        return best;
    }

    // ---------------------------------------------------------------------
    // 3) Product except self — without division, O(1) extra space.
    // ---------------------------------------------------------------------
    // First pass: result[i] = product of everything LEFT of i.
    // Second pass: multiply in product of everything RIGHT of i, on the fly.
    static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= right;
            right *= nums[i];
        }
        return result;
    }

    // ---------------------------------------------------------------------
    // 4) In-place array rotation by k (right rotation).
    // ---------------------------------------------------------------------
    // Trick: reverse whole array, reverse first k, reverse last n-k.
    // [1,2,3,4,5,6,7], k=3 -> reverse all -> [7,6,5,4,3,2,1]
    //   -> reverse first 3 -> [5,6,7,4,3,2,1] -> reverse last 4 -> [5,6,7,1,2,3,4].
    static void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    static void reverse(int[] nums, int lo, int hi) {
        while (lo < hi) {
            int tmp = nums[lo];
            nums[lo++] = nums[hi];
            nums[hi--] = tmp;
        }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // Prefix sums
        int[] revenue = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] prefix = buildPrefix(revenue);
        System.out.println("Revenue days 2..5 inclusive = " + rangeSum(prefix, 2, 5));
        // 4 + 1 + 5 + 9 = 19

        // Kadane's
        int[] pnl = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Best streak P&L = " + maxSubarray(pnl));
        // Subarray [4,-1,2,1] sums to 6

        // Product except self
        int[] scores = {1, 2, 3, 4};
        int[] scaled = productExceptSelf(scores);
        System.out.println("Product except self = " + java.util.Arrays.toString(scaled));
        // [24, 12, 8, 6]

        // Rotate
        int[] logs = {1, 2, 3, 4, 5, 6, 7};
        rotate(logs, 3);
        System.out.println("Rotated by 3      = " + java.util.Arrays.toString(logs));
        // [5, 6, 7, 1, 2, 3, 4]
    }
}
