package dsa.sorting;

import java.util.Comparator;
import java.util.List;

/**
 * Sorting — implementations of the algorithms interviewers most often ask you to write.
 *
 * Run: java dsa.sorting.Sorting
 */
public class Sorting {

    // ---------------------------------------------------------------------
    // 1) Merge sort — O(n log n) time, O(n) space, stable.
    // ---------------------------------------------------------------------
    static void mergeSort(int[] arr) {
        if (arr.length <= 1) return;
        int[] buffer = new int[arr.length];
        mergeSort(arr, buffer, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int[] buffer, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        mergeSort(arr, buffer, lo, mid);
        mergeSort(arr, buffer, mid + 1, hi);
        merge(arr, buffer, lo, mid, hi);
    }

    private static void merge(int[] arr, int[] buffer, int lo, int mid, int hi) {
        for (int i = lo; i <= hi; i++) buffer[i] = arr[i];
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (buffer[i] <= buffer[j]) arr[k++] = buffer[i++];  // <= keeps stable
            else                        arr[k++] = buffer[j++];
        }
        while (i <= mid) arr[k++] = buffer[i++];
        // right side already in place if i exhausted
    }

    // ---------------------------------------------------------------------
    // 2) Quick sort — in-place, average O(n log n), worst O(n²).
    // ---------------------------------------------------------------------
    // Median-of-three pivot would harden against worst-case adversarial input;
    // here we use a simple random pivot which is good enough for the pattern.
    static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1, new java.util.Random(42));
    }

    private static void quickSort(int[] arr, int lo, int hi, java.util.Random rng) {
        if (lo >= hi) return;
        int pivotIdx = lo + rng.nextInt(hi - lo + 1);
        int pivot = arr[pivotIdx];
        swap(arr, pivotIdx, hi);                          // park pivot at end

        int store = lo;
        for (int i = lo; i < hi; i++) {
            if (arr[i] < pivot) swap(arr, i, store++);
        }
        swap(arr, store, hi);                             // restore pivot

        quickSort(arr, lo, store - 1, rng);
        quickSort(arr, store + 1, hi, rng);
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    // ---------------------------------------------------------------------
    // 3) Quickselect — kth smallest in average O(n), without sorting.
    // ---------------------------------------------------------------------
    // k is 1-indexed: k=1 -> minimum.
    static int quickselect(int[] arr, int k) {
        int target = k - 1;
        int lo = 0, hi = arr.length - 1;
        java.util.Random rng = new java.util.Random(42);
        while (lo <= hi) {
            int pivotIdx = lo + rng.nextInt(hi - lo + 1);
            int p = partition(arr, lo, hi, pivotIdx);
            if (p == target) return arr[p];
            if (p < target)  lo = p + 1;
            else             hi = p - 1;
        }
        throw new IllegalStateException("unreachable");
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

    // ---------------------------------------------------------------------
    // 4) Dutch national flag — 3-way partition in one pass.
    // ---------------------------------------------------------------------
    // Useful when values fall in a small set (sort colors, bucket priorities).
    // [0..lo) = LESS, [lo..mid) = EQUAL, [mid..hi] = UNKNOWN, (hi..n) = GREATER
    static void dutchFlag(int[] arr, int pivot) {
        int lo = 0, mid = 0, hi = arr.length - 1;
        while (mid <= hi) {
            if      (arr[mid] < pivot) swap(arr, lo++, mid++);
            else if (arr[mid] > pivot) swap(arr, mid, hi--);
            else                       mid++;
        }
    }

    // ---------------------------------------------------------------------
    // 5) Multi-key Comparator — leaderboard.
    // ---------------------------------------------------------------------
    // Score DESC, then time ASC, then name ASC. Chaining .thenComparing
    // composes tie-breakers without nesting if/else.
    public record Entry(String name, int score, int timeMs) {}

    static List<Entry> rank(List<Entry> entries) {
        return entries.stream()
            .sorted(Comparator
                .comparingInt(Entry::score).reversed()
                .thenComparingInt(Entry::timeMs)
                .thenComparing(Entry::name))
            .toList();
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        int[] a = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        mergeSort(a);
        System.out.println("mergeSort  -> " + java.util.Arrays.toString(a));
        // [1, 2, 3, 4, 5, 6, 7, 8, 9]

        int[] b = {5, 2, 8, 1, 9, 3, 7, 4, 6};
        quickSort(b);
        System.out.println("quickSort  -> " + java.util.Arrays.toString(b));
        // [1, 2, 3, 4, 5, 6, 7, 8, 9]

        int[] c = {7, 10, 4, 3, 20, 15};
        System.out.println("3rd smallest = " + quickselect(c.clone(), 3));
        // 7

        int[] d = {2, 0, 2, 1, 1, 0};
        dutchFlag(d, 1);
        System.out.println("dutchFlag(=1) -> " + java.util.Arrays.toString(d));
        // [0, 0, 1, 1, 2, 2]

        List<Entry> leaderboard = List.of(
            new Entry("Alice", 100, 1200),
            new Entry("Bob",   100,  900),
            new Entry("Cara",   90, 1500),
            new Entry("Dan",   100,  900)
        );
        System.out.println("rank:");
        rank(leaderboard).forEach(e -> System.out.println("  " + e));
        // 1. Bob   (100, 900)
        // 2. Dan   (100, 900)
        // 3. Alice (100, 1200)
        // 4. Cara  ( 90, 1500)
    }
}
