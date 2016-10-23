package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static com.ambrosoft.exercises.Utils.createRandomArray;

/**
 * Created by jacek on 10/15/16.
 */
public class Heapify {

    private static class Heap {
        final int[] array;
        int count;

        Heap(final int[] values) {
            final int length = values.length;
            System.arraycopy(values, 0, array = new int[length], 0, count = length);
            for (int i = length / 2; i >= 0; --i) {
                heapify(array, length, i);
            }
        }

        int size() {
            return count;
        }

        int deleteMin() {
            if (count > 0) {
                final int result = array[0];
                array[0] = array[--count];
                heapify(array, count, 0);
                return result;
            } else {
                throw new NoSuchElementException();
            }
        }

        void println() {
            int[] current = new int[count];
            System.arraycopy(array, 0, current, 0, count);
            System.out.println(Arrays.toString(current));
        }

        // heap property: parent <= children
        // binary tree:
        // for parent at index n, left child at 2n, right child at 2n + 1 (works if starting from 1)
        // if starting from zero: 2n+1 and 2n+2 respectively

        private static void heapify(final int[] heap, final int count, int n) {
            for (int index = n; ; ) {
                {
                    final int lft = 2 * n + 1;
                    if (lft < count) {
                        if (heap[lft] < heap[index]) {
                            index = lft;
                        }
                        final int rgt = lft + 1;
                        if (rgt < count && heap[rgt] < heap[index]) {
                            index = rgt;
                        }
                    }
                }
                // index @ parent or one of its (existing) children w/ smallest value of the (max) three
                if (index != n) {   // index points to smallest child of n
                    // swap with smaller of the children
                    final int smallest = heap[index];
                    heap[index] = heap[n];   // possibly disrupting heap property at index
                    heap[n] = smallest;
                    // continue loop with node set to smallest
                    n = index;
                } else {    // no child was smaller
                    break;
                }
            }
        }
    }

    static void checkHeap(final Heap heap) {
        int value = heap.deleteMin();
        while (heap.size() > 0) {
            final int min = heap.deleteMin();
            if (min < value) {
                throw new Error();
            } else {
                value = min;
            }
        }
    }

    public static void main(String[] args) {
        int[] array = createRandomArray(20, 20);
        Heap heap = new Heap(array);
        heap.println();

        while (heap.size() > 0) {
            System.out.println("heap = " + heap.deleteMin());
        }


        for (int i = 0; i < 10000; i++) {
            checkHeap(new Heap(createRandomArray(10000, 10000)));
        }
    }
}
