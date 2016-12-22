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

        Using hints from GeeksForGeeks
     */

    private static int lcs(char[] a, char[] b) {
        return lcs(a, a.length, b, b.length);
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

    static int lcs(String a, String b) {
        return lcs(a.toCharArray(), b.toCharArray());
    }

    static void test(String a, String b) {
        int result = lcs(a, b);
        System.out.println(String.format("%s %s -> %d", a, b, result));
    }

    public static void main(String[] args) {
//        test("photograph", "tomography");
        test("jacek1xab", "jacek2xabcccc");
    }
}
