package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 1/4/17.
 */
public class SkienaBinarySearch {
    /*
        Skiena notes that modified binary search can find beginning (or end) of a range of duplicates
     */

    static int search(String[] data, String x) {
        return search(data, x, 0, data.length);
    }

    private static int search(String[] data, String x, int start, int end) {
        if (start < end) {
            final int mid = start + (end - start) / 2;
            final int cmp = x.compareTo(data[mid]);
            if (cmp == 0) {
                return mid;
            } else if (cmp > 0) {
                return search(data, x, mid + 1, end);
            } else {
                return search(data, x, start, mid);
            }
        } else {
            return -1;
        }
    }

    static int searchRangeLow(String[] data, String x) {
        return searchRangeLow(data, x, 0, data.length);
    }

    // don't return on equality
    // return start on base case
    private static int searchRangeLow(String[] data, String x, int start, int end) {
        if (start < end) {
            final int mid = start + (end - start) / 2;
            if (x.compareTo(data[mid]) > 0) {
                return searchRangeLow(data, x, mid + 1, end);
            } else {
                return searchRangeLow(data, x, start, mid);
            }
        } else {
            return start;
        }
    }

    static int searchRangeHigh(String[] data, String x) {
        return searchRangeHigh(data, x, 0, data.length);
    }

    private static int searchRangeHigh(String[] data, String x, int start, int end) {
        if (start < end) {
            final int mid = start + (end - start) / 2;
            if (x.compareTo(data[mid]) < 0) {
                return searchRangeHigh(data, x, start, mid);
            } else {
                return searchRangeHigh(data, x, mid + 1, end);
            }
        } else {
            return end;
        }
    }

    static void test(String[] data, String x) {
        System.out.println("data = " + Arrays.toString(data));
        int index = search(data, x);
        System.out.println(String.format("%s -> %d", x, index));
    }

    static void testRangeLow(String[] data, String x) {
        System.out.println("data = " + Arrays.toString(data));
        int index = searchRangeLow(data, x);
        System.out.println(String.format("%s -> %d", x, index));
    }

    static void testRangeHigh(String[] data, String x) {
        System.out.println("data = " + Arrays.toString(data));
        int index = searchRangeHigh(data, x);
        System.out.println(String.format("%s -> %d", x, index));
    }

    public static void main(String[] args) {
        final String[] data = {"a", "b", "c", "d", "e", "f"};
        for (String datum : data) {
            test(data, datum);
        }

        final String[] data2 = {"a", "b", "b", "b", "c", "d", "d", "d", "e", "f", "f", "f", "f"};
        for (String datum : data2) {
            testRangeLow(data2, datum);
        }

        testRangeLow(data2, "b");
        testRangeHigh(data2, "b");

        testRangeLow(data2, "d");
        testRangeHigh(data2, "d");

        testRangeLow(data2, "f");
        testRangeHigh(data2, "f");
    }
}
