package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by jacek on 11/3/16.
 */

final class IntegerArray implements Iterable<Integer> {
    private int[] array;
    private int end;

    IntegerArray(int capacity) {
        array = new int[capacity];
    }

    IntegerArray() {
        this(2);
    }

    IntegerArray add(int val) {
        if (end == array.length) {
            System.arraycopy(array, 0, array = new int[end * 2], 0, end);
        }
        array[end++] = val;
        return this;
    }

    int size() {
        return end;
    }

    int get(int index) {
        if (index < 0 || index >= end) {
            throw new IllegalArgumentException();
        }
        return array[index];
    }

    boolean remove(int v) {
        for (int i = end; --i >= 0; ) {
            if (array[i] == v) {
                array[i] = array[--end];
                return true;
            }
        }
        return false;
    }

    int removeLast() {
        final int index = end - 1;
        if (index >= 0) {
            return array[end = index];
        } else {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < end;
            }

            @Override
            public Integer next() {
                return index < end ? array[index++] : null;
            }
        };
    }

    Iterable<Integer> asIterable() {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return IntegerArray.this.iterator();
            }
        };
    }

    @Override
    public String toString() {
        int[] values = new int[end];
        System.arraycopy(array, 0, values, 0, end);
        return Arrays.toString(values);
    }

    public static void main(String[] args) {
        IntegerArray ia = new IntegerArray(10);
        ia.add(3);
        ia.add(2);
        ia.add(1);

        for (Integer integer : ia) {
            System.out.println("integer = " + integer);
        }
    }
}
