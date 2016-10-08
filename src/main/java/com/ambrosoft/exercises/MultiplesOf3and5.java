package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/4/16.
 */
public class MultiplesOf3and5 {
    public static void main(String[] args) {
        System.out.println("count = " + countMultiples(1000));
    }

    static long countMultiples(int n) {
        long count = 0;
        while (--n > 0) {
            if (n % 3 == 0 || n % 5 == 0) {
                count += n;
            }
        }
        return count;
    }
}
