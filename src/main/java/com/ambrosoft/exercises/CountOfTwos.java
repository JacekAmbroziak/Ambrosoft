package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/26/16.
 */
public class CountOfTwos {
    // count occurrences of digit 2 in decimal representations of numbers 0..n

    // 2's occur in decimal representation when 2, 20, 200 are present in the number

    // brute force useful for testing
    static int bruteForce(int n) {
        int count = 0;
        for (int i = 0; i <= n; ++i) {
            count += countTwos(Integer.toString(i));
        }
        return count;
    }

    static int bruteForce2(int n) {
        int count = 0;
        for (int i = 0; i <= n; ++i) {
            count += countTwos(i);
        }
        return count;
    }

    private static int countTwos(String num) {
        int count = 0;
        for (int i = num.length(); --i >= 0; ) {
            if (num.charAt(i) == '2') {
                ++count;
            }
        }
        return count;
    }

    private static int countTwos(int num) {
        int count = 0;
        while (num > 0) {
            if (num % 10 == 2) {
                ++count;
            }
            num /= 10;
        }
        return count;
    }

    // less brute force: counting: how many times 2 as the last digit between 0 and n: from first 2, every 10
    // 2x is on 10 times from 20 to 29 every 100
    // 2xy is on 100 times from 200 to 299 every 1000
    // count how many full ranges make it + possibly last fractional

    static void test(int n) {
        System.out.println("bf1 n = " + n + "\t" + bruteForce(n));
        System.out.println("bf2 n = " + n + "\t" + bruteForce2(n));
    }

    static void test1(int n) {
        System.out.println("n = " + n + "\tcount " + countTwos(n));
    }

    static void test2(int n) {
        System.out.println("n = " + n + "\tcount " + countTwos(String.valueOf(n)));
    }

    public static void main(String[] args) {
        test1(22);
        test2(22);
        test(25);
    }
}
