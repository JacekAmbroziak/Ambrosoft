package com.ambrosoft.exercises;

/**
 * Created by jacek on 8/22/16.
 */

public class Finally {
    public static void main(String args[]) {
        System.out.println(test());
    }

    public static int test() {
        try {
            return 1;
        } finally {
            System.out.println("finally trumps return.");
//            return 2;
        }
    }
}
