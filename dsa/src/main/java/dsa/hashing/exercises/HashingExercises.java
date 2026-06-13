package dsa.hashing.exercises;

import java.util.*;

/**
 * Hashing — exercises.
 */
public class HashingExercises {

    /**
     * Exercise 1 — Two Sum (LeetCode 1).
     * Return indices i, j such that nums[i] + nums[j] == target. Exactly one solution.
     * Example: nums=[2,7,11,15], target=9 -> [0,1]
     */
    static int[] twoSum(int[] nums, int target) {
        // TODO
        return new int[]{-1, -1};
    }

    /**
     * Exercise 2 — Valid Sudoku (LeetCode 36).
     * Check that each row, column, and 3x3 box has no duplicate digit '1'..'9'.
     * Use composite keys like "row3=5", "col7=5", "box1=5" stored in one HashSet.
     */
    static boolean isValidSudoku(char[][] board) {
        // TODO
        return false;
    }

    /**
     * Exercise 3 — Insert Delete GetRandom O(1) (LeetCode 380).
     * Design a structure with O(1) insert, remove, and random.
     * Hint: Map<value, index> + ArrayList<value>. To remove, swap with last.
     */
    public static class RandomSet {
        private final List<Integer> values = new ArrayList<>();
        private final Map<Integer, Integer> index = new HashMap<>();
        private final Random rng = new Random(42);

        public boolean insert(int val) {
            // TODO
            return false;
        }

        public boolean remove(int val) {
            // TODO
            return false;
        }

        public int getRandom() {
            // TODO
            return -1;
        }
    }

    /**
     * Exercise 4 — Top K frequent elements (LeetCode 347).
     * Return the k most frequent elements. Aim for O(n) avg with bucket sort.
     * Example: nums=[1,1,1,2,2,3], k=2 -> [1,2]
     */
    static int[] topKFrequent(int[] nums, int k) {
        // TODO
        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println("twoSum = " + Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));

        RandomSet rs = new RandomSet();
        rs.insert(1); rs.insert(2); rs.insert(3);
        rs.remove(2);
        System.out.println("RandomSet getRandom = " + rs.getRandom());

        System.out.println("topKFrequent = "
            + Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }
}
