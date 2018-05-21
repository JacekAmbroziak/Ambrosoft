package com.ambrosoft.exercises;

/**
 * Created on 1/8/18
 */
public class LongestSubstringWithoutRepeatingChar {

    static int lengthOfLongestSubstring(String s) { // LeetCode
        if (s == null) {
            return 0;
        } else {
            final int length = s.length();
            if (length == 0) {
                return 0;
            } else {
                int maxLen = 0;
                final int[] charFreq = new int[256];
                int start = 0, end = 0; // window start & end
                int count = 0;
                while (end < length) {
                    if (charFreq[s.charAt(end++)]++ > 0) {
                        ++count;
                    }
                    while (count > 0) {
                        if (charFreq[s.charAt(start++)]-- > 1) {
                            --count;
                        }
                    }
                    maxLen = Math.max(maxLen, end - start);
                }
                return maxLen;
            }
        }
    }

    static void test(String s) {
        int res = lengthOfLongestSubstring(s);
        System.out.println(s + '\t' + res);
    }

    public static void main(String[] args) {
        test("kwas");
        test("kwaas");
        test("abababca");
        test("aaaaaaaa");
    }
}
