package com.ambrosoft.exercises;

import java.util.Arrays;

/**
 * Created by jacek on 12/7/16.
 */
public class QueueViaStacks {
    final StackMin in = new StackMin();
    final StackMin out = new StackMin();

    void add(int value) {
        in.push(value);
    }

    int remove() {
        if (isEmpty()) {
            throw new IllegalStateException("empty stack");
        } else {
            if (out.isNonEmpty()) {
                return out.pop();
            } else {
                while (in.isNonEmpty()) {
                    out.push(in.pop());
                }
                return out.pop();
            }
        }
    }

    boolean isEmpty() {
        return in.isEmpty() && out.isEmpty();
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(10, 100);
        System.out.println("a = " + Arrays.toString(a));

        final QueueViaStacks queue = new QueueViaStacks();
        for (int i : a) {
            queue.add(i);
        }
        while (!queue.isEmpty()) {
            int val = queue.remove();
            System.out.println("val = " + val);
        }
    }
}
