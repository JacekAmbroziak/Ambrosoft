package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 11/3/16.
 * <p>
 * "Programming Pearls"
 */

public class Heap {
    private final int[] data;
    private int end = 1;

    public Heap(int size) {
        this.data = new int[size + 1];  // root at 1, data[0] not used
    }

    // will hopefully be inlined; improves readability
    // OTOH swap probably not necessary at all: element bubbling up is unnecessarily stored in non-final locations
    // ie. consecutive swaps can be simplified
    private static void swap(int[] a, int i, int j) {
        final int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // i -- index of an element that may be violating the heap property by being smaller than its parent
    // propagate up towards root
    private static void siftUp(final int[] a, int i) {
        while (i > 1) { // not root
            final int parent = i / 2;
            if (a[i] < a[parent]) {
                swap(a, i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private static void siftDown(int[] a, int n) {
        for (int i = 1; ; ) {
            int child = i * 2;  // left child
            if (child < n) {    // left child exists in the tree
                if (child + 1 < n) {    // right child also exists
                    if (a[child + 1] < a[child]) {  // and if smaller
                        ++child;    // then we remember the right child as smaller of the two
                    }
                }
                // child is index of the smaller (or only) child
                if (a[i] > a[child]) {
                    swap(a, i, child);
                    i = child;
                } else {
                    break;
                }
            } else {
                break;  // no children -> we are done
            }
        }
    }

    public int size() {
        return end - 1;
    }

    public void insert(final int val) {
        final int idx = end;
        if (idx < data.length) {
            data[idx] = val;
            siftUp(data, idx);
            end = idx + 1;
        } else {
            throw new Error("heap overflow");
        }
    }

    public int extractMin() {
        if (end > 1) {
            final int min = data[1];
            data[1] = data[--end];
            siftDown(data, end);
            return min;
        } else {
            throw new Error("heap underflow");
        }
    }

    public static void main(String[] args) {
        final int size = 20;
        int[] random = Utils.createRandomArray(size, size * 2);

        System.out.println(Arrays.toString(random));

        final Heap heap = new Heap(size);
        for (int i = size; --i >= 0; ) {
            heap.insert(random[i]);
        }

        while (heap.size() > 0) {
            System.out.println("heap.extractMin() = " + heap.extractMin());
        }
    }
}
