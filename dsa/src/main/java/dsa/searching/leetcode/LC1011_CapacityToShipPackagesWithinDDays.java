package dsa.searching.leetcode;

/**
 * LeetCode 1011 — Capacity To Ship Packages Within D Days
 * Difficulty: Medium   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
 *
 * <h2>Problem</h2>
 * A conveyor belt has packages with weights {@code weights[i]}. Packages must
 * be shipped in their given order. Each day, the ship is loaded with packages
 * whose total weight does not exceed the ship's capacity. Return the minimum
 * capacity that allows shipping all packages within {@code days} days.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; days &le; weights.length &le; 5 * 10^4</li>
 *   <li>1 &le; weights[i] &le; 500</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   weights = [1,2,3,4,5,6,7,8,9,10], days = 5  -> 15
 *     (one valid split: [1,2,3,4,5] [6,7] [8] [9] [10])
 *   weights = [3,2,2,4,1,4],          days = 3  -> 6
 *   weights = [1,2,3,1,1],            days = 4  -> 3
 * </pre>
 *
 * <h2>Approach — binary search on the answer</h2>
 * The capacity lives in {@code [max(weights), sum(weights)]}:
 * <ul>
 *   <li>Lower bound: at least the heaviest single package (one package per day
 *       still needs that).</li>
 *   <li>Upper bound: ship everything in one day at sum capacity.</li>
 * </ul>
 * For a candidate capacity {@code mid}, greedily simulate: pack each weight
 * into the current day if it fits, else start a new day. If days needed &le; D,
 * shrink {@code hi}; else grow {@code lo}.
 *
 * <h2>Why this differs from LC0410 Split Array Largest Sum</h2>
 * Almost identical structure: binary search on a capacity, greedy feasibility
 * check. The difference is framing — here we're minimising ship capacity given
 * a day budget; in LC0410 we're minimising the max sum given a piece count.
 * Same algorithm, dual phrasing.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>days == weights.length — answer is max(weights) (one package per day).</li>
 *   <li>days == 1 — answer is sum(weights).</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC1011_CapacityToShipPackagesWithinDDays
 * </pre>
 */
public class LC1011_CapacityToShipPackagesWithinDDays {

    /** Binary search on capacity — O(n log sum). */
    public static int shipWithinDays(int[] weights, int days) {
        int lo = 0, hi = 0;
        for (int w : weights) { lo = Math.max(lo, w); hi += w; }

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canShip(weights, mid, days)) hi = mid;
            else                              lo = mid + 1;
        }
        return lo;
    }

    /** Greedy: can we ship within `days` at the given capacity? */
    private static boolean canShip(int[] weights, int capacity, int days) {
        int needed = 1;
        int load = 0;
        for (int w : weights) {
            if (load + w > capacity) {
                needed++;
                load = 0;
            }
            load += w;
            if (needed > days) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(shipWithinDays(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, 5));   // 15
        System.out.println(shipWithinDays(new int[]{3, 2, 2, 4, 1, 4}, 3));                  // 6
        System.out.println(shipWithinDays(new int[]{1, 2, 3, 1, 1}, 4));                     // 3
        System.out.println(shipWithinDays(new int[]{5}, 1));                                  // 5
    }
}
