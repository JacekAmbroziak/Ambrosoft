package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/22/16.
 */
public class Multiply {
    static int multiply(int a, int b) {
        return a == 0 || b == 0 ? 0 : multiply(a, b, 0);
    }

    private static int multiply(final int a, final int b, final int accumulator) {
        if (b == 0) {
            return accumulator;
        } else if ((b & 0x01) == 0) {  // b is even
            return multiply(a << 1, b >>> 1, accumulator);
        } else {
            return multiply(a, b - 1, accumulator + a);
        }
    }

    static void test(int a, int b) {
        int res = multiply(a, b);
        System.out.println(String.format("%d * %d = %d", a, b, res));
    }

    public static void main(String[] args) {
        test(3, 4);
        test(3, 4);
        test(5, 5);
        test(10, 10);
        test(1, 1);
    }
}
