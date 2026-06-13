package dsa.graphs.exercises;

import java.util.List;

/**
 * Graphs — exercises.
 */
public class GraphsExercises {

    /**
     * Exercise 1 — Course Schedule (LeetCode 207).
     * numCourses courses labeled 0..n-1. prerequisites[i] = [a, b] means
     * to take a you must finish b. Return true if you can finish all courses.
     * Hint: topo sort — if every node ends up in the order, no cycle.
     */
    static boolean canFinish(int numCourses, int[][] prerequisites) {
        // TODO
        return false;
    }

    /**
     * Exercise 2 — Redundant Connection (LeetCode 684).
     * A tree of n nodes (1..n) has one extra edge. Return that edge.
     * Hint: union-find. The first edge whose endpoints are already connected
     * is the redundant one.
     */
    static int[] findRedundantConnection(int[][] edges) {
        // TODO
        return new int[]{-1, -1};
    }

    /**
     * Exercise 3 — Word Ladder (LeetCode 127).
     * Return shortest number of words to transform beginWord into endWord,
     * changing one letter at a time, each intermediate must be in wordList.
     * Hint: BFS treating words as graph nodes; neighbours differ by one char.
     */
    static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // TODO
        return 0;
    }

    /**
     * Exercise 4 — Number of Connected Components in an Undirected Graph (LeetCode 323).
     * n nodes, edges[][2]. Return count of connected components.
     */
    static int countComponents(int n, int[][] edges) {
        // TODO
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("canFinish = " + canFinish(2, new int[][]{{1, 0}}));         // true
        System.out.println("redundant = "
            + java.util.Arrays.toString(findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}})));
        System.out.println("ladderLength = "
            + ladderLength("hit", "cog", List.of("hot", "dot", "dog", "lot", "log", "cog")));
        System.out.println("countComponents = "
            + countComponents(5, new int[][]{{0, 1}, {1, 2}, {3, 4}}));
    }
}
