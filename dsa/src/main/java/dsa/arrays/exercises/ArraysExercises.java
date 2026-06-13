package dsa.arrays.exercises;

/**
 * Arrays — exercises. Try without looking at the solutions first.
 * After 20 minutes of stuck, peek at dsa/arrays/exercises/solutions/ArraysSolutions.java.
 */
public class ArraysExercises {

    /**
     * Exercise 1 — Best time to buy and sell stock (LeetCode 121).
     * Given daily prices, return the maximum profit from a single buy + sell.
     * Constraint: buy before you sell. If no profit possible, return 0.
     * Example: [7,1,5,3,6,4] -> 5  (buy at 1, sell at 6)
     */
    static int maxProfit(int[] prices) {
        // TODO
        return 0;
    }

    /**
     * Exercise 2 — Subarray Sum Equals K (LeetCode 560).
     * Return the number of contiguous subarrays whose sum equals k.
     * Hint: prefix sum + hash map of previously-seen prefix counts.
     * Example: nums=[1,1,1], k=2 -> 2
     */
    static int subarraySumEqualsK(int[] nums, int k) {
        // TODO
        return 0;
    }

    /**
     * Exercise 3 — Maximum product subarray (LeetCode 152).
     * Like Kadane's but track BOTH min and max ending here
     * (because a negative * min-so-far can become the new max).
     * Example: [2,3,-2,4] -> 6 (subarray [2,3])
     */
    static int maxProduct(int[] nums) {
        // TODO
        return 0;
    }

    /**
     * Exercise 4 — Move zeroes (LeetCode 283).
     * Move all 0s to the end in-place, preserving the order of non-zero elements.
     * Example: [0,1,0,3,12] -> [1,3,12,0,0]
     */
    static void moveZeroes(int[] nums) {
        // TODO
    }

    public static void main(String[] args) {
        System.out.println("maxProfit([7,1,5,3,6,4]) = " + maxProfit(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println("subarraySumEqualsK([1,1,1], 2) = " + subarraySumEqualsK(new int[]{1, 1, 1}, 2));
        System.out.println("maxProduct([2,3,-2,4]) = " + maxProduct(new int[]{2, 3, -2, 4}));

        int[] zeroes = {0, 1, 0, 3, 12};
        moveZeroes(zeroes);
        System.out.println("moveZeroes -> " + java.util.Arrays.toString(zeroes));
    }
}
