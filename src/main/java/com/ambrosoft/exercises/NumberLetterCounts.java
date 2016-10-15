package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/8/16.
 */

public class NumberLetterCounts {
    static String[] digits = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
    static String[] teens = {"eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen", "twenty"};
    static String[] tens = {"ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety", "hundred"};

    static String toEnglish(int n) {
        if (n < 1 || n > 1000) {
            throw new IllegalArgumentException();
        } else if (n == 1000) {
            return "one thousand";
        } else {
            final StringBuilder sb = new StringBuilder(32);

            if (n >= 100) {
                sb.append(digits[n / 100]).append(" hundred");
                n %= 100;
                if (n > 0) {
                    sb.append(" and ");
                }
            }

            if (n >= 20) {
                sb.append(tens[n / 10 - 1]);
                n %= 10;
                if (n > 0) {
                    sb.append(' ');
                }
            } else if (n > 10 && n < 20) {
                sb.append(teens[n - 11]);
                n = 0;
            }

            if (n > 0 && n <= 10) {
                sb.append(digits[n]);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i < 1001; i++) {
            System.out.println(toEnglish(i));
        }

        int count = 0;
        int maxLen = 0;
        for (int i = 1; i <= 1000; i++) {
            final int length = toEnglish(i).length();
            count += length;
            if (length > maxLen) {
                maxLen = length;
            }
        }
        System.out.println("count = " + count);
        System.out.println("maxLen = " + maxLen);
    }
}
