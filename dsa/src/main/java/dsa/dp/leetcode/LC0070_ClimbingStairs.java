package dsa.dp.leetcode;

/**
 * LeetCode 70 — Climbing Stairs
 * Difficulty: Easy   Tags: Math, DP, Memoization
 * URL: https://leetcode.com/problems/climbing-stairs/
 *
 * <h2>Problem</h2>
 * You are climbing a staircase. It takes n steps to reach the top. Each time
 * you can either climb 1 or 2 steps. In how many distinct ways can you climb
 * to the top?
 *
 * <h2>Recurrence</h2>
 * {@code f(n) = f(n - 1) + f(n - 2)}, with {@code f(1) = 1, f(2) = 2}.
 * Fibonacci shape; O(1) space with two running variables.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.dp.leetcode.LC0070_ClimbingStairs
 * </pre>
 */
public class LC0070_ClimbingStairs {

    public static int climbStairs(int n) {
        if (n <= 2) return n;
        int prev2 = 1, prev1 = 2;
        for (int i = 3; i <= n; i++) {
            int curr = prev1 + prev2;
            prev2 = prev1;
            prev1 = curr;
        }
        return prev1;
    }

    public static void main(String[] args) {
        System.out.println(climbStairs(2));    // 2
        System.out.println(climbStairs(3));    // 3
        System.out.println(climbStairs(5));    // 8
    }
}
