package javacore.collections.exercises;

import java.util.*;

/**
 * Collections — exercises.
 */
public class CollectionsExercises {

    /**
     * Exercise 1 — Implement an LRU cache using LinkedHashMap.
     * Capacity 3; on the 4th unique put, evict the least-recently-used entry.
     * Bonus: do it WITHOUT calling super.put() for each access — the
     * accessOrder=true constructor flag does the heavy lifting.
     */
    public static class LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LruCache(int capacity) {
            // TODO — call super with accessOrder=true so get() moves to end.
            super(capacity);
            this.capacity = capacity;
        }

        // TODO — override removeEldestEntry to evict when size > capacity.
    }

    /**
     * Exercise 2 — Frequency map.
     * Given a list, return a Map of value -> count using merge or computeIfAbsent.
     * Bonus: do it without explicit if-statements.
     */
    static <T> Map<T, Integer> frequencies(List<T> items) {
        // TODO
        return Map.of();
    }

    /**
     * Exercise 3 — Group orders by customer, then sort each customer's list
     * by orderId ascending.
     */
    public record Order(String customer, int orderId, double total) {}

    static Map<String, List<Order>> groupAndSort(List<Order> orders) {
        // TODO — groupingBy + comparator on the value lists.
        return Map.of();
    }

    /**
     * Exercise 4 — Detect a "duplicate within k" sliding window.
     * Return true if any value appears twice within a window of size k+1.
     * Hint: a HashSet of the values in the current window, slid forward.
     * Example: [1,2,3,1], k=3 -> true (the 1's are 3 apart).
     */
    static boolean containsNearbyDuplicate(int[] nums, int k) {
        // TODO
        return false;
    }

    public static void main(String[] args) {
        // Exercise 1
        LruCache<Integer, String> cache = new LruCache<>(3);
        cache.put(1, "one"); cache.put(2, "two"); cache.put(3, "three");
        cache.get(1);                              // touches 1
        cache.put(4, "four");                       // should evict 2
        System.out.println("LRU keys (expected [3,1,4]) = " + cache.keySet());

        // Exercise 2
        System.out.println("freq([a,b,a,c,a,b]) = "
            + frequencies(List.of("a", "b", "a", "c", "a", "b")));

        // Exercise 3
        var ordered = groupAndSort(List.of(
            new Order("A", 3, 10), new Order("B", 1, 20),
            new Order("A", 1, 30), new Order("B", 5, 5)));
        System.out.println("Grouped & sorted = " + ordered);

        // Exercise 4
        System.out.println("nearbyDup([1,2,3,1], 3) = " + containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3));
        System.out.println("nearbyDup([1,0,1,1], 1) = " + containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1));
    }
}
