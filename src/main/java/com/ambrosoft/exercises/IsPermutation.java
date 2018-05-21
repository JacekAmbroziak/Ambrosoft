package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by jacek on 12/4/16.
 * Updated 12/25/17
 * InterviewCake smart solution with a Set, unpairedChars, to which we can add/remove chars seen
 * Even counts cancel each other
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
        System.out.println("isPermutationOfPalindrome(string) = " + isPermutationOfPalindrome_InterviewLake(string));
    }

    static boolean isPermutationOfPalindrome(String string) {
        // a palindrome of odd length will have even numbers of all chars except one character can appear odd number of times
        // NOT JUST ONCE!
        // count chars
        // is alphabet limited?

        final HashMap<Character, Integer> counts = new HashMap<>();
        for (Character c : string.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int oddCount = 0;
        for (final Integer count : counts.values()) {
            if (count % 2 != 0) {
                if (oddCount != 0) {    // there was an odd count before!
                    return false;
                } else {
                    oddCount = count;
                }
            }
        }
        return string.length() % 2 == 0 ? oddCount == 0 : oddCount > 0;
    }

    static boolean isPermutationOfPalindrome_InterviewLake(String string) {
        final HashSet<Character> unpairedChars = new HashSet<>();
        for (final Character c : string.toCharArray()) {
            if (unpairedChars.contains(c)) {
                unpairedChars.remove(c);
            } else {
                unpairedChars.add(c);
            }
        }
        return unpairedChars.size() <= 1;
    }

    public static void main(String[] args) {
        test("kwas", "wask");
        test("", "");
        test("a", "a");
        test("a", "b");

        test2("cca");
        test2("ivicc");
        test2("aaa");
        test2("aaab");
        test2("aaabb");
        test2("aaabbc");
    }
}
