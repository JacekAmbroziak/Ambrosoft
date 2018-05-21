package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/7/16.
 */
public class Collatz {
    private static int seqLen(long n) {
        int count = 1;
        while (n > 1) {
            n = n % 2 == 0 ? n / 2 : 3 * n + 1;
            ++count;
            System.out.println("n = " + n);
        }
        return count;
    }

    private static int seqLenFast(long n) {
        int count = 1;
        while (n > 1) {
            if ((n & 1) == 0) { // even
                n >>= 1;
            } else {
                // n = 2k + 1
                // 3*(2k + 1) + 1= 6k + 4 = 2(3k + 2) which is even so would be divided into 3k + 2
                //                n = k << 1 + k + 2;
                n += (n >> 1) + 1;
            }
            System.out.println("n = " + n);
            ++count;
        }
        return count;
    }

    public static void main(String[] args) {
        System.out.println("count = " + seqLen(11));
        System.out.println("count = " + seqLenFast(11));

        if (true) {
            return;
        }

        int start = -1;
        int max = 0;
        for (int i = 1; i < 1000000; ++i) {
            int count = seqLen(i);
            if (count > max) {
                max = count;
                start = i;
            }
        }
        System.out.println("start = " + start);
        System.out.println("max seq len = " + max);
    }
}
