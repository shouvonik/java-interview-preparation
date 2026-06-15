package dsa.heaps.leetcode;

/**
 * LeetCode 621 — Task Scheduler
 * Difficulty: Medium   Tags: Array, Hash Table, Greedy, Sorting, Heap, Counting
 * URL: https://leetcode.com/problems/task-scheduler/
 *
 * <h2>Problem</h2>
 * Given a characters array {@code tasks}, representing tasks a CPU needs to do.
 * Each task takes 1 unit of time. There's also a positive integer {@code n}
 * representing the cooldown period between two same tasks (the same task must
 * not run within n consecutive intervals). Return the least number of units
 * of time required to finish all tasks.
 *
 * <h2>Examples</h2>
 * <pre>
 *   tasks = ['A','A','A','B','B','B'], n = 2  -> 8
 *     (A B idle A B idle A B  - or  A B C A B C A B C with idle slots)
 *   tasks = ['A','C','A','B','D','B'], n = 1  -> 6   (e.g. A B A B C D)
 *   tasks = ['A','A','A','B','B','B'], n = 0  -> 6
 * </pre>
 *
 * <h2>Approach — closed-form formula</h2>
 * Let {@code maxCount} = frequency of the most-common task, and {@code numMax}
 * = how many tasks share that frequency. The schedule is structured as
 * {@code maxCount - 1} cycles of length {@code n + 1}, plus a final group of
 * {@code numMax} tasks for the last occurrence of every "most-frequent" task:
 * <p>
 * {@code answer = (maxCount - 1) * (n + 1) + numMax}
 * <p>
 * But if we have so many other tasks that the cycles can't accommodate idles,
 * the answer is just {@code tasks.length}. So:
 * <p>
 * {@code answer = max(tasks.length, (maxCount - 1) * (n + 1) + numMax)}
 *
 * <h2>Why this works</h2>
 * The bottleneck is always the most-frequent task. Between two of its
 * occurrences, there must be exactly n other slots (filled by other tasks or
 * idle). The formula counts those exactly.
 *
 * <h2>Alternative — heap simulation</h2>
 * Use a max-heap of remaining counts. Each cycle, pop up to n+1 distinct
 * tasks; decrement and re-add the non-zero ones. Same answer but O(N log K).
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.heaps.leetcode.LC0621_TaskScheduler
 * </pre>
 */
public class LC0621_TaskScheduler {

    /** Closed-form — O(N). */
    public static int leastInterval(char[] tasks, int n) {
        int[] freq = new int[26];
        for (char t : tasks) freq[t - 'A']++;
        int maxCount = 0, numMax = 0;
        for (int f : freq) {
            if (f > maxCount) { maxCount = f; numMax = 1; }
            else if (f == maxCount) numMax++;
        }
        return Math.max(tasks.length, (maxCount - 1) * (n + 1) + numMax);
    }

    public static void main(String[] args) {
        System.out.println(leastInterval(new char[]{'A','A','A','B','B','B'}, 2));   // 8
        System.out.println(leastInterval(new char[]{'A','C','A','B','D','B'}, 1));   // 6
        System.out.println(leastInterval(new char[]{'A','A','A','B','B','B'}, 0));   // 6
    }
}
