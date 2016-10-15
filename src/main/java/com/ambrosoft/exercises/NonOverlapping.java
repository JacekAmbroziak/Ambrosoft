package com.ambrosoft.exercises;

/**
 * Created by jacek on 9/5/16. Marina de Negru
 */
public class NonOverlapping {
    static void printNonOverlapping(String input) {
        printNonOverlapping("", input, 0);
    }

    static void printNonOverlapping(final String prefix, final String input, int depth) {
        System.out.print("\ndepth = " + depth + '\t');
//        System.out.println("number = " + number);
//        System.out.println("prefix = " + prefix);
        System.out.println(prefix + '[' + input + ']');
        // for inputs of length >= 2, find all splits
        for (int i = 1; i < input.length(); i++) {
            final String head = input.substring(0, i);
            final String tail = input.substring(i);
            printNonOverlapping(prefix + '(' + head + ')', tail, depth + 1);
        }
    }

    public static void main(String[] args) {
        printNonOverlapping("1234");
    }
}
