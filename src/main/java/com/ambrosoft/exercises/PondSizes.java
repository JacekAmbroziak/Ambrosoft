package com.ambrosoft.exercises;

/**
 * Created by jacek on 4/13/16.
 */
public class PondSizes {
    public static void main(String[] args) {

        int[][] a = {
                {0, 2, 1, 0},
                {0, 1, 0, 1},
                {1, 1, 0, 1},
                {0, 1, 0, 1}
        };

        pondSizes(a);
    }

    static void pondSizes(final int[][] a) {
        final int nRows = a.length;
        final int nCols = a[0].length;

        int start = 0;
        int counts[] = new int[nRows * nCols + 1];

        for (int i = 0; i < nRows; i++) {
            final int[] row = a[i];
            for (int j = 0; j < nCols; j++) {
                if (row[j] == 0) {
                    // look to the left
                    if (j > 0 && row[j - 1] < 0) {
                        row[j] = row[j - 1];
                    } else {
                        row[j] = --start;
                    }
                    final int index = -row[j];
                    ++counts[index];
                    // look up
                    if (i > 0) {
                        final int[] upper = a[i - 1];
                        if (j > 0 && upper[j - 1] < 0) {
                            counts[index] += counts[-upper[j - 1]]; // inherit count
                            counts[-upper[j - 1]] = 0;
                        }
                        if (upper[j] < 0) {
                            counts[index] += counts[-upper[j]];     // inherit count
                            counts[-upper[j]] = 0;
                        }
                        if (j + 1 < nCols && upper[j + 1] < 0) {
                            counts[index] += counts[-upper[j + 1]]; // inherit count
                            counts[-upper[j + 1]] = 0;
                        }
                    }
                }
            }
        }

        for (int i = counts.length; --i >= 0; ) {
            if (counts[i] > 0) {
                System.out.println("pond = " + counts[i]);
            }
        }
    }
}
