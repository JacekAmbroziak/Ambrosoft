package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/30/16.
 */
public class SortedNoLength {

    static class List {
        final int[] array;

        List(int[] array) {
            this.array = array;
        }

        int getElement(int index) {
            return index < array.length ? array[index] : -1;
        }

        // just a shortcut when we are working with known data
        int binarySearch(int k, int lo, int hi) {
            return Arrays.binarySearch(array, lo, hi, k);
        }
    }

    /*
        We know that data is sorted
        No idea of the length of data
        Would like to use binary search, but no idea what index to start with;
        one possibility is to first search for real end of data, then do binary search
        start with a guess
        if -1, half the guess
     */

    static int search(List list, int k) {
        int index = 1000;
        while (list.getElement(index) < 0) {
            index = index / 2;
        }

        // TODO still not OK if element at index valid but smaller

        if (list.getElement(index) > k) {
            return list.binarySearch(k, 0, index);
        }
        // we know that k is somewhere between index and 2*index, where data ends
        // can do special binary search
        return specialBinarySearch(list, k, index, 2 * index);
    }

    static int specialBinarySearch(List list, int k, int lo, int hi) {
        final int mid = lo + (hi - lo) / 2;
        final int element = list.getElement(mid);
        if (element < 0) {
            return specialBinarySearch(list, k, lo, mid);
        } else if (element == k) {
            return mid;
        } else if (element < k) {
            return specialBinarySearch(list, k, lo, mid);
        } else {
            return list.binarySearch(k, lo, mid);
        }
    }

    static void test(List l, int k) {
        int index = search(l, k);
        System.out.println(String.format("%d -> %d", k, index));
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(20, 100);
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        List list = new List(a);
//        int idx = search(list,50);
//        System.out.println("idx = " + idx);

        test(list, 50);
        test(list, 51);
        test(list, 52);
    }
}
