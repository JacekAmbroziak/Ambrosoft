package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/2/17.
 */
public class Sort2DArray {
    static int[][] random2D(int nRows, int nCols, int bound) {
        final int[][] result = new int[nRows][];
        for (int row = 0; row < nRows; row++) {
            result[row] = Utils.createRandomArray(nCols, bound);
        }
        return result;
    }

    static void print2DArray(int[][] a) {
        for (int row = 0; row < a.length; row++) {
            System.out.println(Arrays.toString(a[row]));
        }
    }

    static void sort(final int[][] a) {
        final int nRows = a.length;
        final int nCols = a[0].length;
        final int[] sorted = new int[nRows * nCols];
        for (int row = 0, index = 0; row < nRows; row++, index += nCols) {
            System.arraycopy(a[row], 0, sorted, index, nCols);
        }
        Arrays.sort(sorted);

        // now assign sorted values back to original array in the order of diagonals
        // starting from left column & bottom row
        a[0][0] = sorted[0];    // corner
        int index = 1;
        // leftmost column (without corner element)
        for (int row = 1; row < nRows; row++) {
            // diagonal UP & RIGHT
            for (int r = row, c = 0; r >= 0 && c < nCols; ) {
                a[r--][c++] = sorted[index++];
            }
        }
        // bottom row (except element shared with leftmost column)
        for (int col = 1; col < nCols; col++) {
            // diagonal UP & RIGHT
            for (int r = nRows - 1, c = col; r >= 0 && c < nCols; ) {
                a[r--][c++] = sorted[index++];
            }
        }
    }

    public static void main(String[] args) {
        int[][] a = random2D(7, 4, 30);
        print2DArray(a);
        sort(a);
        System.out.println();
        print2DArray(a);
    }
}
