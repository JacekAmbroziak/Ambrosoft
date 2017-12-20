package com.ambrosoft.exercises;

/**
 * Created on 12/10/17
 * <p>
 * https://www.codewars.com/kata/coding-with-squared-strings
 */
public class CodeDecodeSquaredString {
    private static final char VTCHAR = (char) 11;

    private static String toSquaredString(final String s) {
        final int len = s.length();
        // find square size
        int n = 1;
        while (n * n < len) {
            ++n;
        }
        final int nSquared = n * n;
        final StringBuilder sb = new StringBuilder(nSquared + n - 1);   // original chars, padding up to n^2, n-1 newlines
        sb.append(s);   // start w/ original
        while (sb.length() < nSquared) {    // add padding
            sb.append(VTCHAR);
        }
        for (int nlIdx = n, count = n; --count > 0; nlIdx += n + 1) {   // insert newlines
            sb.insert(nlIdx, '\n');
        }
        return sb.toString();
    }

    private static int charCount(String s, char c) {
        final int len = s.length();
        int counter = 0;
        for (int found, start = 0; start < len && (found = s.indexOf(c, start)) >= 0; start = found + 1) {
            ++counter;
        }
        return counter;
    }

    private static String rotateSquared(final String s) {
        final int nLines = charCount(s, '\n') + 1;    // should be the same number as length of 1st line
        final String[] lines = s.split("\n");
        final StringBuilder sb = new StringBuilder();
        for (int col = 0; ; ) { // perform CW rotation by reading chars from columns into StringBuilder
            for (int row = nLines; --row >= 0; ) {
                sb.append(lines[row].charAt(col));
            }
            if (++col < nLines) {
                sb.append('\n');
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static String code(String s) {
        return s.length() < 2 ? s : rotateSquared(toSquaredString(s));
    }

    public static String decode(String s) {
        if (s.length() < 2) {
            return s;
        } else {
            final String[] lines = s.split("\n");
            final int nLines = lines.length;
            final StringBuilder sb = new StringBuilder();
            for (int col = nLines; --col >= 0; ) {  // read from array to StringBuilder
                for (int row = 0; row < nLines; ++row) {
                    sb.append(lines[row].charAt(col));
                }
            }
            while (sb.charAt(sb.length() - 1) == VTCHAR) {
                sb.setLength(sb.length() - 1);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        String s = "I.was.going.fishing.that.morning.at.ten.o'clock";
//        String s = "";
        String sq = toSquaredString(s);
        String rotCW = rotateSquared(sq);
        System.out.println("sq = " + sq);
        System.out.println("rotCW = " + rotCW);
        System.out.println("code = " + code(s));


        String dec = decode(rotCW);
        System.out.println("dec = " + dec);
        boolean test = s.equals(decode(code(s)));
        System.out.println("test = " + test);
    }
}
