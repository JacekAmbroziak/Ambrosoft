package com.ambrosoft.exercises;

/**
 * Created on 12/9/17
 */
public class OddOrEven {
    public static String oddOrEven(int[] array) {
        boolean sumIsEven = true;
        for (int element : array) {
            // EO, OE -> O
            // OO, EE -> E
            sumIsEven = sumIsEven == (element % 2 == 0);
        }
        return sumIsEven ? "even" : "odd";
    }
}
