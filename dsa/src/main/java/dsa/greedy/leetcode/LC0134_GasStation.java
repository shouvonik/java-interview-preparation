package dsa.greedy.leetcode;

/**
 * LeetCode 134 — Gas Station
 * Difficulty: Medium   Tags: Array, Greedy
 * URL: https://leetcode.com/problems/gas-station/
 *
 * <h2>Problem</h2>
 * There are n gas stations along a circular route, where the amount of gas at
 * the i-th station is {@code gas[i]} and travelling from station i to i+1
 * costs {@code cost[i]} gas. You begin with an empty tank at some station.
 * Return the starting gas station's index if you can travel around the circuit
 * once in the clockwise direction, otherwise return -1.
 *
 * <h2>Approach — single-pass greedy</h2>
 * Compute {@code total = sum(gas[i] - cost[i])}. If negative, return -1 (not
 * enough fuel overall). Otherwise, walk once tracking {@code tank}; whenever
 * tank goes negative, reset start to the next station and tank to 0.
 *
 * <h2>Why the greedy works</h2>
 * If sum(gas) &ge; sum(cost), a solution exists (provable). If starting at
 * station s fails by running negative at station e, no station in
 * {@code [s..e]} can be a valid start either (each had a non-positive prefix
 * relative to s). So we can skip past e + 1.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0134_GasStation
 * </pre>
 */
public class LC0134_GasStation {

    public static int canCompleteCircuit(int[] gas, int[] cost) {
        int total = 0, tank = 0, start = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            total += diff;
            tank += diff;
            if (tank < 0) {
                start = i + 1;
                tank = 0;
            }
        }
        return total < 0 ? -1 : start;
    }

    public static void main(String[] args) {
        System.out.println(canCompleteCircuit(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5, 1, 2})); // 3
        System.out.println(canCompleteCircuit(new int[]{2, 3, 4}, new int[]{3, 4, 3}));              // -1
    }
}
