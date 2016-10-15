package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/9/16.
 * https://projecteuler.net/problem=21
 */
public class AmicableNumbers {
    static int sumOfProperDivisors(final int n) {
        final int sqrt = (int) Math.sqrt(n);
        int sum = 1;
        for (int i = 2; i < sqrt; ++i) {
            if (n % i == 0) {
                sum += i + n / i;
            }
        }
        if (sqrt > 1 && n % sqrt == 0) {
            sum += sqrt;
        }
        return sum;
    }

    public static void main(String[] args) {
        System.out.println("sum = " + sumOfProperDivisors(220));
        System.out.println("sum = " + sumOfProperDivisors(284));

        final int N = 10000;
        int[] allSums = new int[N];

        for (int i = 1; i < N; ++i) {
            allSums[i] = sumOfProperDivisors(i);
        }
        int amicables = 0;
        for (int i = 1; i < N; ++i) {
            final int sum = allSums[i];
            if (sum < N && allSums[sum] == i && sum != i) {
                System.out.println("amicable i = " + i);
                amicables += i;
            }
        }
        System.out.println("done " + amicables);
    }
}
