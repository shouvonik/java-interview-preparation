package dsa.hashing;

import java.util.*;

/**
 * Hashing — composite keys, group-by, set tricks, and an LRU cache.
 *
 * Run: java dsa.hashing.Hashing
 */
public class Hashing {

    // ---------------------------------------------------------------------
    // 1) Dedup with a composite key.
    // ---------------------------------------------------------------------
    // Records auto-generate equals/hashCode, so they're the ideal composite-key type.
    public record CallKey(String userId, String requestId, int hourBucket) {}
    public record ApiCall(String userId, String requestId, int hourBucket, long timestamp) {}

    static List<ApiCall> dedupCalls(List<ApiCall> calls) {
        Set<CallKey> seen = new HashSet<>();
        List<ApiCall> out = new ArrayList<>();
        for (ApiCall c : calls) {
            CallKey key = new CallKey(c.userId(), c.requestId(), c.hourBucket());
            if (seen.add(key)) out.add(c);                  // add() returns false if already present
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // 2) Group by computed key — anagrams.
    // ---------------------------------------------------------------------
    // Anagram canonical key = sorted letters. Group everything that shares it.
    static List<List<String>> groupAnagrams(String[] words) {
        Map<String, List<String>> groups = new HashMap<>();
        for (String w : words) {
            char[] chars = w.toCharArray();
            Arrays.sort(chars);
            String key = new String(chars);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(w);
        }
        return new ArrayList<>(groups.values());
    }

    // ---------------------------------------------------------------------
    // 3) Longest consecutive sequence — O(n) with a HashSet.
    // ---------------------------------------------------------------------
    // Trick: only start counting from `x` if `x-1` is NOT in the set
    // (i.e. x is the start of a run). Each value is visited at most twice.
    static int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int best = 0;
        for (int x : set) {
            if (set.contains(x - 1)) continue;             // not a run start
            int len = 1;
            while (set.contains(x + len)) len++;
            best = Math.max(best, len);
        }
        return best;
    }

    // ---------------------------------------------------------------------
    // 4) LRU Cache — using LinkedHashMap in access-order mode.
    // ---------------------------------------------------------------------
    // The simplest correct LRU in Java. Override removeEldestEntry to evict.
    // For an interview, also know the doubly-linked-list-+-hashmap version.
    static class LruCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;
        LruCache(int capacity) {
            super(capacity, 0.75f, /*accessOrder=*/true);
            this.capacity = capacity;
        }
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        // Dedup
        List<ApiCall> calls = List.of(
            new ApiCall("u1", "r1", 14, 1000),
            new ApiCall("u1", "r1", 14, 1500),     // duplicate of above
            new ApiCall("u1", "r2", 14, 2000),
            new ApiCall("u2", "r1", 14, 2500)
        );
        System.out.println("After dedup: " + dedupCalls(calls).size() + " calls"); // 3

        // Group anagrams
        String[] words = {"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println("Anagram groups: " + groupAnagrams(words));
        // [[eat, tea, ate], [tan, nat], [bat]]   (order of groups not guaranteed)

        // Longest consecutive
        int[] events = {100, 4, 200, 1, 3, 2};
        System.out.println("Longest consecutive run: " + longestConsecutive(events));
        // 4  ([1,2,3,4])

        // LRU
        LruCache<String, String> cache = new LruCache<>(3);
        cache.put("a", "Alpha");
        cache.put("b", "Beta");
        cache.put("c", "Gamma");
        cache.get("a");                            // touches a -> recency order: b,c,a
        cache.put("d", "Delta");                   // evicts b (eldest)
        System.out.println("LRU keys: " + cache.keySet());   // [c, a, d]
    }
}
