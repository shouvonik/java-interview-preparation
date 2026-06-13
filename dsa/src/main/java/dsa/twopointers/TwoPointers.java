package dsa.twopointers;

import java.util.*;

/**
 * Two Pointers — opposite-ends, fast/slow, and read/write patterns.
 *
 * Run: java dsa.twopointers.TwoPointers
 */
public class TwoPointers {

    // ---------------------------------------------------------------------
    // 1) Opposite ends — 2Sum on a sorted array (LeetCode 167).
    // ---------------------------------------------------------------------
    // Indices are 1-based per LeetCode convention.
    static int[] twoSumSorted(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) return new int[]{left + 1, right + 1};
            if (sum < target) left++;
            else              right--;
        }
        return new int[]{-1, -1};
    }

    // ---------------------------------------------------------------------
    // 2) Fast / slow — linked list cycle detection (Floyd's).
    // ---------------------------------------------------------------------
    public static class Node {
        int val; Node next;
        Node(int val) { this.val = val; }
    }

    static boolean hasCycle(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // 3) Read / write — remove duplicates from sorted array in-place.
    // ---------------------------------------------------------------------
    // write index trails read; write only when value changes.
    static int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int write = 1;
        for (int read = 1; read < nums.length; read++) {
            if (nums[read] != nums[read - 1]) nums[write++] = nums[read];
        }
        return write;
    }

    // ---------------------------------------------------------------------
    // 4) 3Sum — find unique triplets that sum to zero (LeetCode 15).
    // ---------------------------------------------------------------------
    // Sort, then fix nums[i] and 2-pointer on the suffix. Skip duplicates at
    // both the outer and inner level to avoid duplicate triplets.
    static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;        // skip dup pivot
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    out.add(List.of(nums[i], nums[left], nums[right]));
                    left++; right--;
                    while (left < right && nums[left] == nums[left - 1]) left++;
                    while (left < right && nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // 5) Trapping rain water — O(n) time, O(1) space (LeetCode 42).
    // ---------------------------------------------------------------------
    // At any index, water trapped = min(maxLeft, maxRight) - height[i].
    // Two pointers from both ends, advancing the smaller side, tracking running max.
    static int trapRainWater(int[] heights) {
        int left = 0, right = heights.length - 1;
        int maxLeft = 0, maxRight = 0, total = 0;
        while (left < right) {
            if (heights[left] < heights[right]) {
                if (heights[left] >= maxLeft) maxLeft = heights[left];
                else                          total += maxLeft - heights[left];
                left++;
            } else {
                if (heights[right] >= maxRight) maxRight = heights[right];
                else                            total += maxRight - heights[right];
                right--;
            }
        }
        return total;
    }

    // ---------------------------------------------------------------------
    // Demo
    // ---------------------------------------------------------------------
    public static void main(String[] args) {
        System.out.println("twoSumSorted = "
            + Arrays.toString(twoSumSorted(new int[]{2, 7, 11, 15}, 9)));
        // [1, 2]

        // Build a list 1->2->3->4->2 (cycle: 4 points back to the second 2)
        Node n1 = new Node(1), n2 = new Node(2), n3 = new Node(3), n4 = new Node(4);
        n1.next = n2; n2.next = n3; n3.next = n4; n4.next = n2;
        System.out.println("hasCycle = " + hasCycle(n1));
        // true

        int[] dedup = {1, 1, 2, 2, 3, 3, 3, 4};
        int newLen = removeDuplicates(dedup);
        System.out.println("removeDuplicates len = " + newLen
            + ", prefix = " + Arrays.toString(Arrays.copyOf(dedup, newLen)));
        // 4, [1, 2, 3, 4]

        System.out.println("threeSum = " + threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        // [[-1, -1, 2], [-1, 0, 1]]

        int[] heights = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
        System.out.println("trapRainWater = " + trapRainWater(heights));
        // 6
    }
}
