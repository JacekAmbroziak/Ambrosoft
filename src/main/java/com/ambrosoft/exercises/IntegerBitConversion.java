package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/14/16.
 */
public class IntegerBitConversion {

    // rather trivial problem: how many bit flips difference between 2 numbers

    static void convert(int N, int M) {
        int count = 0;
        while (N != 0 || M != 0) {
            if (((N & 1) ^ (M & 1)) == 1) {
                ++count;
            }
            N >>>= 1;
            M >>>= 1;
        }
        System.out.println("count = " + count);
    }

    public static void main(String[] args) {
        convert(29, 15);
        convert(1777, 1777);
        convert(7, 8);
        convert(1, 2);
    }
}
