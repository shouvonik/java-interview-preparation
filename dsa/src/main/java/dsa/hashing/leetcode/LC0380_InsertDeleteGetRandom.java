package dsa.hashing.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * LeetCode 380 — Insert Delete GetRandom O(1)
 * Difficulty: Medium   Tags: Array, Hash Table, Math, Design, Randomized
 * URL: https://leetcode.com/problems/insert-delete-getrandom-o1/
 *
 * <h2>Problem</h2>
 * Implement {@code RandomizedSet} supporting three operations in average O(1):
 * <ul>
 *   <li>{@code insert(val)} — add val if not present; return true if added.</li>
 *   <li>{@code remove(val)} — remove val if present; return true if removed.</li>
 *   <li>{@code getRandom()} — return a uniformly random element. Each element
 *       must have equal probability.</li>
 * </ul>
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>-2^31 &le; val &le; 2^31 - 1</li>
 *   <li>At most 2 * 10^5 calls combined.</li>
 *   <li>{@code getRandom} only called when at least one element is present.</li>
 * </ul>
 *
 * <h2>Approach — ArrayList + HashMap</h2>
 * <ul>
 *   <li>{@code ArrayList<Integer> values} stores elements in insertion order,
 *       letting {@code getRandom} pick {@code values.get(rng.nextInt(size))} in O(1).</li>
 *   <li>{@code HashMap<Integer, Integer> index} maps each value to its position
 *       in {@code values}, giving O(1) presence test for insert/remove.</li>
 * </ul>
 *
 * <h2>The remove trick</h2>
 * Removing arbitrary index from an ArrayList is O(n) due to shifting. To stay
 * O(1):
 * <ol>
 *   <li>Find the index {@code i} of the value to remove.</li>
 *   <li>Swap {@code values.get(last)} into position {@code i}.</li>
 *   <li>Update {@code index.put(lastVal, i)}.</li>
 *   <li>Pop the last element.</li>
 * </ol>
 * This is the famous "swap with last and pop" pattern.
 *
 * <h2>Why uniformity holds</h2>
 * {@code getRandom} samples uniformly from a fixed-size ArrayList, so each
 * current element has probability {@code 1/size}. The swap-with-last trick
 * doesn't bias future samples — every still-present element has equal chance.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Remove the last element — special-case the swap to avoid swapping
 *       with itself (still works without the special case, but is wasted work).</li>
 *   <li>Insert duplicate — return false.</li>
 *   <li>Remove absent — return false.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.hashing.leetcode.LC0380_InsertDeleteGetRandom
 * </pre>
 */
public class LC0380_InsertDeleteGetRandom {

    public static class RandomizedSet {
        private final List<Integer> values = new ArrayList<>();
        private final Map<Integer, Integer> index = new HashMap<>();
        private final Random rng = new Random(42);

        public boolean insert(int val) {
            if (index.containsKey(val)) return false;
            index.put(val, values.size());
            values.add(val);
            return true;
        }

        public boolean remove(int val) {
            Integer idx = index.remove(val);
            if (idx == null) return false;
            int last = values.size() - 1;
            if (idx != last) {
                int lastVal = values.get(last);
                values.set(idx, lastVal);
                index.put(lastVal, idx);
            }
            values.remove(last);
            return true;
        }

        public int getRandom() {
            return values.get(rng.nextInt(values.size()));
        }
    }

    public static void main(String[] args) {
        RandomizedSet s = new RandomizedSet();
        System.out.println(s.insert(1));    // true
        System.out.println(s.remove(2));    // false (not present)
        System.out.println(s.insert(2));    // true
        int r1 = s.getRandom();              // 1 or 2
        System.out.println(r1 == 1 || r1 == 2);
        System.out.println(s.remove(1));    // true
        System.out.println(s.insert(2));    // false (already present)
        System.out.println(s.getRandom());  // 2
    }
}
