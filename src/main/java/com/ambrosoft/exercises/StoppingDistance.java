package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jacek on 8/29/16.
 */
/*
    Please use this Google doc to code during your interview. To free your hands for coding, we recommend that you use a headset or a phone with speaker option.

        f(x) = x/2 if x is even, 3x+1 otherwise
        x is a positive integer
        n, f(n), f(f(n)), …


        n = 1

        1, 4, 2, 1   stopping distance = 0
        2, 1, …      stopping distance =
        3, 10, 5, 16 (4 steps)-> 7

        Stopping distance of n = number of steps to 1
                             */

public class StoppingDistance {

    public static void main(String[] args) {
        for (int n = 3; n < 100; ++n) {
            System.out.println("n = " + n);
            System.out.println("res1 = " + stoppingDistance(n));
            System.out.println("res2 = " + stepsRec(n));
            System.out.println("--------------------------------");
        }
        System.out.println();
    }

    static final Map<Integer, Integer> distances = new HashMap<>();

    static {
        distances.put(1, 0);
    }

    static int stoppingDistance(int n) {
        Integer value = distances.get(n);
        if (value != null) {
            return value;
        } else if (n < 1) {
            throw new IllegalArgumentException();
        } else if (n == 1) {
            return 0;
        } else {
            final int arg = n;
            int steps = 0;
            List<Integer> prev = new ArrayList<>();
            do {
                ++steps;
                prev.add(n);
            } while ((n = f(n)) > 1);
            distances.put(arg, steps); // 3 -> 7 ( 10, 6) etc.
            for (int i = 0; i < prev.size(); ++i) { // [3, 10, 5, 16, 8, 4, 2]
                distances.put(prev.get(i), steps - i);
            }
            return steps;
        }
    }

    static int stepsRec(final int n) {
        Integer val = distances.get(n);
        if (val != null) {
            return val;
        } else {
            int result = 1 + stepsRec(f(n));
            distances.put(n, result);
            return result;
        }
    }

    private static int f(final int n) {
        return (n % 2 == 0) ? n / 2 : 3 * n + 1;
    }

    /*
        if n is odd, it is of the form 2m + 1
        3(2m + 1) + 1 = 6m + 3 + 1 = 6m + 4 = 2(3m + 2)
        so it is even, and so next number has to be 3m + 2

        n = 2m + 1
        m = (n - 1)/2
        3m + 2 = 3(n - 1)/2 + 2
     */
}

