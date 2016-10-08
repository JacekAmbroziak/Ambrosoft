package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/6/16.
 * <p>
 * <p>
 * <p>
 * The elegant and fast solution to this problem would not be the brute force one
 * but a good implementation of a sliding window, avoiding zeros
 */

public class ProductOfDigits {
    public static void main(String[] args) {
        // has to be long
        long maxProdOf13 = 9L * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9 * 9;
        System.out.println("n = " + maxProdOf13);
        System.out.println(Character.getNumericValue('0'));
        System.out.println(Character.getNumericValue('A'));

        String digits = "73167176531330624919225119674426574742355349194934" +
                "96983520312774506326239578318016984801869478851843" +
                "85861560789112949495459501737958331952853208805511" +
                "12540698747158523863050715693290963295227443043557" +
                "66896648950445244523161731856403098711121722383113" +
                "62229893423380308135336276614282806444486645238749" +
                "30358907296290491560440772390713810515859307960866" +
                "70172427121883998797908792274921901699720888093776" +
                "65727333001053367881220235421809751254540594752243" +
                "52584907711670556013604839586446706324415722155397" +
                "53697817977846174064955149290862569321978468622482" +
                "83972241375657056057490261407972968652414535100474" +
                "82166370484403199890008895243450658541227588666881" +
                "16427171479924442928230863465674813919123162824586" +
                "17866458359124566529476545682848912883142607690042" +
                "24219022671055626321111109370544217506941658960408" +
                "07198403850962455444362981230987879927244284909188" +
                "84580156166097919133875499200524063689912560717606" +
                "05886116467109405077541002256983155200055935729725" +
                "71636269561882670428252483600823257530420752963450";

        System.out.println("answer = " + largestProduct(digits, 13));
        System.out.println("answer = " + largestProductSliding(digits, 13));
        System.out.println("answer = " + largestProductSliding("12034", 2));
    }

    static long largestProduct(final String digits, final int length) {
        final int strlen = digits.length();
        if (length > strlen) {
            throw new IllegalArgumentException();
        } else {
            long max = 0;
            for (int start = 0; start + length < strlen; start++) {
                final long prod = productOfDigits(digits.substring(start, start + length));
                if (prod > max) {
                    max = prod;
                }
            }
            return max;
        }
    }


    static long largestProductSliding(final String digits, final int n) {
        final int strlen = digits.length();
        if (n > strlen) {
            throw new IllegalArgumentException();
        } else {
            long max = 0;
            SEARCH:
            for (int start = 0, end; (end = start + n) <= strlen; ) {
                // find 1st non-zero product of n consecutive digits
                long product = 1;
                for (int i = start; i < end; ) {
                    if ((product *= digits.charAt(i++) - '0') == 0) {
                        start = i;
                        continue SEARCH;
                    }
                }
                // we found some non-zero product
                if (product > max) {
                    max = product;
                }

                while (end < strlen) {
                    // slide the window
                    product /= digits.charAt(start++) - '0';    // guaranteed non-zero
                    product *= digits.charAt(end++) - '0';

                    if (product == 0) {
                        start = end;    // jump beyond '0'
                        continue SEARCH;
                    } else if (product > max) {
                        max = product;
                    }
                }
                return max;
            }
            return max;
        }
    }

    private static long productOfDigits(final String digits) {
        long product = 1L;
        for (int n = digits.length(); --n >= 0; ) {
            final char digit = digits.charAt(n);
            switch (digit) {
                case '0':
                    return 0;

                case '1':
                    continue;

                case '2':
                    product <<= 1;
                    break;

                case '3':
                    product = (product << 1) + product;
                    break;

                case '4':
                    product <<= 2;
                    break;

                default:
                    product *= digit - '0';
                    break;
            }
        }
        return product;
    }
}
