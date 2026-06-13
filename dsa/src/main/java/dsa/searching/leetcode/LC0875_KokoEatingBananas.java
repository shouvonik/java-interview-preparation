package dsa.searching.leetcode;

/**
 * LeetCode 875 — Koko Eating Bananas
 * Difficulty: Medium   Tags: Array, Binary Search
 * URL: https://leetcode.com/problems/koko-eating-bananas/
 *
 * <h2>Problem</h2>
 * Koko has {@code n} piles of bananas with sizes {@code piles[i]} and {@code h}
 * hours before guards return. Each hour she picks a pile and eats up to {@code k}
 * bananas (eating fewer than k from a pile finishes that pile and ends the hour
 * — she can't switch piles mid-hour). Find the minimum integer eating speed
 * {@code k} that lets her finish all the bananas within h hours.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; piles.length &le; 10^4</li>
 *   <li>piles.length &le; h &le; 10^9</li>
 *   <li>1 &le; piles[i] &le; 10^9</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   piles = [3, 6, 7, 11], h = 8   -> 4
 *   piles = [30, 11, 23, 4, 20], h = 5  -> 30
 *   piles = [30, 11, 23, 4, 20], h = 6  -> 23
 * </pre>
 *
 * <h2>Approach — binary search on the answer</h2>
 * The answer is an integer in {@code [1, max(piles)]}. The predicate
 * {@code canFinish(k)} = "can Koko clear all piles in &le; h hours at speed k"
 * is <b>monotone</b>: if speed k works, every speed k' &gt; k also works. So
 * we binary-search for the smallest k that satisfies the predicate.
 *
 * <h2>Time per pile at speed k</h2>
 * For a pile of size p, hours needed = {@code ceil(p / k)} = {@code (p + k - 1) / k}
 * using integer arithmetic. Summing these over all piles gives total hours.
 *
 * <h2>Why max(piles) is the upper bound</h2>
 * At speed {@code max(piles)}, every pile finishes in exactly 1 hour, totalling
 * {@code piles.length} hours &le; h (guaranteed by the constraint). So the
 * answer is never larger than {@code max(piles)}.
 *
 * <h2>Watch out for overflow</h2>
 * Total hours can exceed {@code Integer.MAX_VALUE} when many large piles meet
 * a tiny speed. Accumulate in a {@code long}.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0875_KokoEatingBananas
 * </pre>
 */
public class LC0875_KokoEatingBananas {

    /** Binary search on speed — O(n log max(piles)). */
    public static int minEatingSpeed(int[] piles, int h) {
        int lo = 1, hi = 0;
        for (int p : piles) hi = Math.max(hi, p);

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (canFinish(piles, mid, h)) hi = mid;
            else                          lo = mid + 1;
        }
        return lo;
    }

    private static boolean canFinish(int[] piles, int speed, int h) {
        long hours = 0;
        for (int p : piles) {
            hours += (p + speed - 1) / speed;            // ceil(p/speed)
            if (hours > h) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(minEatingSpeed(new int[]{3, 6, 7, 11}, 8));            // 4
        System.out.println(minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 5));      // 30
        System.out.println(minEatingSpeed(new int[]{30, 11, 23, 4, 20}, 6));      // 23
        System.out.println(minEatingSpeed(new int[]{1}, 1));                       // 1
    }
}
