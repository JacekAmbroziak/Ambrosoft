package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/5/16.
 */
public class LargestPalindrome {

    /*
        3-digit numbers: [100, 999]
     */

    static boolean isPalindromic(int n) {
        return isPalindrome(Integer.toString(n));
    }

    static boolean isPalindrome(final String string) {
        int length = string.length();
        switch (length) {
            case 0:
            case 1:
                return true;

            default:
                for (int i = 0, j = length - 1; i < j; ) {
                    if (string.charAt(i++) != string.charAt(j--)) {
                        return false;
                    }
                }
                return true;
        }
    }

    static int search() {
        int largest = Integer.MIN_VALUE;
        for (int i = 999; i >= 100; --i) {
            for (int j = i; j >= 100; --j) {
                if (isPalindromic(i * j)) {
                    System.out.println("palindromic = " + i * j);
                    largest = Math.max(largest, i * j);
                }
            }
        }
        return largest;
    }

    public static void main(String[] args) {
        System.out.println(isPalindromic(9009));
        System.out.println(isPalindromic(9019));
        int largest = search();
        System.out.println("largest = " + largest);
    }

}
