package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/4/16.
 */
public class IsPermutation {
    // assuming non-empty strings
    static boolean isPermutation(String a, String b) {
        if (a.length() == b.length()) {
            return toSorted(a).equals(toSorted(b));
        }
        return false;
    }

    static String toSorted(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    static void test(String a, String b) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("isPermutation(a, b) = " + isPermutation(a, b));
    }

    static void test2(String string) {
        System.out.println("string = " + string);
        System.out.println("isPermutationOfPalindrome(string) = " + isPermutationOfPalindrome(string));
    }

    static boolean isPermutationOfPalindrome(String string) {
        // a palindrome of odd length will have even numbers of all chars except one
        // count chars
        // is alphabet limited?
        int length = string.length();




        return false;
    }

    public static void main(String[] args) {
        test("kwas", "wask");
        test("", "");
        test("a", "a");
        test("a", "b");

        test2("cca");
    }
}
