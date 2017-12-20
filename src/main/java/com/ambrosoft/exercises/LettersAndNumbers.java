package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 10/24/16.
 */
public class LettersAndNumbers {
    /*
        array with letters and numbers
        find longest subarray with equal number of letters and numbers

        similar to palindrome, but where palindrome makes progress on recognizing identical chars
        this one progresses by seeing letter/digit or digit/letter at ends

        subarray will have even length
        if digits or letters not present -> empty sequence

        seems, however, that one can simply count all digits and all letters, take min and mult by 2

        Importance of asking questions: in Gayle's book we look for contiguous subarray
        Can this be solved with DP?
        If seq is balanced it may look like ABABAB, but also AAABBB
        If external are A..B then what's inside must also be balanced
        or BABAABAB   BBAAAABB
        If external are same (& in the 2nd case also starting and ending with BB) then the induction step
        is not apparent... such interval is a concatenation of 2 or more balanced intervals

     */


    static int longestBalanced(String input) {
        final char[] chars = input.toCharArray();
        final int length = chars.length;
        final int[][] memo = new int[length][];
        // create a triangular array
        for (int i = 0; i < length; ++i) {
            final int[] row = new int[length - i];
            Arrays.fill(row, -1);  // -1 stands for unknown; fill entire row
            row[0] = 0;    // atomic, length 1 (delta 0), trivial non-balanced-seqs at every char
            memo[i] = row;
        }
        if (length % 2 == 1) {  // longest can't be odd
            // so -- either left (skip last) or right (skip first)
            final int lft = longestBalanced(chars, 0, length - 2, memo);
            final int rgt = longestBalanced(chars, 1, length - 2, memo);
            return Math.max(lft, rgt);
        } else {    // consider all chars
            return longestBalanced(chars, 0, length - 1, memo);
        }
    }

    static boolean isBalancing(char c1, char c2) {
        if (Character.isDigit(c1)) {
            return Character.isLetter(c2);
        } else if (Character.isLetter(c1)) {
            return Character.isDigit(c2);
        } else {
            throw new Error();
        }
    }

    // delta is length - 1, so if first addresses first char of an interval, first+delta addresses the last
    // always called with odd delta
    private static int longestBalanced(char[] chars, int start, int delta, int[][] memo) {
//        System.out.println("first = " + first + "\tdelta = " + delta + " last " + (first + delta));
        if (memo[start][delta] >= 0) { // subproblem already seen & solved
            return memo[start][delta];
        } else {
            int result;
            if (delta == 1) {    // two chars
                result = isBalancing(chars[start], chars[start + 1]) ? 2 : 0;
            } else {

                if (isBalancing(chars[start], chars[start + delta])) {   //  contribution
                    final int sub = longestBalanced(chars, start + 1, delta - 2, memo);
                    result = sub != 0 ? sub + 2 : 0;
                } else {    // not balanced ends
                    int smaller = longestBalanced(chars, start + 1, delta - 2, memo);
                    if (smaller > 0) {
                        result = smaller;
                    } else {
                        result = 0;
                        for (int k = 1; k < delta; k += 2) {
//                            System.out.println("dividing k = " + k);
                            if (longestBalanced(chars, start, k, memo) > 0 && longestBalanced(chars, start + k + 1, delta - k - 1, memo) > 0) {
                                result = delta + 1; // i.e. length
                            }
                        }
                    }
                }
            }
//            System.out.println("result = " + result);
            return memo[start][delta] = result;   // store and return
        }
    }

    static void test(String input) {
        System.out.println("input = " + input);
        System.out.println("len2 = " + longestBalanced(input));
    }

    public static void main(String[] args) {
        test("a1a2bc");
        test("aa1a2bca");
        test("a1bc4");
        test("a1a1a1");
        test("aaa111");
        test("aa1111aa");
        test("aa11");
//        test("ab4b6csghshshsh1516171");
    }
}
