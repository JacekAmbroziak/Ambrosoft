package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created on 12/24/17
 * <p>
 * InterviewLake:
 * didn't know that ONE value can be XORed by all these numbers and it will still remember one of them
 */
public class UniqueID {
    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(10, 200);
        int[] b = new int[a.length * 2 - 1];

        System.arraycopy(a, 0, b, 0, a.length);
        System.arraycopy(a, 1, b, a.length, a.length - 1);

        System.out.println(Arrays.toString(b));

        int val = 0;
        for (int n : b) {
            val ^= n;
        }
        System.out.println("val = " + val);
    }
}
