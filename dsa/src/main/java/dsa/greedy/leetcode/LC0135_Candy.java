package dsa.greedy.leetcode;

import java.util.Arrays;

/**
 * LeetCode 135 — Candy
 * Difficulty: Hard   Tags: Array, Greedy
 * URL: https://leetcode.com/problems/candy/
 *
 * <h2>Problem</h2>
 * There are n children. Each child has a rating in {@code ratings}. You must
 * give each child at least 1 candy. Children with a higher rating get more
 * candies than their (immediate) neighbours. Return the minimum total candies.
 *
 * <h2>Approach — two passes</h2>
 * <ol>
 *   <li>Left-to-right: if {@code ratings[i] > ratings[i-1]}, child i gets
 *       {@code candies[i-1] + 1}.</li>
 *   <li>Right-to-left: if {@code ratings[i] > ratings[i+1]}, ensure
 *       {@code candies[i] >= candies[i+1] + 1}.</li>
 * </ol>
 * Sum the array.
 *
 * <h2>Why two passes</h2>
 * One pass can't simultaneously honour both the left and right neighbour
 * constraints. The first sweep handles the "rising from the left" requirement;
 * the second sweep patches up the "rising from the right".
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0135_Candy
 * </pre>
 */
public class LC0135_Candy {

    public static int candy(int[] ratings) {
        int n = ratings.length;
        int[] candies = new int[n];
        Arrays.fill(candies, 1);
        for (int i = 1; i < n; i++) {
            if (ratings[i] > ratings[i - 1]) candies[i] = candies[i - 1] + 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) candies[i] = Math.max(candies[i], candies[i + 1] + 1);
        }
        int total = 0;
        for (int c : candies) total += c;
        return total;
    }

    public static void main(String[] args) {
        System.out.println(candy(new int[]{1, 0, 2}));        // 5
        System.out.println(candy(new int[]{1, 2, 2}));        // 4
        System.out.println(candy(new int[]{1, 3, 2, 2, 1}));  // 7
    }
}
