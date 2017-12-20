package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/29/16.
 */
public class EditDistanceVariant {
    /*
        2D DP table needed
        working with all prefixes from 0-length
    */

    static final int INSERT = 1;
    static final int REPLACE = 1;
    static final int DELETE = 1;

    static int editDistance(String a, String b) {
        final int alen = a.length();
        final int blen = b.length();

        if (alen == 0) {
            return blen * INSERT;
        } else if (blen == 0) {
            return alen * INSERT;
        } else if (alen == 1 && blen == 1) {
            return a.charAt(0) == b.charAt(0) ? 0 : REPLACE;
        } else {
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < alen; ++i) {
                String a1 = a.substring(0, i);
                String a2 = a.substring(i);

//                System.out.println("a1 = " + a1 + '\t' + a2);

                for (int j = 1; j < blen; j++) {
                    String b1 = b.substring(0, j);
                    String b2 = b.substring(j);

                    int dist = editDistance(a1, b1) + editDistance(a2, b2);
                    if (dist < min) {
                        min = dist;
                    }
                }
            }
            return min;
        }
    }

    static int editDistance(char[] a, int aStart, int aEnd, char[] b, int bStart, int bEnd) {
        return 0;
    }

    public static void main(String[] args) {
        int res = editDistance("Jacek", "Marzena");
        System.out.println("res = " + res);
    }
}
