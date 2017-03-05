package com.ambrosoft.exercises;

/**
 * Created by jacek on 2/26/17.
 */
public class Integer2String {

    private static char digit(final int n) {
        if (n >= 0 && n < 10) {
            return (char) ('0' + n);
        } else if (n < 36) {
            return (char) ('a' + (n - 10));
        } else {
            throw new IllegalArgumentException();
        }
    }

    static String int2string(int n, int base) {
        if (n < base) {
            return "" + digit(n);
        } else {
            return int2string(n / base, base) + digit(n % base);
        }
    }

    static void test(int n, int base) {
        System.out.println(String.format("%d -> %s", n, int2string(n, base)));
    }

    public static void main(String[] args) {
        test(10, 10);
        test(10, 2);
        test(10, 16);
    }
}
