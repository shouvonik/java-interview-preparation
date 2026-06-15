package javacore.collections.exercises.solutions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Reference solutions for CollectionsExercises.
 */
public class CollectionsSolutions {

    // Exercise 1 — LRU via LinkedHashMap in access-order mode.
    // The trick: pass accessOrder=true to the super constructor; then get()
    // moves the entry to the end of the internal doubly-linked list.
    // removeEldestEntry is consulted after every put; return true once we
    // exceed capacity and the eldest (head of the list) is evicted.
    public static class LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public LruCache(int capacity) {
            super(capacity, 0.75f, /*accessOrder=*/true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    // Exercise 2 — frequencies via merge.
    // merge(key, 1, Integer::sum) is the idiomatic "increment a counter map"
    // — atomic from the map's POV, no get/put pair, no explicit conditional.
    static <T> Map<T, Integer> frequencies(List<T> items) {
        Map<T, Integer> counts = new HashMap<>();
        for (T item : items) counts.merge(item, 1, Integer::sum);
        return counts;
    }

    // Exercise 3 — group by customer, then sort each list.
    // groupingBy collects into Map<K, List<V>>. We sort each value list in a
    // second pass; alternatively a downstream Collector could collect into a
    // TreeSet/sorted structure during the group itself.
    public record Order(String customer, int orderId, double total) {}

    static Map<String, List<Order>> groupAndSort(List<Order> orders) {
        Map<String, List<Order>> grouped = orders.stream()
            .collect(Collectors.groupingBy(Order::customer));
        grouped.values().forEach(list -> list.sort(Comparator.comparingInt(Order::orderId)));
        return grouped;
    }

    // Exercise 4 — sliding window of size k+1, HashSet tracks current contents.
    // The window slides by ONE on every step: add nums[i] at the right edge,
    // and once the window exceeds k+1 elements, drop nums[i - k - 1] from
    // the left edge. If add() returns false, the value was already present
    // within k indices.
    static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!window.add(nums[i])) return true;
            if (window.size() > k) window.remove(nums[i - k]);
        }
        return false;
    }

    public static void main(String[] args) {
        // Exercise 1
        LruCache<Integer, String> cache = new LruCache<>(3);
        cache.put(1, "one"); cache.put(2, "two"); cache.put(3, "three");
        cache.get(1);
        cache.put(4, "four");
        System.out.println("LRU keys = " + cache.keySet());     // [3, 1, 4]

        // Exercise 2
        System.out.println("freq = " + frequencies(List.of("a", "b", "a", "c", "a", "b"))); // {a=3, b=2, c=1}

        // Exercise 3
        var ordered = groupAndSort(List.of(
            new Order("A", 3, 10), new Order("B", 1, 20),
            new Order("A", 1, 30), new Order("B", 5, 5)));
        System.out.println("Grouped & sorted = " + ordered);

        // Exercise 4
        System.out.println("nearbyDup [1,2,3,1] k=3 = " + containsNearbyDuplicate(new int[]{1, 2, 3, 1}, 3)); // true
        System.out.println("nearbyDup [1,0,1,1] k=1 = " + containsNearbyDuplicate(new int[]{1, 0, 1, 1}, 1)); // true
        System.out.println("nearbyDup [1,2,3,1,2,3] k=2 = "
            + containsNearbyDuplicate(new int[]{1, 2, 3, 1, 2, 3}, 2)); // false
    }
}
