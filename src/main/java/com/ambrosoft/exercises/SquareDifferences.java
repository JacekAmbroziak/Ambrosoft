package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/5/16.
 */
public class SquareDifferences {
    static int findDiff(int n) {
        int squares = 0, sum = 0;
        for (int i = n; i > 0; --i) {
            squares += i * i;
            sum += i;
        }
        return sum * sum - squares;
    }

    public static void main(String[] args) {
        System.out.println("diff = " + findDiff(100));
    }
}
