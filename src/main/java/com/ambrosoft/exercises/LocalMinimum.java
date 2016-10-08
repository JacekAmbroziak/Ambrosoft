package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 8/13/16.
 */
public class LocalMinimum {


    public static void main(String[] args) {
        int[][] array = random2DArray(10);
        findLocalMin(array, 0, 0, 9, 9);
    }


    static void findLocalMin(int[][] a, int i0, int j0, int i1, int j1) {

        final int imid = i0 + (i1 - i0) / 2;
        final int jmid = j0 + (j1 - j0) / 2;

        int min = Integer.MAX_VALUE;


        int ii = 0, jj = 0;

        // upper row
        for (int i = i0; i <= i1; ++i) {
            if (a[i][j0] < min) {
                min = a[i][j0];
                ii = i;
                jj = j0;
            }
        }
        // bottom row
        for (int i = i0; i <= i1; ++i) {
            if (a[i][j1] < min) {
                min = a[i][j1];
                ii = i;
                jj = j1;
            }
        }
        // first column
        for (int j = j0 + 1; j <= j1; ++j) {
            if (a[i0][j] < min) {
                min = a[i0][j];
                ii = i0;
                jj = j;
            }
        }
        // last column
        for (int j = j0 + 1; j <= j1; ++j) {
            if (a[i1][j] < min) {
                min = a[i1][j];
                ii = i1;
                jj = j;
            }
        }
        // mid row
        for (int i = i0 + 1; i < i1; ++i) {
            if (a[i][jmid] < min) {
                min = a[i][jmid];
                ii = i;
                jj = jmid;
            }
        }
        // mid column
        for (int j = j0 + 1; j < j1; ++j) {
            if (a[imid][j] < min) {
                min = a[imid][j];
                ii = imid;
                jj = j;
            }
        }

        System.out.println("min = " + min);

    }

    private static int[][] random2DArray(int n) {
        final int[][] array = new int[n][n];
        final int[] numbers = new int[n * n];

        for (int i = numbers.length; --i >= 0; ) {
            numbers[i] = i;
        }
        shuffle(numbers);

        for (int i = n, k = 0; --i >= 0; ) {
            for (int j = n; --j >= 0; ) {
                array[i][j] = numbers[k++];
            }
        }
        return array;
    }

    static int[] random1DArray(final int n) {
        final int[] numbers = new int[n];
        for (int i = numbers.length; --i >= 0; ) {
            numbers[i] = i;
        }
        shuffle(numbers);
        return numbers;
    }

    static void shuffle(int[] array) {
        final Random rand = new Random();
        for (int i = array.length; --i > 0; ) {
            final int index = rand.nextInt(i + 1);
            final int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
