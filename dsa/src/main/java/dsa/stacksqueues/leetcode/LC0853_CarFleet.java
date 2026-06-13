package dsa.stacksqueues.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 853 — Car Fleet
 * Difficulty: Medium   Tags: Array, Sorting, Stack, Monotonic Stack
 * URL: https://leetcode.com/problems/car-fleet/
 *
 * <h2>Problem</h2>
 * There are n cars going to the same destination along a one-lane road. The
 * destination is at a distance {@code target}. Each car {@code i} has a
 * starting {@code position[i]} and speed {@code speed[i]}. Cars cannot
 * overtake; a faster car catches up to a slower one and the two become a
 * "fleet" travelling at the slower speed. Return the number of fleets that
 * arrive at the destination.
 *
 * <h2>Examples</h2>
 * <pre>
 *   target=12, position=[10,8,0,5,3], speed=[2,4,1,1,3]  -> 3
 *   target=10, position=[3],          speed=[3]          -> 1
 *   target=100, position=[0,2,4],     speed=[4,2,1]      -> 1
 * </pre>
 *
 * <h2>Approach — sort by starting position desc, compare ETAs</h2>
 * Compute each car's time-to-target = {@code (target - position[i]) / speed[i]}.
 * Sort cars by starting position in DESCENDING order. Walk the sorted list:
 * a car with a smaller (faster) ETA than the current fleet leader catches up
 * and joins it. A car with a strictly LARGER ETA forms a new fleet behind.
 *
 * <h2>Why sort by descending position</h2>
 * We process cars from front-most (closest to target) to back-most. The
 * front-most car always leads its fleet's pace. Each new car either catches
 * the current fleet (so no new fleet) or doesn't (so it starts one).
 *
 * <h2>Use double for time-to-target</h2>
 * Integer division would lose precision and produce incorrect catch-up
 * decisions. Cast to double during the division.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single car — always 1 fleet.</li>
 *   <li>All identical times — single fleet.</li>
 *   <li>Strictly increasing times from front to back — n fleets.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.stacksqueues.leetcode.LC0853_CarFleet
 * </pre>
 */
public class LC0853_CarFleet {

    /** Sort + sweep — O(n log n). */
    public static int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        double[][] cars = new double[n][2];                  // [position, time]
        for (int i = 0; i < n; i++) {
            cars[i][0] = position[i];
            cars[i][1] = (double) (target - position[i]) / speed[i];
        }
        Arrays.sort(cars, Comparator.comparingDouble((double[] a) -> -a[0]));

        int fleets = 0;
        double slowestSoFar = 0;
        for (double[] c : cars) {
            if (c[1] > slowestSoFar) {
                fleets++;
                slowestSoFar = c[1];
            }
        }
        return fleets;
    }

    public static void main(String[] args) {
        System.out.println(carFleet(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));  // 3
        System.out.println(carFleet(10, new int[]{3}, new int[]{3}));                            // 1
        System.out.println(carFleet(100, new int[]{0, 2, 4}, new int[]{4, 2, 1}));                // 1
    }
}
