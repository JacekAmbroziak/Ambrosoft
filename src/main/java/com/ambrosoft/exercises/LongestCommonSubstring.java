package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/21/16.
 */
public class LongestCommonSubstring {
    /*
        Looking for a DP solution, inspired by longest common subsequence
        This time common chars need to be consecutive
        What are the smaller subproblems (in prefixes)?
        What's the recurrence?
        It may turn out that like in the previous cases: DP finds length, but solution needs to be reconstructed
        There can be several common substrings

        length of common substring ending here?

        Imagine (draw examples) maximal common substrings identified and highlighted
            they end at some indexes (where they achieve max length)

        Using hints from GeeksForGeeks:
            DP tabularize lengths of common substrings ending at all indexes i, j
     */

    private static int lcs(char[] a, char[] b) {
        return lcsDP(a, a.length, b, b.length);
    }

    static int lcs(char[] a, int alen, char[] b, int blen) {
        if (alen == 0 || blen == 0) {
            return 0;
        }

        if (a[alen - 1] == b[blen - 1]) {   // can be part of common substring ending here

            int left = lcs(a, alen - 1, b, blen);
            int right = lcs(a, alen, b, blen - 1);
            int center = lcs(a, alen - 1, b, blen - 1) + 1;

            final int lr = Math.max(left, right);
            if (lr > center) {
                return lr;
            } else {
                return center;
            }

        } else {
            return 0;
//            return Math.max(lcs(a, alen - 1, b, blen), lcs(a, alen, b, blen - 1));
        }
    }

    static int lcsDP(char[] a, int alen, char[] b, int blen) {
        int maxLen = 0; // track the maximum length as DP table is populated
        int endInA = 0;
        final int[][] lenAtPrefix = new int[alen + 1][blen + 1];
        // populate the table
        // i,j play the role of (exclusive) 'end'
        for (int i = 1; i <= alen; i++) {
            for (int j = 1; j <= blen; j++) {
                if (a[i - 1] == b[j - 1]) {
                    lenAtPrefix[i][j] = lenAtPrefix[i - 1][j - 1] + 1;
                    if (lenAtPrefix[i][j] > maxLen) {
                        maxLen = lenAtPrefix[i][j];
                        endInA = i;
                    }
                }
                // if not equal, common length drops to 0 (so no change to initialized table)
            }
        }
        if (endInA > 0) {
            String substring = String.valueOf(a, endInA - maxLen, maxLen);
            System.out.println("substring = " + substring);
        }
        return maxLen;
    }

    static int lcs(String a, String b) {
        return lcs(a.toCharArray(), b.toCharArray());
    }

    static void test(String a, String b) {
        int result = lcs(a, b);
        System.out.println(String.format("%s %s -> %d", a, b, result));
    }

    public static void main(String[] args) {
//        test("photograph", "tomography");
        test("jaek1xab", "jaek2xabcccc");
    }
}
