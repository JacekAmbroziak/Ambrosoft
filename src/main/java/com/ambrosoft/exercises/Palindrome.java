package com.ambrosoft.exercises;

/**
 * Created by jacek on 7/12/16.
 */
public class Palindrome {
    // English word or sentence
    static boolean isPalindrome(String input) {
        if (input == null) {
            throw new IllegalArgumentException();
        } else if (input.isEmpty()) {
            return false;
        } else if (input.length() == 1) {
            return true;
        } else {
            final int length = input.length();
            for (int i = 0, j = length - 1; i < j; ++i, --j) {
                for (; i < length; ++i) {
                    if (Character.isLetterOrDigit(input.charAt(i))) {
                        break;
                    }
                }
                if (j == length) {
                    break;
                }
                for (; j >= 0; --j) {
                    if (Character.isLetterOrDigit(input.charAt(j))) {
                        break;
                    }
                }
                if (j < 0) {
                    break;
                }
                if (sameChar(input, i, j)) {
                    // OK
                } else {
                    return false;
                }
            }
            return true;
        }
    }

    static boolean sameChar(String string, int index1, int index2) {
        return Character.toLowerCase(string.charAt(index1)) == Character.toLowerCase(string.charAt(index2));
    }

    static void test(String input) {
        System.out.println("input = " + input + "\t isP " + isPalindrome(input));
    }

    public static void main(String[] args) {
        test("");
        test("RADAR");
        test("RADARa");
        test("Amor, Roma!");
        test(",,.,.,  !");
    }
}
