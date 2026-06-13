package dsa.searching.exercises.solutions;

/**
 * Reference solutions for SearchingExercises.
 */
public class SearchingSolutions {

    // Exercise 1 — Find lower bound of target and lower bound of target+1.
    // Second minus 1 gives the last occurrence.
    static int[] searchRange(int[] nums, int target) {
        int left = lowerBound(nums, target);
        if (left == nums.length || nums[left] != target) return new int[]{-1, -1};
        int right = lowerBound(nums, target + 1) - 1;
        return new int[]{left, right};
    }

    static int lowerBound(int[] nums, int target) {
        int lo = 0, hi = nums.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] < target) lo = mid + 1;
            else                    hi = mid;
        }
        return lo;
    }

    // Exercise 2 — Find min in rotated sorted array.
    // Compare nums[mid] with nums[hi]: if mid > hi, min is to the right; else, mid or left.
    static int findMin(int[] nums) {
        int lo = 0, hi = nums.length - 1;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] > nums[hi]) lo = mid + 1;
            else                      hi = mid;
        }
        return nums[lo];
    }

    // Exercise 3 — Ship within D days. BS on capacity in [max(weights), sum(weights)].
    static int shipWithinDays(int[] weights, int days) {
        int lo = 0, hi = 0;
        for (int w : weights) {
            lo = Math.max(lo, w);
            hi += w;
        }
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canShip(weights, mid, days)) hi = mid;
            else                             lo = mid + 1;
        }
        return lo;
    }

    static boolean canShip(int[] weights, int capacity, int days) {
        int needed = 1, load = 0;
        for (int w : weights) {
            if (load + w > capacity) { needed++; load = 0; }
            load += w;
            if (needed > days) return false;
        }
        return true;
    }

    // Exercise 4 — First bad version. Lower bound on isBadVersion.
    static int firstBadVersion(int n, int firstBad) {
        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (isBadVersion(mid, firstBad)) hi = mid;
            else                             lo = mid + 1;
        }
        return lo;
    }

    static boolean isBadVersion(int v, int firstBad) {
        return v >= firstBad;
    }

    public static void main(String[] args) {
        System.out.println("searchRange([5,7,7,8,8,10], 8) = "
            + java.util.Arrays.toString(searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8)));
        // [3, 4]

        System.out.println("findMin([4,5,6,7,0,1,2]) = " + findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        // 0

        System.out.println("shipWithinDays([1..10], 5) = "
            + shipWithinDays(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));
        // 15

        System.out.println("firstBadVersion(n=10, firstBad=4) = " + firstBadVersion(10, 4));
        // 4
    }
}
