package com.ambrosoft.exercises;

import static com.ambrosoft.exercises.Primality.isPrime5Plus;

/**
 * Created by jacek on 10/6/16.
 */
public class SummationOfPrimes {
    public static void main(String[] args) {
        sumPrimes(2000000);
    }

    static void sumPrimes(int limit) {
        long sum = 2 + 3;
        for (int number = 5; ; number += 6) {
            if (isPrime5Plus(number)) {
                if (number < limit) {
                    sum += number;
                } else {
                    break;
                }
            }
            if (isPrime5Plus(number + 2)) {
                if (number + 2 < limit) {
                    sum += number + 2;
                } else {
                    break;
                }

            }
        }
        System.out.println("sum = " + sum);
    }
}
