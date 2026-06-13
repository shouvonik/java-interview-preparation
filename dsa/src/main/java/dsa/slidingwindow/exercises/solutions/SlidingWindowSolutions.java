package dsa.slidingwindow.exercises.solutions;

/**
 * Reference solutions for SlidingWindowExercises.
 */
public class SlidingWindowSolutions {

    // Exercise 1 — Fixed window sum / k.
    static double findMaxAverage(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        long best = sum;
        for (int right = k; right < nums.length; right++) {
            sum += nums[right] - nums[right - k];
            best = Math.max(best, sum);
        }
        return best / (double) k;
    }

    // Exercise 2 — Variable window; valid when (len - maxCharCount) <= k.
    // Trick: never shrink maxCharCount when window contracts — the answer can
    // only grow when a *better* maxCharCount appears, so a stale value is OK.
    static int characterReplacement(String s, int k) {
        int[] count = new int[26];
        int left = 0, maxCount = 0, best = 0;
        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'A']++;
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);
            while ((right - left + 1) - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            best = Math.max(best, right - left + 1);
        }
        return best;
    }

    // Exercise 3 — Fixed window of size s1.length() over s2; compare char counts.
    static boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) return false;
        int[] target = new int[26];
        int[] window = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            target[s1.charAt(i) - 'a']++;
            window[s2.charAt(i) - 'a']++;
        }
        if (java.util.Arrays.equals(target, window)) return true;
        for (int right = s1.length(); right < s2.length(); right++) {
            window[s2.charAt(right) - 'a']++;
            window[s2.charAt(right - s1.length()) - 'a']--;
            if (java.util.Arrays.equals(target, window)) return true;
        }
        return false;
    }

    // Exercise 4 — Minimum window substring with two counters and a "have" tally.
    static String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        int[] need = new int[128];
        for (char c : t.toCharArray()) need[c]++;
        int required = t.length();                       // chars still missing

        int left = 0, bestLen = Integer.MAX_VALUE, bestStart = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (need[c] > 0) required--;
            need[c]--;                                   // negative = surplus
            while (required == 0) {                      // window is valid
                if (right - left + 1 < bestLen) {
                    bestLen = right - left + 1;
                    bestStart = left;
                }
                char lc = s.charAt(left);
                need[lc]++;
                if (need[lc] > 0) required++;            // crossed back into deficit
                left++;
            }
        }
        return bestLen == Integer.MAX_VALUE ? "" : s.substring(bestStart, bestStart + bestLen);
    }

    public static void main(String[] args) {
        System.out.println("findMaxAverage = " + findMaxAverage(new int[]{1, 12, -5, -6, 50, 3}, 4));
        // 12.75

        System.out.println("characterReplacement = " + characterReplacement("AABABBA", 1));
        // 4

        System.out.println("checkInclusion = " + checkInclusion("ab", "eidbaooo"));
        // true

        System.out.println("minWindow = " + minWindow("ADOBECODEBANC", "ABC"));
        // BANC
    }
}
