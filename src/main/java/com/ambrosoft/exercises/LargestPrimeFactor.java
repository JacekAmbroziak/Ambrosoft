package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/4/16.
 */

public class LargestPrimeFactor {
    public static void main(String[] args) {
        long n = 600851475143L;
        boolean ip = Primality.isPrime(n);
        System.out.println("ip = " + ip);

        factor(n);
        factor2(n);
        factor3(n);

/*
        for (long k = n / 2; k > 3; --k) {
            if (Primality.isPrime(k) && n % k == 0) {
                System.out.println("k = " + k);
                break;
            }
        }
*/

    }


    static void factor(long number) {
        System.out.println("node = " + number);
        if (number % 2 == 0) {
            System.out.println("factor = " + 2);
            factor(number / 2);
        } else {
            for (long factor = 3; factor < number; factor += 2) {

                if (Primality.isPrime(factor) && number % factor == 0) {
                    System.out.println(number + "\tfactor = " + factor);
                    factor(number / factor);
                    break;
                }

            }
        }
    }

    /*
        go through successive growing primes p from 2
        if p divides n, n = n/p, repeat with the same p
        otherwise continue with bigger primes p*p <= n
        Biggest factor is last p or last n (last n?)
     */

    static void factor2(long number) {
        long factor = 2;
        while (number % factor == 0) {
            number /= factor;
        }
        for (factor = 3; factor * factor <= number; factor += 2) {
            if (Primality.isPrime(factor)) {
                while (number % factor == 0) {
                    number /= factor;
                    System.out.println("factor = " + factor);
                }
            }
        }
        System.out.println("node = " + number);
    }

    private static long factorOut(long number, long prime) {
        while (number % prime == 0) {
            number /= prime;
        }
        return number;
    }

    static void factor3(long number) {
        System.out.println("factor 3");
        number = factorOut(number, 2);
        number = factorOut(number, 3);
        long prime = 5;
        while (prime * prime <= number) {
            if (Primality.isPrime(prime)) {
                number = factorOut(number, prime);
            }
            if (Primality.isPrime(prime + 2)) {
                number = factorOut(number, prime + 2);
            }
            prime += 6;
        }
        System.out.println("node = " + number);
    }
}
