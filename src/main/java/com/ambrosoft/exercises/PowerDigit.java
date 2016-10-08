package com.ambrosoft.exercises;

import java.math.BigInteger;

/**
 * Created by jacek on 10/7/16.
 */
public class PowerDigit {
    public static void main(String[] args) {
        BigInteger bigInteger = BigInteger.valueOf(2).pow(1000);
        System.out.println("bigInteger = " + bigInteger);
        String digits = bigInteger.toString();
        int sum = 0;
        for (int i = digits.length(); --i >= 0; ) {
            sum += digits.charAt(i) - '0';
        }
        System.out.println("sum = " + sum);
    }
}
