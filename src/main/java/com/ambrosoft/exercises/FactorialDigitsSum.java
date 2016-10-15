package com.ambrosoft.exercises;

import java.math.BigInteger;

/**
 * Created by jacek on 10/8/16.
 * <p>
 * https://projecteuler.net/problem=20
 */
public class FactorialDigitsSum {
    public static void main(String[] args) {
        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= 100; ++i) {
            fact = fact.multiply(BigInteger.valueOf(i));
        }

        System.out.println("fact = " + fact);

        int sum = 0;
        final String digits = fact.toString();
        for (int i = digits.length(); --i >= 0; ) {
            sum += digits.charAt(i) - '0';
        }
        System.out.println("sum = " + sum);
    }
}
