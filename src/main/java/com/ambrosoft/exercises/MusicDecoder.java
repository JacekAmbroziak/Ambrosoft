package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Arrays;

public class MusicDecoder {

    private static void range(int value, int last, int delta, ArrayList<Integer> values) {
        values.add(value);
        if (last > value) {
            while ((value += delta) <= last) {
                values.add(value);
            }
        } else {
            while ((value -= delta) >= last) {
                values.add(value);
            }
        }
    }

    static void decode(String codes, ArrayList<Integer> values) {
        for (String code : codes.split(",")) {
            final int star = code.indexOf('*');
            if (star > 0) {
                final Integer value = Integer.parseInt(code.substring(0, star));
                int count = Integer.parseInt(code.substring(star + 1));
                while (--count >= 0) {
                    values.add(value);
                }
            } else {
                final int dash = code.indexOf('-', 1);    // skip possible minus sign
                if (dash > 0) {
                    final int first = Integer.parseInt(code.substring(0, dash));
                    final int slash = code.lastIndexOf('/');
                    if (slash > 0) {
                        final int delta = Integer.parseInt(code.substring(slash + 1));
                        final int last = Integer.parseInt(code.substring(dash + 1, slash));
                        range(first, last, delta, values);
                    } else {
                        final int last = Integer.parseInt(code.substring(dash + 1));
                        range(first, last, 1, values);
                    }
                } else {
                    values.add(Integer.parseInt(code));
                }
            }
        }
    }

    static int[] decode(String codes) {
        final ArrayList<Integer> values = new ArrayList<>();
        decode(codes, values);
        final int size = values.size();
        final int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = values.get(i);
        }
        return result;
    }

    static void test(String codes) {
        System.out.println("codes = " + codes);
        System.out.println(Arrays.toString(decode(codes)));
    }

    public static void main(String[] args) {
        test("1,3-5,7-11,14,15,17-20");
        test("1,2*2,3");
        test("1,5-3,7");
    }
}
