package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/6/16.
 */
public class PythagoreanTriplet {

    public static void main(String[] args) {
        findTriplet(1000);
    }

    static void findTriplet(int sum) {
        for (int a = sum - 2; --a > 0; ) {
            final int aSquared = a * a;
            for (int b = a; --b > 0; ) {
                final int c = sum - a - b;
                if (aSquared + b * b == c * c) {
                    System.out.println("a = " + a);
                    System.out.println("b = " + b);
                    System.out.println("c = " + c);
                    System.out.println("* = " + (long) a * b * c);
                    return;
                }
            }
        }
    }
}
