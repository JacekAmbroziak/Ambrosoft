package com.ambrosoft.exercises;

/**
 * Created by jacek on 4/12/16.
 */
public class ContiguousMax {
    public static void main(String[] args) {

        System.out.println("sum = " + sum(new int[]{2, -8, 3, -2, 4, -10}));
        System.out.println("sum = " + sum(new int[]{100, -1, -1, -1, 100}));
        System.out.println("sum = " + sum(new int[]{-1, -1, -1, -1}));
        System.out.println("sum = " + sum(new int[]{-3}));
        System.out.println("sum = " + sum(new int[]{-3, -2}));
        System.out.println("sum = " + sum(new int[]{-3, -2, -1}));
        System.out.println("sum = " + sum(new int[]{-1, -1, -1, 0}));
        System.out.println("sum = " + sum(new int[]{-1, -1, -1, 0, 1}));
        System.out.println("sum = " + sum(new int[]{-1, -1, -1, 0, 1, 2}));
        System.out.println("sum = " + sum(new int[]{3, -2}));
        System.out.println("sum = " + sum(new int[]{3, -2, 3}));
        System.out.println("sum = " + sum(new int[]{1}));
        System.out.println("sum = " + sum(new int[]{1, 2}));
        System.out.println("sum = " + sum(new int[]{1, 2, 3}));
        System.out.println("sum = " + sum(new int[]{1, 2, 3, -1}));
        System.out.println("sum = " + sum(new int[]{2, 3, -8, -1, 2, 4, -2, 3}));

        int[] a = Utils.createRandomArray(20);
        Utils.limitArray(a, 100);
        System.out.println("sum(a) = " + sum(a));
    }

    static String sum(final int[] a) {
        int jra = sumJRA(a);
        int gayle = getMaxSum(a);
        final String msg = jra + "\tgayle = " + gayle;
//        System.out.println(msg);
        return msg;
    }

    static int sumJRA(final int[] a) {
        // seq doesn't start with negative or end w/ negative
        // except when all VALUES (so far) have been negative
        // then the seq is just a single biggest negative node
        // when the first positive is seen it replaces negative seq & starts new sequence
        // "positive balance"
        // pos neg+ can have positive balance that would add to following pos if any
        // if balance falls below zero we can stop expanding, remember last backup max
        // "best in the past"?
        // if extending never recovers, we can go back to last max
        // remember last maximum group
        // we drop negative balances

        int balance = a[0];
        int lastMax = a[0];

        for (int i = 1; i < a.length; i++) {
            final int val = a[i];
            if (val > 0) {
                if (balance > 0) {
                    balance += val;
                } else {
                    balance = val;
                }
                if (balance > lastMax) {
                    lastMax = balance;
                }
            } else if (val == 0) {
                if (lastMax < 0) {
                    lastMax = val;
                }
                if (balance < 0) {
                    balance = 0;
                }
            } else {    // val < 0
                if (val > lastMax) {
                    lastMax = val;
                    balance = val;
                } else if (balance > 0) {
                    balance += val;
                } else {
                    balance = val;
                }
            }
        }

        return Math.max(balance, lastMax);
    }

    // Gayle's (Kadane)
    static int getMaxSum(final int[] a) {
        int maxsum = 0, sum = 0;
        for (int val : a) {
            sum += val;
            if (sum > maxsum) {
                maxsum = sum;
            } else if (sum < 0) {
                sum = 0;
            }
        }
        return maxsum;
    }
}
