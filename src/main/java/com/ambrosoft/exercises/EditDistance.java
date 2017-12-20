package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/29/16.
 */
public class EditDistance {
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
        final int[][] cost = new int[alen + 1][blen + 1];

        // i,j represent prefixes of input strings ending at i,j  eg. a(0,i) exclusive

        // cost[0][0] is 0: no change between two empty strings
        // going from prefixes of a to an empty prefix of b requires a number of deletions
        for (int i = 1; i <= alen; i++) {
            cost[i][0] = DELETE * i;
        }
        // starting from empty prefix of a, we need a number of insertions
        for (int j = 1; j <= blen; j++) {
            cost[0][j] = INSERT * j;
        }

        for (int i = 1; i <= alen; i++) {
            final char achar = a.charAt(i - 1);
            for (int j = 1; j <= blen; j++) {
                if (achar == b.charAt(j - 1)) { // agreement
                    cost[i][j] = cost[i - 1][j - 1];    // just copy cost before agreement
                } else {
                    final int minLR = Math.min(cost[i - 1][j] + DELETE, cost[i][j - 1] + INSERT);
                    cost[i][j] = Math.min(minLR, cost[i - 1][j - 1] + REPLACE);
                }
            }
        }

        return cost[alen][blen];
    }

    public static void main(String[] args) {
        int res = editDistance("Jacek", "Marzena");
        System.out.println("res = " + res);
    }
}
