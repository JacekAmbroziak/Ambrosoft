package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/4/16.
 */

public class EvenFibonacci {
    static long countEvenFibo(int limit) {
        long sum = 2;
        int a = 1, b = 2, num;
        while ((num = a + b) < limit) {
            a = b;
            b = num;
            if (num % 2 == 0) {
                sum += num;
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        long sum = countEvenFibo(4000000);
        System.out.println("sum = " + sum);
    }
}
