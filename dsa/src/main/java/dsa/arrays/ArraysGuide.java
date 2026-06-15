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
    // Idea: precompute cumulative sums once, then answer any range-sum query
    // in O(1) by subtracting two of them.
    //
    // prefix[i] = sum of nums[0..i-1]  (prefix[0] = 0 is the empty prefix).
    // The extra leading 0 means prefix has length n+1, which removes the need
    // for a special case when the range starts at index 0.
    //
    // Then sum(l, r) inclusive = prefix[r+1] - prefix[l].
    //   Everything up to r, minus everything before l, leaves exactly l..r.
    //
    // Cost: O(n) one-time build, O(1) per query, O(n) extra space.
    // Use it when the array is static (no updates) and you have MANY queries —
    // if the array changes between queries, reach for a Fenwick/segment tree.
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
    // Idea: scan left to right keeping the best sum of a subarray that ENDS at
    // the current index. At each step you face a single binary choice:
    //   - extend the running subarray:  current + nums[i]
    //   - or cut your losses and start fresh at nums[i].
    // You start fresh exactly when the running sum has gone negative, because a
    // negative prefix can only drag down whatever comes next.
    //
    // `current` = best sum ending at i;  `best` = best seen anywhere so far.
    // We seed both with nums[0] so the answer is correct even when every number
    // is negative (the answer is then the single largest element).
    //
    // Cost: O(n) time, O(1) space, single pass.
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
    // Goal: result[i] = product of every element EXCEPT nums[i].
    // The naive answer is (product of all) / nums[i], but division is banned
    // (and breaks on zeros). The trick: product-except-i is just
    //   (product of everything to the LEFT of i) * (product to the RIGHT of i).
    //
    // First pass (left -> right): result[i] holds the left product.
    // Second pass (right -> left): keep a running `right` product and fold it
    // in as we go, so we never need a separate suffix array.
    //
    // We don't count the output array as extra space (it's the required result),
    // so this is O(1) auxiliary space. Cost: O(n) time, two passes.
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
    // Goal: shift every element k places to the right, wrapping around, using
    // no second array.
    //
    // First, k %= n: rotating by n lands back where you started, so only the
    // remainder matters (and it guards against k > n).
    //
    // Trick: three reversals. Reversing the whole array puts the last k elements
    // at the front but in the wrong order; reversing each of the two pieces
    // (first k, last n-k) fixes their internal order.
    //   [1,2,3,4,5,6,7], k=3
    //     reverse all      -> [7,6,5,4,3,2,1]
    //     reverse first 3  -> [5,6,7,4,3,2,1]
    //     reverse last 4   -> [5,6,7,1,2,3,4]
    //
    // Cost: O(n) time, O(1) space — each element is touched a constant number
    // of times.
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
