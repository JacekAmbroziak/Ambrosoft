package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 1/20/17.
 */
public class RandomInterval {
    static double[] randomIntervals(int n, double bound) {
        double[] result = new double[n];
        final ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        for (int i = n; --i >= 0; ) {
            result[i] = threadLocalRandom.nextDouble(0.0, bound);
        }
        return result;
    }

    static double sum(double[] nums) {
        double sum = 0.0;
        for (double num : nums) {
            sum += num;
        }
        return sum;
    }

    static int randomIntervalLinear(double[] intervals) {
        final double limit = sum(intervals);
        final double dice = ThreadLocalRandom.current().nextDouble(0, limit);
        int index = 0;
        double end = intervals[0];
        while (true)
            if (dice < end) {
                return index;
            } else {
                end += intervals[++index];
            }
    }

    static void testSearch(double[] intervals) {
        final double limit = sum(intervals);
        double[] copy = Arrays.copyOf(intervals, intervals.length);
        toCumulative(copy);

        for (int count = 1000000; --count >= 0; ) {
            final double dice = ThreadLocalRandom.current().nextDouble(0, limit);
            int index = 0;
            double end = intervals[0];
            while (true) {
                if (dice < end) {
                    break;
                } else {
                    end += intervals[++index];
                }
            }
            // index should be set in the correct interval


            int bi = binarySearch(copy, dice, 0, intervals.length);
            System.out.println(String.format("index = %d, bi = %d", index, bi));
            if (bi != index) {
                throw new Error();
            }
        }
    }


    static double toCumulative(double[] lengths) {
        double sum = lengths[0];
        for (int i = 1; i < lengths.length; i++) {
            sum = lengths[i] += sum;
        }
        System.out.println(Arrays.toString(lengths));
        return sum;
    }

    static int binarySearch(int[] a, int val, int start, int end) {
        if (start < end) {
            final int mid = start + (end - start) / 2;
            if (a[mid] < val) {
                return binarySearch(a, val, mid + 1, end);
            } else {
                return binarySearch(a, val, start, mid);
            }
        } else {
            return start;
        }
    }

    static int binarySearch(double[] a, double val, int start, int end) {
        if (start < end) {
            final int mid = start + (end - start) / 2;
            if (a[mid] < val) {
                return binarySearch(a, val, mid + 1, end);
            } else {
                return binarySearch(a, val, start, mid);
            }
        } else {
            return start;
        }
    }

    static void testBinarySearch(int[] a, int val) {
        System.out.println(Arrays.toString(a));
        int idx = binarySearch(a, val, 0, a.length);
        System.out.println(String.format("%d -> a[%d] = %d", val, idx, a[idx]));
    }

    static void testChoice(int n, double bound) {
        double[] intervals = randomIntervals(n, bound);
        int[] counters = new int[n];
        System.out.println("intervals = " + Arrays.toString(intervals));
        for (int m = 1000000; --m >= 0; ) {
            int chosen = randomIntervalLinear(intervals);
//            System.out.println("chosen = " + chosen);
            ++counters[chosen];
        }
        System.out.println("counters = " + Arrays.toString(counters));
        double[] ratios = new double[n];

        for (int i = intervals.length; --i >= 0; ) {
            ratios[i] = counters[i] / intervals[i];
        }
        System.out.println("ratios = " + Arrays.toString(ratios));
    }

    public static void main(String[] args) {
//        testChoice(20, 100.0);
//        toCumulative(new double[]{1.0, 2.0, 3.0});
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 3);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 2);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 6);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 9);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 8);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 10);
//        testBinarySearch(new int[]{1, 3, 4, 7, 9, 10}, 1);

        double[] intervals = randomIntervals(100, 1000.0);
        testSearch(intervals);
    }
}
