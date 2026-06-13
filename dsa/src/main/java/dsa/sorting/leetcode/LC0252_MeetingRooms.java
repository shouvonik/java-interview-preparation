package dsa.sorting.leetcode;

import java.util.Arrays;
import java.util.Comparator;

/**
 * LeetCode 252 — Meeting Rooms
 * Difficulty: Easy   Tags: Array, Sorting
 * URL: https://leetcode.com/problems/meeting-rooms/  (premium)
 *
 * <h2>Problem</h2>
 * Given an array of meeting time intervals where {@code intervals[i] = [start, end]},
 * determine if a person could attend all meetings (i.e. no two intervals overlap).
 *
 * <h2>Constraints</h2>
 * <ul>
 *   <li>0 &le; intervals.length &le; 10^4</li>
 *   <li>intervals[i].length == 2</li>
 *   <li>0 &le; start &lt; end &le; 10^6</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * <pre>
 *   [[0,30],[5,10],[15,20]]  -> false   (overlaps exist)
 *   [[7,10],[2,4]]            -> true    (back-to-back is OK if no overlap)
 *   [[1,5],[5,10]]            -> true    (end-touching-start is allowed)
 *   []                         -> true
 * </pre>
 *
 * <h2>Approach</h2>
 * Sort by start time, then check adjacent pairs. If any earlier meeting's end
 * is strictly greater than the next meeting's start, return false. Otherwise true.
 *
 * <h2>Why "strictly greater"</h2>
 * Typical convention: a meeting ending at 10 and another starting at 10 don't
 * overlap (you walk out as the next walks in). So {@code &gt;}, not {@code &ge;}.
 *
 * <h2>Edge cases</h2>
 * <ul>
 *   <li>Empty or single interval — return true (no conflict possible).</li>
 *   <li>Two intervals with same start — they always overlap; sorted order
 *       puts them adjacent and the check catches it.</li>
 * </ul>
 *
 * Run:
 * <pre>
 *   mvn -pl dsa exec:java -Dexec.mainClass=dsa.sorting.leetcode.LC0252_MeetingRooms
 * </pre>
 */
public class LC0252_MeetingRooms {

    /** Sort + linear scan — O(n log n). */
    public static boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i - 1][1] > intervals[i][0]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(canAttendMeetings(new int[][]{{0, 30}, {5, 10}, {15, 20}})); // false
        System.out.println(canAttendMeetings(new int[][]{{7, 10}, {2, 4}}));            // true
        System.out.println(canAttendMeetings(new int[][]{{1, 5}, {5, 10}}));            // true
        System.out.println(canAttendMeetings(new int[][]{}));                            // true
    }
}
