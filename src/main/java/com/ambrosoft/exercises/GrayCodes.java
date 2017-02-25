package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/9/17.
 */
public class GrayCodes {
    /*
        all numbers with n bits but arranged so that only one bit changes at a time
        Could be used to manipulate all subsets of a set by changing only one element at a time
        Also similar to Towers of Hanoi: p(n-1), then The Big One, then p(n-1) again
     */
    static String[] grayCodes(int len) {
        if (len == 0) {
            throw new IllegalArgumentException();
        } else if (len == 1) {
            return new String[]{"0", "1"};
        } else {
            final String[] smaller = grayCodes(len - 1);
            final int count = smaller.length;
            final String[] result = new String[2 * count];
            for (int i = 0; i < count; i++) {
                result[i] = "0" + smaller[i];
            }
            // list smaller in reverse, prepending "1"
            int index = count;
            for (int i = count; --i >= 0; ) {
                result[index++] = "1" + smaller[i];
            }
            return result;
        }
    }

    public static void main(String[] args) {
        String[] codes = grayCodes(4);
        System.out.println(Arrays.toString(codes));
    }
}
