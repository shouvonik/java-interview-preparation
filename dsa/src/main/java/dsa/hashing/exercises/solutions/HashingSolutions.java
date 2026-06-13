package dsa.hashing.exercises.solutions;

import java.util.*;

/**
 * Reference solutions for HashingExercises.
 */
public class HashingSolutions {

    // Exercise 1 — Two Sum. Single pass: for each num, look up (target - num) in
    // the map of seen indices. If present, we have the answer.
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (seen.containsKey(complement)) return new int[]{seen.get(complement), i};
            seen.put(nums[i], i);
        }
        throw new IllegalArgumentException("no solution");
    }

    // Exercise 2 — Valid Sudoku. One pass; composite-key strings prevent collisions
    // between row/col/box namespaces.
    static boolean isValidSudoku(char[][] board) {
        Set<String> seen = new HashSet<>();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch == '.') continue;
                int box = (r / 3) * 3 + (c / 3);
                if (!seen.add("r" + r + "=" + ch)) return false;
                if (!seen.add("c" + c + "=" + ch)) return false;
                if (!seen.add("b" + box + "=" + ch)) return false;
            }
        }
        return true;
    }

    // Exercise 3 — O(1) insert/remove/random.
    public static class RandomSet {
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
            if (idx != last) {                                    // swap last into hole
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

    // Exercise 4 — Top K frequent with bucket sort by frequency.
    static int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int n : nums) counts.merge(n, 1, Integer::sum);

        // bucket[f] = list of values with frequency f. f in [1, n].
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[nums.length + 1];
        for (var e : counts.entrySet()) {
            int f = e.getValue();
            if (buckets[f] == null) buckets[f] = new ArrayList<>();
            buckets[f].add(e.getKey());
        }

        int[] out = new int[k];
        int idx = 0;
        for (int f = buckets.length - 1; f >= 1 && idx < k; f--) {
            if (buckets[f] == null) continue;
            for (int v : buckets[f]) {
                if (idx == k) break;
                out[idx++] = v;
            }
        }
        return out;
    }

    public static void main(String[] args) {
        System.out.println("twoSum = " + Arrays.toString(twoSum(new int[]{2, 7, 11, 15}, 9)));
        // [0, 1]

        char[][] board = {
            {'5','3','.','.','7','.','.','.','.'},
            {'6','.','.','1','9','5','.','.','.'},
            {'.','9','8','.','.','.','.','6','.'},
            {'8','.','.','.','6','.','.','.','3'},
            {'4','.','.','8','.','3','.','.','1'},
            {'7','.','.','.','2','.','.','.','6'},
            {'.','6','.','.','.','.','2','8','.'},
            {'.','.','.','4','1','9','.','.','5'},
            {'.','.','.','.','8','.','.','7','9'}
        };
        System.out.println("isValidSudoku = " + isValidSudoku(board));
        // true

        RandomSet rs = new RandomSet();
        rs.insert(1); rs.insert(2); rs.insert(3);
        rs.remove(2);
        System.out.println("RandomSet has 2 after remove? " + (rs.remove(2) || false));
        // false (returns false since 2 is gone)

        System.out.println("topKFrequent = "
            + Arrays.toString(topKFrequent(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        // [1, 2]
    }
}
