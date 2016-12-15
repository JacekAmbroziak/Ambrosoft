package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/7/16.
 */
public class SortStack {
    private StackNode top;

    static class StackNode {
        final int datum;
        StackNode next;

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

    void push(final int value) {
        final StackNode el = new StackNode(value);
        el.next = top;
        top = el;
    }

    void sort() {
        if (isNonEmpty()) {

            // look for largest by popping all & pushing to temporary

        }
    }


    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(10, 100);
        final SortStack stack = new SortStack();
        for (int i : a) {
            stack.push(i);
        }
        System.out.println("a = " + Arrays.toString(a));
        while (stack.isNonEmpty()) {
            System.out.println("stack.pop() = " + stack.pop());
        }
    }
}
