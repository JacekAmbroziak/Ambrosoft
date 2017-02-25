package com.ambrosoft.exercises;

/**
 * Created by jacek on 1/9/17.
 */
public class LCSPrinceton {
    /*
        Sedgewick considers suffixes, not prefixes, which would work well with lists
     */

    static String lcs(String s, String t) {
        int n = s.length();
        int m = t.length();
        int[][] opt = new int[n + 1][m + 1];

        // bottom up filling of optimal length table
        for (int i = n; --i >= 0; ) {
            for (int j = m; --j >= 0; ) {
                if (s.charAt(i) == t.charAt(j)) {
                    opt[i][j] = opt[i + 1][j + 1] + 1;
                } else {
                    opt[i][j] = Math.max(opt[i + 1][j], opt[i][j + 1]);
                }
            }
        }

        final StringBuilder sb = new StringBuilder(Math.min(n, m));
        for (int i = 0, j = 0; i < n && j < m; ) {
            if (s.charAt(i) == t.charAt(j)) {
                sb.append(s.charAt(i));
                ++i;
                ++j;
            } else if (opt[i + 1][j] >= opt[i][j + 1]) {
                ++i;
            } else {
                ++j;
            }
        }
        return sb.toString();
    }

    static void test(String s, String t) {
        String lcs = lcs(s, t);
        System.out.println(String.format("%s, %s -> %s", s, t, lcs));
    }

    public static void main(String[] args) {
        test("abcba", "abba");
        test("- - G G C - - A - C C A C G",
                "A C G G C G G A T - - A C G");
    }
}
