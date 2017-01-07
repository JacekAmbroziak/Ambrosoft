package com.ambrosoft.exercises;

import static com.ambrosoft.exercises.Sort2DArray.*;

/**
 * Created by jacek on 1/5/17.
 */
public class Array2DSearch {
    /*
        Find an element in 2D sorted array
        Generalization of binary search doesn't work all that well
        Except we can think in quadrants
     */

    static class Coords {
        static final Coords ORIGIN = new Coords(0, 0);
        static final Coords NOTFOUND = new Coords(-1, -1);

        final int row;
        final int col;

        Coords(int r, int c) {
            row = r;
            col = c;
        }

        int valueIn(int[][] a) {
            return a[row][col];
        }

        int valueInLast(int[][] a) {
            return a[row - 1][col - 1];
        }

        Coords mipoint(Coords hi) {
            return new Coords((row + hi.row) / 2, (col + hi.col) / 2);
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", row, col);
        }

        boolean smallerThan(Coords hi) {
            return row <= hi.row && col <= hi.col;
        }
    }

    static void find(final int[][] sorted, int val) {
        int nRows = sorted.length;
        int nCols = sorted[0].length;

        Coords coords = find(sorted, val, Coords.ORIGIN, new Coords(nRows, nCols));
        System.out.println("coords = " + coords);
    }

    // TODO consider 3 remaining quadrants
    static Coords find(int[][] sorted, int val, Coords lo, Coords hi) {
        if (val >= lo.valueIn(sorted) && val <= hi.valueInLast(sorted)) {
            if (lo.smallerThan(hi)) {
                Coords mid = lo.mipoint(hi);
                int sample = mid.valueIn(sorted);
                if (val < sample) {
                    return find(sorted, val, lo, mid);
                } else if (val > sample) {
                    return find(sorted, val, mid, hi);
                } else {
                    return mid;
                }
            } else {
                return Coords.NOTFOUND;
            }
        } else {
            return Coords.NOTFOUND;
        }
    }

    static void findZigZag(int[][] sorted, int val) {
        final int nRows = sorted.length;
        final int nCols = sorted[0].length;
        // start in the upper right corner
        int r = 0;
        int col = nCols - 1;
        int[] row = sorted[r];
        do {
            while (col >= 0 && row[col] > val) {
                --col;
            }
            if (col < 0) {
                break;
            } else if (row[col] == val) {
                System.out.println("found " + r + " " + col);
                return;
            } else {
                row = ++r < nRows ? sorted[r] : null;
            }
        } while (row != null);
        System.out.println("not found");
    }

    public static void main(String[] args) {
        int[][] a = random2D(7, 4, 30);
        print2DArray(a);
        sort(a);
        System.out.println();
        print2DArray(a);
        findZigZag(a, 15);
    }
}
