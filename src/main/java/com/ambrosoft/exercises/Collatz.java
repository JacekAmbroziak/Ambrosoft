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
        }
        return count;
    }

    public static void main(String[] args) {
//        int count = seqLen(13);
//        System.out.println("count = " + count);

        int start = -1;
        int max = 0;
        for (int i = 1; i < 1000000; ++i) {
            int count = seqLen(i);
            if (count > max) {
                max = count;
                start = i;
            }
        }
        System.out.println("max = " + max);
        System.out.println("start = " + start);
    }
}
