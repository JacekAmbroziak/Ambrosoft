package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/7/16.
 */
public class StackMin {
    private StackNode top;

    static class StackNode {
        int datum;
        StackNode next;
        int min;    // minimum value from here to bottom (data str. augmentation)

        StackNode(int datum) {
            this.datum = datum;
        }
    }

    boolean isEmpty() {
        return top == null;
    }

    boolean isNonEmpty() {
        return top != null;
    }

    int peek() {
        if (top != null) {
            return top.datum;
        } else {
            throw new IllegalStateException("empty stack");
        }
    }

    int pop() {
        if (top != null) {
            final int datum = top.datum;
            top = top.next;
            return datum;
        } else {
            throw new IllegalStateException("empty stack");
        }
    }

    int min() {
        if (top != null) {
            return top.min;
        } else {
            throw new IllegalStateException("empty stack");
        }
    }

    void push(final int value) {
        final StackNode el = new StackNode(value);

        if (top != null) {
            el.min = Math.min(value, top.min);
            el.next = top;
        } else {
            el.min = value; // only value we know
        }

        top = el;
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(10, 100);
        final StackMin stack = new StackMin();
        for (int i : a) {
            stack.push(i);
        }
        System.out.println("a = " + Arrays.toString(a));
        while (stack.isNonEmpty()) {
            System.out.println("\tstack.min() = " + stack.min());
            System.out.println("stack.pop() = " + stack.pop());
        }
    }
}
