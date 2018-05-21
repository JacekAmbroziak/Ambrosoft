package com.ambrosoft.exercises;

/**
 * Created on 1/1/18
 * <p>
 * InterviewCake
 */
public class ReverseWords {
    private static void reverseChars(char[] chars, int i, int j) {
        for (; i < j; ++i, --j) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
    }

    static String reverseWords(final String message) {
        final char[] chars = message.toCharArray();
        final int length = chars.length;
        // first reverse all the chars
        reverseChars(chars, 0, length - 1);
        // now reverse back each word
        for (int start = 0; start < length; ) {
            int end = start + 1;
            while (end < length && chars[end] != ' ') {
                ++end;
            }
            reverseChars(chars, start, end - 1);
            start = end + 1;
        }
        return new String(chars);
    }

    public static void main(String[] args) {
        String result = reverseWords("find you will pain only go you recordings security the into if");
        System.out.println("result = " + result);
    }
}
