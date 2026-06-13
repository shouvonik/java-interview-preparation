package dsa.sorting.exercises;

import java.util.List;

/**
 * Sorting — exercises.
 */
public class SortingExercises {

    /**
     * Exercise 1 — Kth largest element (LeetCode 215).
     * Use quickselect; k is 1-indexed for the largest.
     * Example: [3,2,1,5,6,4], k=2 -> 5
     */
    static int kthLargest(int[] nums, int k) {
        // TODO
        return -1;
    }

    /**
     * Exercise 2 — Merge intervals (LeetCode 56).
     * Sort by start, then sweep and merge overlapping.
     * Example: [[1,3],[2,6],[8,10],[15,18]] -> [[1,6],[8,10],[15,18]]
     */
    static int[][] mergeIntervals(int[][] intervals) {
        // TODO
        return new int[0][];
    }

    /**
     * Exercise 3 — Largest number (LeetCode 179).
     * Given non-negative ints, arrange them so concatenation forms the largest number.
     * Hint: custom comparator on string concatenation order.
     * Example: [3,30,34,5,9] -> "9534330"
     */
    static String largestNumber(int[] nums) {
        // TODO
        return "";
    }

    /**
     * Exercise 4 — Stable sort of records preserving input order on ties.
     * Sort the given list by department ASC, then keep insertion order within each dept.
     */
    public record Employee(String name, String dept) {}

    static List<Employee> sortByDept(List<Employee> employees) {
        // TODO — Java's Collections.sort / List.stream().sorted() are stable.
        return employees;
    }

    public static void main(String[] args) {
        System.out.println("kthLargest = " + kthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        System.out.println("mergeIntervals = "
            + java.util.Arrays.deepToString(mergeIntervals(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
        System.out.println("largestNumber = " + largestNumber(new int[]{3, 30, 34, 5, 9}));
        sortByDept(List.of(
            new Employee("Alice", "ENG"),
            new Employee("Bob",   "OPS"),
            new Employee("Cara",  "ENG"),
            new Employee("Dan",   "OPS")
        )).forEach(System.out::println);
    }
}
