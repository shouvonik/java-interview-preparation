package dsa.greedy.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LeetCode 406 — Queue Reconstruction by Height
 * Difficulty: Medium   Tags: Array, Greedy, Sorting
 * URL: https://leetcode.com/problems/queue-reconstruction-by-height/
 *
 * <h2>Problem</h2>
 * You are given an array of people, {@code people[i] = [h_i, k_i]} where
 * {@code h_i} is the height of the i-th person and {@code k_i} is the number
 * of people in front of them who have height &ge; {@code h_i}. Reconstruct
 * the queue.
 *
 * <h2>Approach — sort + insert</h2>
 * Sort by height DESC, with ties broken by k ASC. Walk in that order and
 * insert each person at index {@code k_i} of a growing list.
 *
 * <h2>Why this works</h2>
 * When we insert person p, the list contains only taller-or-equal people.
 * Their relative order to p is what matters. Inserting at index {@code k_i}
 * makes exactly {@code k_i} taller-or-equal people sit in front. Shorter
 * people added later don't affect this count.
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.greedy.leetcode.LC0406_QueueReconstructionByHeight
 * </pre>
 */
public class LC0406_QueueReconstructionByHeight {

    public static int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]);
        List<int[]> out = new ArrayList<>();
        for (int[] p : people) out.add(p[1], p);
        return out.toArray(new int[0][]);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(reconstructQueue(
            new int[][]{{7, 0}, {4, 4}, {7, 1}, {5, 0}, {6, 1}, {5, 2}})));
        // [[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]

        System.out.println(Arrays.deepToString(reconstructQueue(
            new int[][]{{6, 0}, {5, 0}, {4, 0}, {3, 2}, {2, 2}, {1, 4}})));
        // [[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]
    }
}
