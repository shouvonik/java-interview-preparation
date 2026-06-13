package dsa.sorting.leetcode;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * LeetCode 253 — Meeting Rooms II
 * Difficulty: Medium   Tags: Array, Two Pointers, Sorting, Heap
 * URL: https://leetcode.com/problems/meeting-rooms-ii/  (premium)
 *
 * <h2>Problem</h2>
 * Given an array of meeting time intervals {@code intervals[i] = [start_i, end_i]},
 * return the minimum number of conference rooms required.
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>1 &le; intervals.length &le; 10^4</li>
 *   <li>0 &le; start &lt; end &le; 10^6</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[0,30],[5,10],[15,20]]  -> 2
 *   [[7,10],[2,4]]            -> 1   (no overlap)
 *   [[9,10],[4,9],[4,17]]     -> 2
 *   [[1,5],[5,10]]            -> 1   (back-to-back, no overlap)
 * </pre>
 *
 * <h2>Approach 1 — Heap of room end times</h2>
 * Sort by start. Use a min-heap of "earliest room finish time". For each
 * meeting: if the heap's smallest end &le; this start, reuse that room
 * (poll it). Always push the new meeting's end. Heap size at the end is the
 * answer; track the running max.
 *
 * <h2>Approach 2 — Two-pointer sweep on starts/ends</h2>
 * Sort starts and ends separately. Walk both: when a meeting starts before
 * any active one ends, allocate a new room; else "release" an old room.
 * The peak rooms in use is the answer. O(n log n) sort + O(n) sweep.
 *
 * <h2>Why both work</h2>
 * Each gives the same count because both track the maximum number of meetings
 * happening simultaneously. The heap version is more intuitive operationally;
 * the two-pointer version is the cleanest if you only need the count.
 *
 * <h2>"Back-to-back"</h2>
 * Use {@code &le;} (not strict {@code &lt;}) when comparing earliest end to
 * current start, so a room that ends at 5 can host a meeting starting at 5.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Single meeting — 1 room.</li>
 *   <li>All disjoint — 1 room.</li>
 *   <li>All identical [s,e] — n rooms.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0253_MeetingRoomsII
 * </pre>
 */
public class LC0253_MeetingRoomsII {

    /** Heap approach — O(n log n). */
    public static int minMeetingRooms(int[][] intervals) {
        if (intervals.length == 0) return 0;
        int[][] sorted = intervals.clone();
        Arrays.sort(sorted, (a, b) -> Integer.compare(a[0], b[0]));
        PriorityQueue<Integer> rooms = new PriorityQueue<>();  // min-heap of end times
        for (int[] iv : sorted) {
            if (!rooms.isEmpty() && rooms.peek() <= iv[0]) rooms.poll();
            rooms.offer(iv[1]);
        }
        return rooms.size();
    }

    /** Two-pointer sweep — O(n log n). */
    public static int minMeetingRoomsSweep(int[][] intervals) {
        int n = intervals.length;
        int[] starts = new int[n], ends = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = intervals[i][0];
            ends[i]   = intervals[i][1];
        }
        Arrays.sort(starts);
        Arrays.sort(ends);

        int rooms = 0, peak = 0;
        int j = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && ends[j] <= starts[i]) { rooms--; j++; }   // some room freed
            rooms++;
            peak = Math.max(peak, rooms);
        }
        return peak;
    }

    public static void main(String[] args) {
        System.out.println(minMeetingRooms(new int[][]{{0, 30}, {5, 10}, {15, 20}})); // 2
        System.out.println(minMeetingRooms(new int[][]{{7, 10}, {2, 4}}));            // 1
        System.out.println(minMeetingRooms(new int[][]{{9, 10}, {4, 9}, {4, 17}}));   // 2
        System.out.println(minMeetingRooms(new int[][]{{1, 5}, {5, 10}}));            // 1

        System.out.println(minMeetingRoomsSweep(new int[][]{{0, 30}, {5, 10}, {15, 20}})); // 2
    }
}
