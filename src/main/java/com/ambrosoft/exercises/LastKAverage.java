package com.ambrosoft.exercises;

import scala.actors.threadpool.Arrays;

/**
 * Created by jacek on 12/16/16.
 */
public class LastKAverage {
    /*
        average of max last K values
        approach: maintain running sum & circular buffer of values
     */

    static class KWindow {
        final int K;
        final int[] values;
        boolean isFull;
        double sum;
        int current;

        KWindow(int k) {
            K = k;
            values = new int[k];
        }

        double add(final int value) {
            if (isFull) {
                // current at oldest value
                sum -= values[current];
                sum += values[current++] = value;
                current %= K;
                return sum / K;
            } else {
                sum += value;
                values[current++] = value;
                isFull = current == K;
                if (isFull) {
                    current = 0;    // wrap
                    return sum / K;
                } else {
                    return sum / current;
                }
            }
        }

        double average() {
            if (isFull) {
                return sum / K;
            } else if (current > 0) {
                return sum / current;
            } else {
                throw new IllegalStateException("no values");
            }
        }
    }

    // compute average of length values from a, starting at start
    static double trustButVerify(int[] a, int start, int length) {
        double sum = 0.0;
        for (int i = start + length; --i >= start; ) {
            sum += a[i];
        }
        return sum / length;
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(100, 100);
        System.out.println(Arrays.toString(a));
        final KWindow kw = new KWindow(10);
        for (int i : a) {
            System.out.println("kw.add(i) = " + kw.add(i));
        }
        System.out.println("trustButVerify(a,90,10) = " + trustButVerify(a, 90, 10));
        System.out.println("kw.average() = " + kw.average());
    }
}
