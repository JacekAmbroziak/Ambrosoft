package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/1/17.
 * <p>
 * Compute char counts
 * Find pairs of chars that have the same counts
 */
public class TwoCharacters {

    private static int[] charCounts(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); ++i) {
            ++counts[s.charAt(i) - 'a'];
        }
        return counts;
    }

    static int findLongestT(String s) {
        int maxLen = 0;
        final int[] counts = charCounts(s);
        for (int i = counts.length; --i > 0; ) {
            final int count1 = counts[i];
            if (count1 > 0) {
                for (int j = i; --j >= 0; ) {
                    final int count2 = counts[j];
                    if (count2 > 0) {
                        if (Math.abs(count1 - count2) <= 1) {
                            if (checkAlt(s, 'a' + i, 'a' + j)) {
                                if (count1 + count2 > maxLen) {
                                    maxLen = count1 + count2;
                                }
                            }
                        }
                    }
                }
            }
        }
        return maxLen;
    }

    static boolean checkAlt(String s, int a, int b) {
        int last = 0;
        for (int i = 0; i < s.length(); ++i) {
            int c = s.charAt(i);
            if (c == a || c == b) {
                if (c != last) {
                    last = c;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    static void test(String s) {
        System.out.println("s = " + s);
        int len = findLongestT(s);
        System.out.println(len);
    }

    public static void main(String[] args) {
//        test("kaws");
//        test("abracadabra");
        test("beabeefeab");
    }
}
