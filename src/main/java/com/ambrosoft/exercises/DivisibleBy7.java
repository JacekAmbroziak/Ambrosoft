package com.ambrosoft.exercises;

/**
 * Created on 12/20/17
 */
public class DivisibleBy7 {
    public static void main(String[] args) {
        for (int i = 1; i < 20; ++i) {
            System.out.println(String.format("%d %s", i * 7, Integer.toBinaryString(i * 7)));
        }
    }
}
