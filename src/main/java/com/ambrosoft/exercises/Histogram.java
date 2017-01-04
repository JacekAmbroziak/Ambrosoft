package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by jacek on 10/20/16.
 */
public class Histogram {

    static final class RectStart implements Comparable<RectStart> {
        private final int startIndex;
        private final int height;

        RectStart(int startIndex, int height) {
            this.startIndex = startIndex;
            this.height = height;
        }

        int getHeight() {
            return height;
        }

        int areaUpTo(final int end) {
            return (end - startIndex) * height;
        }

        RectStart withHeight(int height) {
            return new RectStart(startIndex, height);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", startIndex, height);
        }

        @Override
        public int compareTo(RectStart o) {
            return o.height - height;
        }
    }


    static int findLargestRectangle(final int[] hist) {
        int largest = 0;
        // collection of continuing rectangles (continuously) ordered by diminishing heights
        final PriorityQueue<RectStart> queue = new PriorityQueue<>();
        for (int i = 0, lastHeight = -1; i < hist.length; i++) {
            final int height = hist[i];
            // height falling completes some continuations (zero completes all)
            while (!queue.isEmpty() && height < queue.peek().getHeight()) {
                final RectStart rectStart = queue.poll();
                largest = Math.max(largest, rectStart.areaUpTo(i));
                queue.add(rectStart.withHeight(height));
            }

            if (height > lastHeight) {
                queue.add(new RectStart(i, height));
            }

            lastHeight = height;
        }

        while (queue.size() > 0) {
            largest = Math.max(largest, queue.poll().areaUpTo(hist.length));
        }
        return largest;
    }

    static void test(int[] a) {
        System.out.println(Arrays.toString(a));
        System.out.println("findBiggestRectangle(a) = " + findLargestRectangle(a));
    }

    public static void main(String[] args) {
        test(new int[]{5, 4, 3, 2, 1, 0, 1, 2, 3, 4, 5});
//        test(new int[]{1, 2, 2, 3, 2, 1});
//        test(new int[]{1, 3, 2, 1, 2});
        test(new int[]{1, 5, 3, 1});
        test(new int[]{4, 3, 2, 2});
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
