package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/13/16.
 */
public class InsertBits {

    static int insert(int N, int M, int i, int j) {
        if (i < j && i >= 0 && j > 0 && i < 31 && j < 31) {
            // one way is to clear the 'window' [j,j] with a mask then shift and OR M
            int len = j - i + 1;
            int wOnes = ((1 << len) - 1) << i;

            System.out.println(Integer.toBinaryString(wOnes));
            System.out.println(Integer.toBinaryString(~wOnes));
            return (N & ~wOnes) | (M << i);
        } else {
            throw new IllegalArgumentException();
        }
    }

    static int insert2(int N, int M, int i, int j) {
        // shift right-left to clear window, OR the M, shift to position, OR original bits after window
        return ((((N >>> j) << (j - i)) | M) << i) | (N & ((1 << i) - 1));
    }

    public static void main(String[] args) {
        int N = 0b10000000000;
        int M = 0b10011;
        int i = 3;
        int j = 7;

        System.out.println(Integer.toBinaryString(N));
        System.out.println(Integer.toBinaryString(M));

        int result = insert(N, M, i, j);
        System.out.println(Integer.toBinaryString(result));
        System.out.println(Integer.toBinaryString(insert2(N, M, i, j)));
    }
}
