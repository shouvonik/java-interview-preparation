package dsa.arrays.exercises.solutions;

/**
 * Reference solutions for ArraysExercises.
 */
public class ArraysSolutions {

    // Exercise 1 — Best time to buy and sell stock.
    // Track the minimum price seen so far; at each day, see how much we'd make
    // selling today. Keep the best of those.
    static int maxProfit(int[] prices) {
        int minSoFar = Integer.MAX_VALUE;
        int best = 0;
        for (int p : prices) {
            minSoFar = Math.min(minSoFar, p);
            best = Math.max(best, p - minSoFar);
        }
        return best;
    }

    // Exercise 2 — Subarray sum equals k.
    // sum(i..j) = prefix[j+1] - prefix[i]. We want this == k, so for each
    // prefix we count how many previous prefixes equal (currentPrefix - k).
    static int subarraySumEqualsK(int[] nums, int k) {
        java.util.Map<Integer, Integer> seen = new java.util.HashMap<>();
        seen.put(0, 1); // empty prefix
        int prefix = 0;
        int count = 0;
        for (int n : nums) {
            prefix += n;
            count += seen.getOrDefault(prefix - k, 0);
            seen.merge(prefix, 1, Integer::sum);
        }
        return count;
    }

    // Exercise 3 — Maximum product subarray.
    // Track both running max and min ending at i. A negative number flips them.
    static int maxProduct(int[] nums) {
        int maxEnding = nums[0];
        int minEnding = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int x = nums[i];
            int candMax = Math.max(x, Math.max(x * maxEnding, x * minEnding));
            int candMin = Math.min(x, Math.min(x * maxEnding, x * minEnding));
            maxEnding = candMax;
            minEnding = candMin;
            best = Math.max(best, maxEnding);
        }
        return best;
    }

    // Exercise 4 — Move zeroes in-place.
    // Two-pointer write index: walk through, whenever we see a non-zero
    // copy it to write++, then zero-fill the tail.
    static void moveZeroes(int[] nums) {
        int write = 0;
        for (int n : nums) {
            if (n != 0) nums[write++] = n;
        }
        while (write < nums.length) nums[write++] = 0;
    }

    public static void main(String[] args) {
        System.out.println("maxProfit([7,1,5,3,6,4]) = " + maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        // 5

        System.out.println("subarraySumEqualsK([1,1,1], 2) = " + subarraySumEqualsK(new int[]{1, 1, 1}, 2));
        // 2

        System.out.println("maxProduct([2,3,-2,4]) = " + maxProduct(new int[]{2, 3, -2, 4}));
        // 6

        int[] zeroes = {0, 1, 0, 3, 12};
        moveZeroes(zeroes);
        System.out.println("moveZeroes -> " + java.util.Arrays.toString(zeroes));
        // [1, 3, 12, 0, 0]
    }
}
