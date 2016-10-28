package com.ambrosoft.exercises;

import java.util.PriorityQueue;

/**
 * Created by jacek on 10/23/16.
 */
public class Kth357 {
    /*
        k-th number divisible only by 3, 5, or 7

        approach:

        keep a seed of initial such numbers
        derive successors by multiplication
        paying attention to generating these numbers in monotonically growing sequence

        modification: (that's why thinking and analysis is so important)
        all such numbers have the form 3^i * 5^j * 7^k for growing triples of (i, j, k)

        powers:
        1, 3,  9, 27, 81
        1, 5, 25, 125
        1, 7, 49, 343

        these are pure contributions of our factors; can be ordered; other numbers are in-between

        every number is a multiplication of some walk down these 3 rows
            eg. 9 = 9,1,1
            15 = 3,5,1
            21 = 3,1,7


        (1,0,0)=3 (0,1,0)=5 (0, 0, 1)=7
        (2, 0, 0)=9 divide by 3, multiply by 5
        (1,1,0)=15 div 5, mult 7
        (1,0,1)=21
            div 3*7, mult 5*5
        (0,2,0)=25
            div 25, mult 27
        (3,0,0)=27
            div 3*3, mult 5*7
        (0,1,1)=35 (2,1,0)=45 (0,0,2)=49


        (9,1,1)
        (3,5,1)
        (3,1,7)
        (1,25,1)
        (27,1,1)
        (1,5,7)=35
        (9,5,1)=45
        (1,1,49)=49
        (3,25,1)=75

        intuition: divide by some N, multiply by next bigger M

        given (i, j, k), what is the next (i1, j1, k1)

        alternative: for (i,j,k), what is the previous number?
            can't have all exponents incremented

        3D DP?

        next in sequence:

        Hint from solution in Gayle's book:

        next number will be
            3 * some prev num, or
            5 * some prev num, or
            7 * some prev num

            bigger than current, but smallest among them

        so indeed we can imagine filling out an array
        I got stuck trying to figure out a transform on coefficient triples



     */


    static int power(int n, int pow) {
        int result = 1;
        while (--pow >= 0) {
            result *= n;
        }
        return result;
    }

    static int nthMultiple(int n) {
        int i = 0, j = 0, k = 0;
        int result = 0;

        while (--n >= 0) {

            final int p3 = power(3, i);
            final int p5 = power(5, j);
            final int p7 = power(7, k);
            result = p3 * p5 * p7;

            // transform i,j,k
            // divide result by factor1 and multiply by factor2, directly bigger
            // such factors are previously found nth multiples!


        }

        return result;

    }

    static int dp(final int n) {
        final int[] seq = new int[n + 1];

        int lastVal = 1, lastj = 1;
        seq[1] = 1;
        int jspan = 0;
        for (int i = 2; i <= n; ++i) {
            int value = 0;
            // search for value minimally bigger than last of the form (3,5,7) * seq[j]
            // in order 7,5,3 so we can continue with same j
            // optimization: can start w/ j closer to seq[j] ~= last/3
            int j = lastj;
//            jspan = j;
            for (; ; ++j) {
                if (seq[j] * 7 > lastVal) {
                    value = seq[j] * 7;
                    break;
                }
            }
            lastj = j;  // store this value for next i
            // TODO: is it really safe to start with last value of j? YES
            // seq[j] * 7 was > last, seq[j] * 5 if also > last will be the first such
            // because seq[j-1] * 7 < last => seq[j-1] * 5 must be < last
            for (; ; ++j) {
                if (seq[j] * 5 > lastVal) {
                    value = Math.min(value, seq[j] * 5);
                    break;
                }
            }
            for (; ; ++j) {
                if (seq[j] * 3 > lastVal) {
                    value = Math.min(value, seq[j] * 3);
                    break;
                }
            }
//            jspan = j - jspan;
//            System.out.println("i = " + i + "\tjspan = " + jspan);
            seq[i] = lastVal = value;
        }

        return seq[n];
    }

    static int gayle1(int n) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        int val = 1;
        queue.add(1);

        for (int i = 0; i < n; i++) {
            val = queue.poll();

            // generate future values
            queue.add(val * 3);
            queue.add(val * 5);
            queue.add(val * 7);

            // queue nonempty; remove elts equal to last removed
            while (queue.peek() == val) {
                queue.remove();
            }
        }
        return val;
    }

    static void test(int n) {
        int result = dp(n);
        System.out.println(n + "\tresult = " + result);
        System.out.println("gayle1(n) = " + gayle1(n));
    }

    public static void main(String[] args) {
        test(500);
        gayle1(40);

        for (int i = 1; i < 40; i++) {
            test(i);
        }
    }
}
