package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 12/21/16.
 */
public class StringShuffle {

    // this strategy doesn't work
    // again a problem with Greed
    // in the following we always try to make progress with X first
    /*
        example: CCA + CBC = CCBCCA if we take letters from either input
        but the algorithm below will consume CC from both X and Z
        leaving A + CBC = BCCA which will fail
     */
    static boolean isShuffle(String x, String y, String z) {
        final int xlen = x.length(), ylen = y.length(), zlen = z.length();
        if (xlen + ylen == zlen) {
            int xi = 0, yi = 0, zi = 0;
            while (zi < zlen)
                if (xi < xlen && x.charAt(xi) == z.charAt(zi)) {
                    ++xi;
                    ++zi;
                } else if (yi < ylen && y.charAt(yi) == z.charAt(zi)) {
                    ++yi;
                    ++zi;
                } else {
                    return false;
                }
            return true;
        } else {
            return false;
        }
    }

    /*
        Looking for a DP strategy
        may turn out that we'll need a 2D boolean array and true in right-lower corner will mean YES

        if Z is a shuffle of X & Y -- prefix of Z is a shuffle of some prefixes of X and Y

        for every prefix of Z, of length zlen, it is a shuffle of some PX & PY of total length zlen
     */

    static boolean isShuffleDP(String x, String y, String z) {
        final int xlen = x.length(), ylen = y.length(), zlen = z.length();
        boolean[][] memo = new boolean[xlen + 1][ylen + 1];
        return isShuffleDP(x, y, z, memo, zlen);
    }

    private static boolean isShuffleDP(String x, String y, String z, boolean[][] memo, int zlen) {
        return false;
    }

    private static String prefix(String input) {
        return input.substring(0, input.length() - 1);
    }

    /*
        the correct solution acknowledges the fact that if Z's char agrees with both X and Y
        that char could have come from either X or Y leading to 2 different subproblems
        Effectively, it reconstructs a possible order of taking chars from either input
        If chars were distinct in both inputs that would never be a problem!
     */

    static boolean isShuffleDPrecursive(String x, String y, String z) {
        final int xlen = x.length(), ylen = y.length(), zlen = z.length();
        if (zlen == 0) {
            return xlen == 0 && ylen == 0;
        } else if (xlen == 0) {
            return z.equals(y);
        } else if (ylen == 0) {
            return z.equals(x);
        } else {    // all non-empty
            final char lastChar = z.charAt(zlen - 1);
            final boolean xe = x.charAt(xlen - 1) == lastChar;
            final boolean ye = y.charAt(ylen - 1) == lastChar;
            if (xe || ye) {
                if (xe && ye) { // both last characters agree: need to consider 2 subproblems
                    return isShuffleDPrecursive(prefix(x), y, prefix(z)) || isShuffleDPrecursive(x, prefix(y), prefix(z));
                } else if (xe) {
                    return isShuffleDPrecursive(prefix(x), y, prefix(z));
                } else {
                    return isShuffleDPrecursive(x, prefix(y), prefix(z));
                }
            } else {
                return false;
            }
        }
    }

    static String shuffle(final String x, final String y) {
        int xlen = x.length(), ylen = y.length();
        final StringBuilder sb = new StringBuilder(xlen + ylen);
        final Random random = new Random(System.currentTimeMillis());
        int xi = 0, yi = 0;
        while (xlen > 0 && ylen > 0) {
            final int dice = random.nextInt(xlen + ylen);
            // pick next char randomly from 1st or 2nd string
            // chances proportional to lengths remaining
            if (dice < xlen) {
                sb.append(x.charAt(xi++));
                --xlen;
            } else {
                sb.append(y.charAt(yi++));
                --ylen;
            }
        }
        if (xlen > 0) {
            sb.append(x, xi, x.length());
        }
        if (ylen > 0) {
            sb.append(y, yi, y.length());
        }
        return sb.toString();
    }

    static void test(String x, String y, String z) {
        System.out.println(String.format("old %s %s %s -> %b", x, y, z, isShuffle(x, y, z)));
        System.out.println(String.format("rec %s %s %s -> %b", x, y, z, isShuffleDPrecursive(x, y, z)));
    }

    public static void main(String[] args) {
        test("chocolate", "chips", "cchocohilaptes");
        test("chocolate", "chips", "chocochilatspe");

        String res = shuffle("chocolate", "chipschips");
        System.out.println("res = " + res);
        test("chocolate", "chipschips", res);
    }
}
