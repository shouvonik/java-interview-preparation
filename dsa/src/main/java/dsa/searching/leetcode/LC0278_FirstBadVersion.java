package dsa.searching.leetcode;

import java.util.function.IntPredicate;

/**
 * LeetCode 278 — First Bad Version
 * Difficulty: Easy   Tags: Binary Search, Interactive
 * URL: https://leetcode.com/problems/first-bad-version/
 *
 * <h2>Problem</h2>
 * You are a product manager whose latest release is failing. Among {@code n}
 * sequential versions (1..n), one was bad — and every version after it is
 * also bad. You are given an API {@code isBadVersion(version)} that returns
 * whether a given version is bad. Find the first bad version, minimising calls
 * to the API.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; bad &le; n &le; 2^31 - 1</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   n = 5, bad = 4  ->  4    (isBad(3)=false, isBad(4)=true)
 *   n = 1, bad = 1  ->  1
 *   n = 10, bad = 7 ->  7
 * </pre>
 *
 * <h2>Approach</h2>
 * Classic "lower bound on a monotone predicate". The function {@code isBad}
 * is false then true (non-decreasing), so the first true is found by binary
 * search: shrink hi when the mid is bad (or might be the first), and grow lo
 * when the mid is still good.
 *
 * <h2>Why this minimises API calls</h2>
 * Each iteration halves the search space, so we call the API O(log n) times.
 * For {@code n = 2^31}, that's at most 31 calls.
 *
 * <h2>Beware of overflow</h2>
 * For {@code n = 2^31 - 1}, computing {@code (lo + hi) / 2} overflows. Always
 * use {@code lo + (hi - lo) / 2}.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>n = 1 — only version 1; if bad, return 1.</li>
 *   <li>Bad version is the last one — converges to n via repeated lo bumps.</li>
 *   <li>Bad version is the first one — converges to 1 via repeated hi shrinks.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.searching.leetcode.LC0278_FirstBadVersion
 * </pre>
 */
public class LC0278_FirstBadVersion {

    /** Binary search using a predicate as the (mock) API. O(log n) calls. */
    public static int firstBadVersion(int n, IntPredicate isBadVersion) {
        int lo = 1, hi = n;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;                 // overflow-safe
            if (isBadVersion.test(mid)) hi = mid;
            else                        lo = mid + 1;
        }
        return lo;
    }

    /** Helper for local testing — would be the LeetCode-provided API in submission. */
    private static IntPredicate badFrom(int firstBad) {
        return v -> v >= firstBad;
    }

    public static void main(String[] args) {
        System.out.println(firstBadVersion(5, badFrom(4)));        // 4
        System.out.println(firstBadVersion(1, badFrom(1)));        // 1
        System.out.println(firstBadVersion(10, badFrom(7)));       // 7
        System.out.println(firstBadVersion(Integer.MAX_VALUE, badFrom(Integer.MAX_VALUE)));  // 2147483647
    }
}
