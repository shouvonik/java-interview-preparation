package dsa.searching.exercises;

/**
 * Searching — exercises.
 */
public class SearchingExercises {

    /**
     * Exercise 1 — First and last position of target (LeetCode 34).
     * Sorted array with duplicates. Return [first, last] index of target,
     * or [-1,-1] if absent. Use lowerBound twice.
     * Example: [5,7,7,8,8,10], target=8 -> [3,4]
     */
    static int[] searchRange(int[] nums, int target) {
        // TODO
        return new int[]{-1, -1};
    }

    /**
     * Exercise 2 — Find minimum in rotated sorted array (LeetCode 153).
     * No duplicates. Return the smallest element.
     * Example: [4,5,6,7,0,1,2] -> 0
     */
    static int findMin(int[] nums) {
        // TODO
        return -1;
    }

    /**
     * Exercise 3 — Capacity to ship packages within D days (LeetCode 1011).
     * Find the smallest ship capacity that allows shipping weights[] in days D,
     * preserving order. Each day's load <= capacity.
     * Example: weights=[1..10], D=5 -> 15
     */
    static int shipWithinDays(int[] weights, int days) {
        // TODO
        return -1;
    }

    /**
     * Exercise 4 — First bad version (LeetCode 278).
     * isBadVersion is monotone: once true, all later versions are bad.
     * Return the first bad version. (For local testing, simulate isBadVersion below.)
     */
    static int firstBadVersion(int n, int firstBad) {
        // TODO — binary search using the helper below as your oracle.
        return -1;
    }

    static boolean isBadVersion(int v, int firstBad) {
        return v >= firstBad;
    }

    public static void main(String[] args) {
        System.out.println("searchRange([5,7,7,8,8,10], 8) = "
            + java.util.Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        System.out.println("findMin([4,5,6,7,0,1,2]) = " + findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        System.out.println("shipWithinDays([1..10], 5) = "
            + shipWithinDays(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));
        System.out.println("firstBadVersion(n=10, firstBad=4) = " + firstBadVersion(10, 4));
    }
}
