package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 4/13/16.
 */
public class PatternMatching {
    public static void main(String[] args) {
        System.out.println(matches("aabab", "catcatgocatgo"));
//        System.out.println(matches("a", "catcatgocatgo"));
//        System.out.println(matches("ab", "catcatgocatgo"));
        System.out.println(matches("aba", "catcatgocatgo"));
    }




    /*

    can substrings of value be assigned to 'a' & 'b' so pattern recreates value?
    need to see how many repetitions are called for

    'catcatcatcat' should be matched by 'aaaa', 'abab', 'abaa' etc.


     */

    static boolean matches(final String pattern, final String value) {
        System.out.println("pattern = " + pattern);
        System.out.println("value = " + value);
        final int plen = pattern.length();
        if (plen == 0) {
            return false;
        } else if (plen == 1) {
            return true;    // a or b matches entire string
        } else {
            final int vlen = value.length();
            if (vlen == 0) {
                return true;    // with a, b empty strings
            } else {
                final int aCount = letterCount(pattern, 'a');
                final int bCount = plen - aCount;
                if (aCount == 0) {
                    return checkSimple(pattern, value, bCount);
                } else if (bCount == 0) {
                    return checkSimple(pattern, value, aCount);
                } else {
                    return pairs(vlen, aCount, bCount, pattern, value);
                }
            }
        }
    }

    private static boolean checkSimple(String pattern, String value, int bCount) {
        if (value.length() % bCount == 0) {
            int[] lengths = new int[bCount];
            Arrays.fill(lengths, value.length() % bCount);
            return checkMatch(value, lengths);
        }
        return false;
    }

    private static int[] mapIntoLengths(final String pattern, final int aLen, final int bLen) {
        final int plen = pattern.length();
        final int[] result = new int[plen];
        for (int i = 0; i < plen; i++) {
            result[i] = pattern.charAt(i) == 'a' ? aLen : -bLen;
        }
        return result;
    }

    private static int letterCount(final String pattern, final char c) {
        int count = 0;
        for (int i = pattern.length(); --i >= 0; ) {
            if (pattern.charAt(i) == c) {
                ++count;
            }
        }
        return count;
    }

    private static boolean pairs(final int vlen, final int aCount, final int bCount, String pattern, String value) {
        for (int aLen = 0; ; ++aLen) {
            final int bs = vlen - aCount * aLen;
            if (bs < 0) {
                return false;
            } else {
                if (bs % bCount == 0) {
                    final int bLen = bs / bCount;
                    System.out.println("aLen = " + aLen + "\tbLen = " + bLen);

                    int[] lengths = mapIntoLengths(pattern, aLen, bLen);
                    if (checkMatch(value, lengths)) {
                        System.out.println("matches ");
                        return true;
                    }
                }
            }
        }
    }

    private static boolean checkMatch(final String value, final int[] lengths) {
        String a = null;
        String b = null;
        int start = 0;
        for (final int length : lengths) {
            if (length == 0) {
                // ignore
            } else if (length > 0) {   // a
                final String sub = value.substring(start, start + length);
                if (a == null) {
                    a = sub;
                } else if (a.equals(sub)) {
                    // good
                } else {
                    return false;
                }
                start += length;
            } else {    // b
                final String sub = value.substring(start, start - length);
                if (b == null) {
                    b = sub;
                } else if (b.equals(sub)) {
                    // good
                } else {
                    return false;
                }
                start -= length;
            }
        }
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        return true;
    }
}
