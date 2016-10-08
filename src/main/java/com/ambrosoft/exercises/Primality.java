package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/4/16.
 */

public class Primality {
    public static boolean isPrime(final long n) {
        if (n < 2) {
            return false;
        } else if (n <= 3) {
            return true;
        } else if (n % 2 == 0 || n % 3 == 0) {
            return false;
        } else {
            final long limit = (long) (Math.sqrt(n) + 1);
            for (long i = 5; i < limit; i += 6) {
                if (n % i == 0 || n % (i + 2) == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    static boolean isPrime5Plus(int n) {
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        } else {
            for (int i = 5; i * i <= n; i += 6) {
                if (n % i == 0 || n % (i + 2) == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    static int nthPrime(int n) {
        switch (n) {
            case 0:
                throw new IllegalArgumentException();

            case 1:
                return 2;

            case 2:
                return 3;

            default:
                // generate candidates, check primality, count
                n -= 2;
                for (int number = 5; ; number += 6) {
                    if (isPrime5Plus(number) && --n == 0) {
                        return number;
                    }
                    if (isPrime5Plus(number + 2) && --n == 0) {
                        return number + 2;
                    }
                }
        }
    }

    private static void test(int n) {
        System.out.println(n + " isPrime? " + isPrime(n));
    }

    public static void main(String[] args) {
        test(2);
        test(3);
        test(5);
        test(9);
        test(6857);
//        for (int i = 0; i < 30; i++) {
//            test(i);
//        }
        for (int i = 1; i < 30; i += 2) {
            test(i);
        }
        for (int i = 1; i < 100; i += 2) {
            if (isPrime(i)) {
                System.out.println(i + "\tisPrime");
            }
        }
        for (int i = 1; i < 20; i++) {
            System.out.println(i + "\t" + nthPrime(i));
        }
        System.out.println(10001 + "\t" + nthPrime(10001));
    }
}
