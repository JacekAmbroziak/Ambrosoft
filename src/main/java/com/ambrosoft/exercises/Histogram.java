package com.ambrosoft.exercises;

import java.util.PriorityQueue;

/**
 * Created by jacek on 10/20/16.
 */
public class Histogram {

    static class RectStart implements Comparable<RectStart> {
        private final int index;
        private final int value;

        RectStart(int index, int value) {
            this.index = index;
            this.value = value;
        }

        int areaUpTo(int end) {
            int area = (end - index) * value;
//            System.out.println("area = " + area);
            return area;
        }

        RectStart withValue(int value) {
            return new RectStart(index, value);
        }

        @Override
        public String toString() {
            return "(" + index + ", " + value + ')';
        }


        @Override
        public int compareTo(RectStart o) {
            return o.value - value;
        }
    }


    static int findBiggestRectangle(final int[] hist) {
        int adds = 0;
        int accum = 0;
        final PriorityQueue<RectStart> queue = new PriorityQueue<>();
        for (int i = 0, lastVal = -1; i < hist.length; i++) {
            final int value = hist[i];
            // value falling completes some continuations (zero completes all)
            while (!queue.isEmpty() && value < queue.peek().value) {
                final RectStart rectStart = queue.poll();
                accum = Math.max(accum, rectStart.areaUpTo(i));
                queue.add(rectStart.withValue(value));
                ++adds;
            }

            if (value > lastVal) {
                queue.add(new RectStart(i, value));
                ++adds;
            }

            lastVal = value;
        }

        while (queue.size() > 0) {
            accum = Math.max(accum, queue.poll().areaUpTo(hist.length));
        }
        System.out.println("adds = " + adds);
        return accum;
    }

    static void test(int[] a) {
//        System.out.println(Arrays.toString(a));
        System.out.println("findBiggestRectangle(a) = " + findBiggestRectangle(a));
    }

    public static void main(String[] args) {
        test(new int[]{5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5});
//        test(new int[]{1, 2, 2, 3, 2, 1});
//        test(new int[]{1, 3, 2, 1, 2});
//        test(new int[]{1, 5, 3, 1});
//        test(new int[]{1, 3, 3, 5, 1});
//        test(new int[]{6, 2, 5, 4, 5, 1, 6});
//        test(new int[]{1, 2, 3, 4, 5});
//        test(new int[]{5, 4, 3, 2, 1});
//        test(Utils.createRandomArray(10000000, 1000));
//        test(new int[]{7, 2, 1, 4, 5, 1, 3, 3});
//        test(new int[]{4, 1000, 1000, 1000, 1000});
//        test(new int[]{2, 1, 4, 5, 1, 3, 3});
    }
}
