package javacore.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Collections — the most-asked area at lead level.
 *
 * Run: java javacore.collections.CollectionsGuide
 *
 * The interviewer is checking that you can talk about WHY a collection behaves
 * the way it does, not just which methods to call. The demos below illustrate
 * the behaviour you should be able to explain on a whiteboard.
 */
public class CollectionsGuide {

    // ---------------------------------------------------------------------
    // 1) HashMap — load factor, resize, collision behaviour
    // ---------------------------------------------------------------------
    // What's happening under the hood when you put() into a HashMap:
    //
    //   1. Compute h = key.hashCode(), then mix it: hash = h ^ (h >>> 16).
    //      The XOR-with-shifted-self spreads the high bits into the low bits,
    //      which matters because the bucket index uses only the LOW bits.
    //   2. bucket = hash & (capacity - 1). Capacity is always a power of two,
    //      so the & is equivalent to "mod capacity" but cheaper.
    //   3. Walk the bucket's chain comparing with equals().
    //   4. If absent, append. If the bucket grows past 8 entries AND total
    //      capacity is >= 64, that single bucket converts to a red-black tree
    //      (TreeNode) — O(log n) lookup instead of O(n) walk.
    //   5. When size > capacity * load_factor (default 0.75), capacity doubles
    //      and every entry is rehashed (the rehash is the expensive bit).
    //
    // This demo just shows the surface behaviour; for a deeper look use the
    // -XX:+PrintFlagsFinal and a heap dump.
    static void hashMapBasics() {
        Map<String, Integer> map = new HashMap<>();
        map.put("alice", 30);
        map.put("bob", 25);
        map.put("carol", 35);

        // null keys and values are allowed in HashMap (but not ConcurrentHashMap).
        map.put(null, 0);
        map.put("alice", null);

        System.out.println("HashMap with nulls: " + map);
    }

    // ---------------------------------------------------------------------
    // 2) ArrayList vs LinkedList — pick the right one
    // ---------------------------------------------------------------------
    // ArrayList is a wrapper around a contiguous array. Random access is O(1),
    // appends are amortised O(1) (the backing array doubles when full), middle
    // inserts/removes are O(n) because everything past the index shifts.
    //
    // LinkedList is a doubly-linked list of node objects. Random access by
    // index walks the list — O(n). Inserts/removes are O(1) ONCE you have the
    // node reference, but you usually don't (you have an index, which costs
    // O(n) to walk to).
    //
    // In practice ArrayList wins almost everywhere because:
    //   - Cache lines hold contiguous memory; chasing pointers misses cache.
    //   - LinkedList's per-node overhead (~24 bytes + the value) is heavy.
    //   - ArrayDeque beats both for queue/stack use.
    static void arrayVsLinked() {
        List<Integer> arr = new ArrayList<>(10);
        List<Integer> lnk = new LinkedList<>();
        for (int i = 0; i < 5; i++) { arr.add(i); lnk.add(i); }
        System.out.println("ArrayList random access [2] = " + arr.get(2));     // O(1)
        System.out.println("LinkedList random access [2] = " + lnk.get(2));    // O(n) — fine here, awful at scale
    }

    // ---------------------------------------------------------------------
    // 3) LinkedHashMap — insertion or access order
    // ---------------------------------------------------------------------
    // LinkedHashMap maintains a doubly-linked list of entries on top of the
    // HashMap structure. Two modes:
    //   - Insertion order (default): entries iterate in the order they were put.
    //   - Access order (constructor flag = true): every get() / put() moves
    //     the entry to the END of the list. That makes LRU eviction trivial:
    //     override removeEldestEntry to evict the head once size > capacity.
    //
    // The whole point of using LinkedHashMap over HashMap is predictable
    // iteration order — useful for caches, JSON-like output, and any time
    // you don't want to surprise your tests.
    static void linkedHashMapOrder() {
        LinkedHashMap<String, Integer> insertion = new LinkedHashMap<>();
        insertion.put("c", 3); insertion.put("a", 1); insertion.put("b", 2);
        System.out.println("LinkedHashMap (insertion) keys = " + insertion.keySet());

        // Access-order: 'a' is accessed, so it moves to the end.
        LinkedHashMap<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
        accessOrder.put("c", 3); accessOrder.put("a", 1); accessOrder.put("b", 2);
        accessOrder.get("c");
        System.out.println("LinkedHashMap (access) keys = " + accessOrder.keySet());
    }

