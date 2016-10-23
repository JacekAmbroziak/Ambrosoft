package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by jacek on 10/16/16.
 */

public class LCS {

    // return length of a longest subsequence
    static int naiveLCS(final String x, final String y) {
        final char[] xa = x.toCharArray();
        final char[] ya = y.toCharArray();
        return naiveLCSaux(xa, xa.length, ya, ya.length);
    }

    // return length of a longest subsequence
    private static int naiveLCSaux(char[] xa, int xlen, char[] ya, int ylen) {
        if (xlen == 0 || ylen == 0) {
            return 0;
        } else {
            if (xa[xlen - 1] == ya[ylen - 1]) {
                return naiveLCSaux(xa, xlen - 1, ya, ylen - 1) + 1;
            } else {
                return Math.max(naiveLCSaux(xa, xlen - 1, ya, ylen), naiveLCSaux(xa, xlen, ya, ylen - 1));
            }
        }
    }

    static int[][] lcsTableOptim(final char[] xa, final char[] ya) {
        final int xlen = xa.length;
        final int ylen = ya.length;
        final int[][] l = new int[xlen + 1][ylen + 1];  // initialized by Java
        for (int i = 0; i < xlen; i++) {
            // this optimization obscures the DP logic
            final int[] row_i = l[i];
            final int[] row_next = l[i + 1];
            for (int j = 0; j < ylen; j++) {
                row_next[j + 1] = xa[i] == ya[j] ? row_i[j] + 1 : Math.max(row_next[j], row_i[j + 1]);
            }
        }
        return l;
    }

    static int[][] lcsTable(final String x, final String y) {
        final char[] xa = x.toCharArray();
        final char[] ya = y.toCharArray();
        final int xlen = xa.length;
        final int ylen = ya.length;
        final int[][] lcs = new int[xlen + 1][ylen + 1];  // initialized by Java to zeros

        for (int i = 0; i < xlen; i++) {
            for (int j = 0; j < ylen; j++) {
                if (xa[i] == ya[j]) {
                    lcs[i + 1][j + 1] = lcs[i][j] + 1;
                } else {
                    lcs[i + 1][j + 1] = Math.max(lcs[i + 1][j], lcs[i][j + 1]);
                }
            }
        }
        return lcs;
    }

    // original strings needed to check if selected chars were the same
    static void assembleLCS(int[][] lcs, char[] xa, char[] ya, int i, int j, StringBuilder sb) {
        if (lcs[i][j] > 0) {
            if (xa[i - 1] == ya[j - 1]) {
                assembleLCS(lcs, xa, ya, i - 1, j - 1, sb);
                sb.append(xa[i - 1]);   // the matching char gets appended
            } else if (lcs[i - 1][j] > lcs[i][j - 1]) {
                assembleLCS(lcs, xa, ya, i - 1, j, sb);
            } else {
                assembleLCS(lcs, xa, ya, i, j - 1, sb);
            }
        }
    }

    static String lcs(final String x, final String y) {
        return lcs(x.toCharArray(), y.toCharArray());
    }

    static String lcs(final char[] xa, final char[] ya) {
        final int[][] lcsl = lcsTableOptim(xa, ya);
        final StringBuilder sb = new StringBuilder();
        assembleLCS(lcsl, xa, ya, xa.length, ya.length, sb);
        return sb.toString();
    }

    static String randomString(final int length) {
        final Random random = new Random();
        final char[] letters = new char[length];
        for (int i = length; --i >= 0; ) {
            letters[i] = (char) ('A' + random.nextInt(26));
        }
        return new String(letters);
    }

    static String reverseString(final String input) {
        if (input.length() < 2) {
            return input;
        } else {
            final char[] chars = input.toCharArray();
            for (int i = 0, j = chars.length - 1; i < j; ) {
                final char temp = chars[i];
                chars[i++] = chars[j];
                chars[j--] = temp;
            }
            return new String(chars);
        }
    }

    static String longestPalindromicSubsequence(final String input) {
        // idea: LCS of input & reverse(input)
        final char[] xa = input.toCharArray();
        final char[] ya = new char[xa.length];
        // reverse xa chars into ya
        for (int i = 0, j = xa.length - 1; j >= 0; ) {
            ya[i++] = xa[j--];
        }
        return lcs(xa, ya);
    }

    static boolean isSubsequence(final String input, final String sub) {
        final int subLen = sub.length();
        if (subLen == 0) {
            return true;
        } else {
            final int inputLen = input.length();
            if (subLen > inputLen) {
                return false;
            } else {
                int j = subLen - 1;
                char c = sub.charAt(j); // first char to match
                for (int i = inputLen; --i >= 0; ) {
                    if (input.charAt(i) == c) {
                        if (j == 0) {   // last char consumed successfully
                            return true;
                        } else {
                            c = sub.charAt(--j);    // next char to match
                        }
                    }
                }
                return false;
            }
        }
    }

    static boolean isSubseqRecursive(final String input, final String sub) {
        if (sub.isEmpty()) {
            return true;
        } else if (input.isEmpty()) {
            return false;
        } else if (input.charAt(0) == sub.charAt(0)) {
            return isSubseqRecursive(input.substring(1), sub.substring(1));
        } else {
            return isSubseqRecursive(input.substring(1), sub);
        }
    }

    static void testLCS(final String x, final String y) {
        final String lcs1 = lcs(x, y);
        if (isSubsequence(x, lcs1) && isSubsequence(y, lcs1)) {
            // good
        } else {
            throw new Error();
        }
        final String lcs2 = lcs(y, x);
        if (isSubsequence(x, lcs2) && isSubsequence(y, lcs2)) {
            // good
        } else {
            throw new Error();
        }
    }

    static void naiveVsFast(int length) {
        String x = randomString(length);
        String y = randomString(length);
        System.out.println("naive len = " + naiveLCS(x, y));
        System.out.println("lcs(x,y) = " + lcs(x, y));
    }

    public static void main(String[] args) {
        final String x = "CATCGA";
        final String y = "GTACCGTCA";
        System.out.println("naive len = " + naiveLCS(x, y));
        final int[][] lcs = lcsTable(x, y);
        for (int[] row : lcs) {
            System.out.println(Arrays.toString(row));
        }

        naiveVsFast(10);

        String input1 = randomString(50);
        String input2 = randomString(50);

        System.out.println("input1 = " + input1);
        System.out.println("input2 = " + input2);
        System.out.println("lcs(input1,input2) = " + lcs(input1, input2));
        System.out.println("lcs(input2,input1) = " + lcs(input2, input1));
        final String lcs1 = lcs(input1, reverseString(input1));
        System.out.println("lcs(input1,reverseString(input1))\t\t\t = " + lcs1);
        System.out.println("longestPalindromicSubsequence(input1)\t\t = " + longestPalindromicSubsequence(input1));
        System.out.println("lcs1 = " + lcs1.length());
        for (int i = 0; i < 100; i++) {
            testLCS(randomString(1000), randomString(1000));
        }
    }
}
