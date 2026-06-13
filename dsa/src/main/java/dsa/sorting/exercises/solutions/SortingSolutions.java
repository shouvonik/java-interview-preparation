package dsa.sorting.exercises.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Reference solutions for SortingExercises.
 */
public class SortingSolutions {

    // Exercise 1 — Kth largest via quickselect.
    // Equivalent to (n - k + 1)-th smallest.
    static int kthLargest(int[] nums, int k) {
        int target = nums.length - k;             // 0-indexed position we want
        int lo = 0, hi = nums.length - 1;
        Random rng = new Random(42);
        while (lo <= hi) {
            int p = partition(nums, lo, hi, lo + rng.nextInt(hi - lo + 1));
            if (p == target) return nums[p];
            if (p < target)  lo = p + 1;
            else             hi = p - 1;
        }
        throw new IllegalStateException();
    }

    private static int partition(int[] arr, int lo, int hi, int pivotIdx) {
        int pivot = arr[pivotIdx];
        swap(arr, pivotIdx, hi);
        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (arr[i] < pivot) swap(arr, i, store++);
        }
        swap(arr, store, hi);
        return store;
    }

    private static void swap(int[] a, int i, int j) { int t = a[i]; a[i] = a[j]; a[j] = t; }

    // Exercise 2 — Merge intervals.
    static int[][] mergeIntervals(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        List<int[]> out = new ArrayList<>();
        int[] cur = intervals[0];
        for (int i = 1; i < intervals.length; i++) {
            int[] next = intervals[i];
            if (next[0] <= cur[1]) {                          // overlaps
                cur[1] = Math.max(cur[1], next[1]);
            } else {
                out.add(cur);
                cur = next;
            }
        }
        out.add(cur);
        return out.toArray(new int[0][]);
    }

    // Exercise 3 — Largest number.
    // Compare a||b vs b||a to decide which should come first.
    static String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++) strs[i] = String.valueOf(nums[i]);
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));   // DESC by combined string
        if (strs[0].equals("0")) return "0";                      // all zeros edge case
        return String.join("", strs);
    }

    // Exercise 4 — Stable sort preserving input order within equal keys.
    public record Employee(String name, String dept) {}

    static List<Employee> sortByDept(List<Employee> employees) {
        return employees.stream()
            .sorted(Comparator.comparing(Employee::dept))         // stable -> preserves input order on ties
            .toList();
    }

    public static void main(String[] args) {
        System.out.println("kthLargest = " + kthLargest(new int[]{3, 2, 1, 5, 6, 4}, 2));
        // 5

        System.out.println("mergeIntervals = "
            + Arrays.deepToString(mergeIntervals(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}})));
        // [[1, 6], [8, 10], [15, 18]]

        System.out.println("largestNumber = " + largestNumber(new int[]{3, 30, 34, 5, 9}));
        // 9534330

        System.out.println("sortByDept:");
        sortByDept(List.of(
            new Employee("Alice", "ENG"),
            new Employee("Bob",   "OPS"),
            new Employee("Cara",  "ENG"),
            new Employee("Dan",   "OPS")
        )).forEach(e -> System.out.println("  " + e));
        // Alice ENG, Cara ENG, Bob OPS, Dan OPS
    }
}
