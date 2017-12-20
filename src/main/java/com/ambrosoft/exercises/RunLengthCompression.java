package com.ambrosoft.exercises;

/**
 * Created on 12/18/17
 * <p>
 * Smartsheet interview on whiteboard
 * Interviewer was messing up with my coding, uncharacteristically
 */
public class RunLengthCompression {

    static String compress(final String in) {
        final int len = in.length();
        if (len < 2) {
            return in;
        } else {
            final StringBuilder sb = new StringBuilder();
            char last = in.charAt(0);
            int count = 1;  // how many chars == to last have we seen?
            for (int i = 1; i < len; i++) {
                final char current = in.charAt(i);
                if (current == last) {
                    ++count;
                } else {    // char change; output last, perhaps preceded by count
                    if (count > 1) {
                        sb.append(count);
                        count = 1;
                    }
                    sb.append(last);
                    last = current;
                }
            }
            if (count > 1) {
                sb.append(count);
            }
            return sb.append(last).toString();
        }
    }

    static void test(String in) {
        System.out.println(String.format("%s -> %s", in, compress(in)));
    }

    public static void main(String[] args) {
        test("a");
        test("aa");
        test("aaa");
        test("aaaa");
        test("ab");
        test("abc");
        test("abbc");
        test("abbcdd");
        test("abbcccdddd");
        test("XYZT");
        test("XYZTTabc");
    }

}
