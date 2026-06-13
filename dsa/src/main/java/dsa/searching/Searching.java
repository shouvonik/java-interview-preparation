package dsa.searching;

/**
 * Searching — binary search templates and "BS on the answer" pattern.
 *
 * Run: java dsa.searching.Searching
 */
public class Searching {

    // ---------------------------------------------------------------------
    // 1) Classic binary search — closed range [lo, hi].
    // ---------------------------------------------------------------------
    static int binarySearch(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;     // overflow-safe
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) lo = mid + 1;
            else                   hi = mid - 1;
        }
        return -1;
    }

    // ---------------------------------------------------------------------
    // 2) Lower bound — first index i such that arr[i] >= target.
    //    Half-open range [lo, hi). Returns n if all elements are < target.
    // ---------------------------------------------------------------------
    static int lowerBound(int[] arr, int target) {
        int lo = 0, hi = arr.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] < target) lo = mid + 1;
            else                   hi = mid;
        }
        return lo;
    }

    // ---------------------------------------------------------------------
    // 3) Search in a rotated sorted array (no duplicates).
    // ---------------------------------------------------------------------
    // Each iteration, one half [lo..mid] or [mid..hi] is guaranteed sorted.
    // Check whether target lies in the sorted half; recurse on that, else the other.
    static int searchRotated(int[] arr, int target) {
        int lo = 0, hi = arr.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] == target) return mid;

            if (arr[lo] <= arr[mid]) {                                 // left half sorted
                if (arr[lo] <= target && target < arr[mid]) hi = mid - 1;
                else                                        lo = mid + 1;
            } else {                                                   // right half sorted
                if (arr[mid] < target && target <= arr[hi]) lo = mid + 1;
                else                                        hi = mid - 1;
            }
        }
        return -1;
    }

    // ---------------------------------------------------------------------
    // 4) Binary search on the answer — Koko eating bananas (LeetCode 875).
    // ---------------------------------------------------------------------
    // Koko has h hours and piles of bananas. Pick the smallest eating speed k
    // (bananas/hour) that lets her finish all piles within h hours.
    // - Answer is a single integer in [1, max(piles)].
    // - feasible(k): sum over piles of ceil(p/k) <= h. Monotone in k:
    //   if k works, every k' > k also works -> binary search for the smallest k.
    static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canFinish(piles, mid, h)) hi = mid;     // try smaller
            else                          lo = mid + 1; // need faster
        }
        return lo;
    }

    static boolean canFinish(int[] piles, int speed, int h) {
        long hours = 0;
        for (int p : piles) {
            hours += (p + speed - 1) / speed;          // integer ceil division
            if (hours > h) return false;
        }
        return true;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        int[] sorted = {1, 3, 5, 7, 9, 11, 13};
        System.out.println("binarySearch(7)  = " + binarySearch(sorted, 7));   // 3
        System.out.println("binarySearch(4)  = " + binarySearch(sorted, 4));   // -1

        System.out.println("lowerBound(6)    = " + lowerBound(sorted, 6));     // 3 (first >= 6 is 7@3)
        System.out.println("lowerBound(13)   = " + lowerBound(sorted, 13));    // 6
        System.out.println("lowerBound(14)   = " + lowerBound(sorted, 14));    // 7 (== n, none >=)

        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("searchRotated(0) = " + searchRotated(rotated, 0)); // 4
        System.out.println("searchRotated(3) = " + searchRotated(rotated, 3)); // -1

        int[] piles = {3, 6, 7, 11};
        System.out.println("minEatingSpeed (h=8) = " + minEatingSpeed(piles, 8)); // 4
    }
}
