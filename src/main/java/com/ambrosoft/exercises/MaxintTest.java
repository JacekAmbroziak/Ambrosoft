package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/5/16.
 */
public class MaxintTest {
    public static void main(String[] args) {
        int a = Integer.MAX_VALUE / 2 + Integer.MAX_VALUE / 4;
        System.out.println("a = " + a);
        int b = a + 2;
        System.out.println("b = " + b);
        System.out.println("(a+b)/2 = " + (a + b) / 2);
        System.out.println("(a+b)>>>1 = " + ((a + b) >>> 1));
        System.out.println("a+(b-a)/2 = " + (a + (b - a) / 2));
        System.out.println("Integer.MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("(Integer.MAX_VALUE>>>1 = " + (Integer.MAX_VALUE >>> 1));
        System.out.println("((Integer.MAX_VALUE+1000)>>>1 = " + ((Integer.MAX_VALUE + 1000) >>> 1));
    }
}