    // ---------------------------------------------------------------------
    // 4) TreeMap — sorted by key, O(log n)
    // ---------------------------------------------------------------------
    // TreeMap is a red-black tree keyed by either natural ordering or a
    // Comparator. Use it when you need range queries (headMap, tailMap,
    // subMap), floor/ceiling lookups, or iteration in sorted order. Costs
    // are O(log n) for all ops — slower than HashMap but with guarantees.
    static void treeMapDemo() {
        TreeMap<Integer, String> tree = new TreeMap<>();
        tree.put(10, "ten");
        tree.put(20, "twenty");
        tree.put(30, "thirty");
        System.out.println("TreeMap.floorKey(25)   = " + tree.floorKey(25));    // 20 — largest key <= 25
        System.out.println("TreeMap.ceilingKey(25) = " + tree.ceilingKey(25));  // 30 — smallest key >= 25
        System.out.println("TreeMap.subMap(15, 30) = " + tree.subMap(15, 30));  // {20=twenty}
    }

    // ---------------------------------------------------------------------
    // 5) Comparator — chain by multiple keys
    // ---------------------------------------------------------------------
    // Pre-Java-8 sorting required writing a Comparator<T> with nested if/else
    // for tie-breakers. The fluent API replaces that with composition:
    //   comparing(primaryKey).thenComparing(secondaryKey)...
    // Each link is a NEW Comparator, so the order matters — the FIRST link is
    // checked first; later links only matter on ties.
    public record Player(String name, int score, int timeMs) {}

    static List<Player> rank(List<Player> players) {
        return players.stream()
            .sorted(Comparator
                .comparingInt(Player::score).reversed()    // primary: score DESC
                .thenComparingInt(Player::timeMs)          // tiebreak 1: time ASC
                .thenComparing(Player::name))              // tiebreak 2: name ASC
            .toList();
    }

    // ---------------------------------------------------------------------
    // 6) ConcurrentHashMap — concurrent default
    // ---------------------------------------------------------------------
    // ConcurrentHashMap uses fine-grained locking on bucket arrays (CAS for
    // empty slots, synchronized blocks on the bin's first node otherwise).
    // Concurrent reads are wait-free; writes to different buckets don't block
    // each other. compute() and merge() are atomic for read-modify-write —
    // use them instead of get/put pairs.
    //
    // Differences from HashMap:
    //   - null keys and values are FORBIDDEN (so null-as-absent ambiguity is gone).
    //   - size() is approximate under concurrent updates.
    //   - Iterators are weakly consistent — they reflect SOME state during
    //     iteration without throwing ConcurrentModificationException.
    static void concurrentMapDemo() {
        ConcurrentHashMap<String, Integer> hits = new ConcurrentHashMap<>();
        hits.merge("home", 1, Integer::sum);   // atomic: get + put folded together
        hits.merge("home", 1, Integer::sum);
        hits.merge("about", 1, Integer::sum);
        System.out.println("ConcurrentHashMap hits = " + hits);
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        hashMapBasics();
        arrayVsLinked();
        linkedHashMapOrder();
        treeMapDemo();

        List<Player> ranked = rank(List.of(
            new Player("Alice", 100, 1200),
            new Player("Bob",   100,  900),
            new Player("Cara",   90, 1500),
            new Player("Dan",   100,  900)
        ));
        System.out.println("Ranked: " + ranked);
        // Bob then Dan (tied on score+time, alphabetical), then Alice, then Cara

        concurrentMapDemo();

        // Bonus — HashSet is just a HashMap with PRESENT as the value.
        HashSet<String> seen = new HashSet<>();
        seen.add("x"); seen.add("y"); seen.add("x");
        System.out.println("HashSet sees 'x' once: size=" + seen.size());

        // Bonus — Arrays.asList vs List.of: asList returns a fixed-size,
        // backed-by-array list (you can SET but not add/remove). List.of is
        // fully immutable.
        List<Integer> backed = Arrays.asList(1, 2, 3);
        backed.set(0, 99);                                  // OK
        System.out.println("Arrays.asList after set: " + backed);
        try {
            backed.add(4);                                  // UnsupportedOperationException
        } catch (UnsupportedOperationException e) {
            System.out.println("Arrays.asList add() correctly threw");
        }
    }
}
