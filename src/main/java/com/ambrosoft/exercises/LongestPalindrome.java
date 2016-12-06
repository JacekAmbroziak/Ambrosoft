package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/22/16.
 */
public class LongestPalindrome {

    static int longestPalindromeLength(String input) {
        final char[] chars = input.toCharArray();
        final int length = chars.length;
        final int[][] m = new int[length][length];
        for (int i = length; --i >= 0; ) {
            Arrays.fill(m[i], -1);  // -1 stands for unknown; fill entire row
            m[i][i] = 1;    // atomic, length 1, trivial palindromes at every char
        }
        return longestPalindromeLength(chars, 0, length - 1, m);
    }

    private static int longestPalindromeLength(char[] chars, int i, int j, int[][] m) {
        // always: j >= i
        if (m[i][j] >= 0) { // subproblem already seen & solved
            return m[i][j];
        } else {
            final int len;
            if (i + 1 == j) {    // two chars
                len = chars[i] == chars[j] ? 2 : 0;
            } else {
                if (chars[i] == chars[j]) {   // palindrome contribution
                    len = 2 + longestPalindromeLength(chars, i + 1, j - 1, m);
                } else {    // search
                    len = Math.max(longestPalindromeLength(chars, i + 1, j, m), longestPalindromeLength(chars, i, j - 1, m));
                }
            }
            return m[i][j] = len;   // store and return
        }
    }

    // alternative recursion: characterize subproblems not with [i,...,j] but (i, length)


    static int longestPalindromeLength2(String input) {
        final char[] chars = input.toCharArray();
        final int length = chars.length;
        // create a triangular array as 'memo' to store max lengths for smaller intervals
        final int[][] m = new int[length][];
        for (int i = 0; i < length; ++i) {
            final int[] row = new int[length - i];
            Arrays.fill(row, -1);  // -1 stands for unknown; fill entire row
            row[0] = 1;    // atomic, length 1 (delta 0), trivial palindromes at every char
            m[i] = row;
        }
        // seeking solution for the whole length
        return longestPalindromeLength2(chars, 0, length - 1, m);
    }

    // delta is length - 1, so if first addresses first char of an interval, first+delta addresses the last
    // we are asking for the length of a longest palindromic subsequence lurking somewhere in that segment
    private static int longestPalindromeLength2(char[] chars, int start, int delta, int[][] memo) {
        if (memo[start][delta] >= 0) { // subproblem already seen & solved
            return memo[start][delta];
        } else {
            final int result;
            if (delta == 1) {    // two chars
                // in the segment is just 2 chars, figuring out max palindrome length is trivial
                result = chars[start] == chars[start + 1] ? 2 : 0;
            } else {
                // for longer segment we don't yet know what's inside (X)
                // but if ends of segment are same char, than answer will be 2 + X
                if (chars[start] == chars[start + delta]) {   // palindrome contribution
                    result = longestPalindromeLength2(chars, start + 1, delta - 2, memo) + 2;
                } else {    // search
                    result = Math.max(longestPalindromeLength2(chars, start + 1, delta - 1, memo),
                            longestPalindromeLength2(chars, start, delta - 1, memo));
                }
            }
            return memo[start][delta] = result;   // store and return
        }
    }

    static int iterative(final String input) {
        final char[] chars = input.toCharArray();
        final int length = chars.length;
        final int[][] m = new int[length][length];
        for (int i = length; --i >= 0; ) {
            m[i][i] = 1;    // atomic, length 1, trivial palindromes at every char
        }

        for (int i = length - 1; --i >= 0; ) {
            final int[] ithRow = m[i];
            final char ithChar = chars[i];
            for (int j = i + 1; j < length; ++j) {
                if (ithChar == chars[j]) {
                    ithRow[j] = m[i + 1][j - 1] + 2;
                } else {
                    ithRow[j] = Math.max(ithRow[j - 1], m[i + 1][j]);
                }
            }
        }
        return m[0][length - 1];
    }

    static void printArray(int[][] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.println(Arrays.toString(a[i]));
        }
    }

    static void test(String input) {
        System.out.println("input = " + input);
        System.out.println("len1 = " + longestPalindromeLength(input));
        System.out.println("len2 = " + longestPalindromeLength2(input));
        System.out.println("iter = " + iterative(input));
    }

    public static void main(String[] args) {
        test("abckayakxyz");
        test("GMXEYLPYIDZXPGPNHODNFHCXNMKANIWQZEKYCUDYKIYPGYOYNB");
        test("kayak");
        test("ala");
        test("aa");
        test("a");
        test("abcdefedcba");
        test("xxxxxxxxxxx");
    }
}
