package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

/**
 * Created by jacek on 6/19/16.
 */
public class PECS {

    static class BetterStack<E> {
        private final Stack<E> theStack = new Stack<>();

        void pushAll(Collection<? extends E> elements) {
            for (E e : elements) {
                theStack.add(e);
            }
        }

        void popAll(Collection<E> elements) {
            while (!theStack.empty()) {
                elements.add(theStack.pop());
            }
        }
    }

    public static void main(String[] args) {
        BetterStack<Number> numberStack = new BetterStack<>();

        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        ints.add(2);
        ints.add(3);

        numberStack.pushAll(ints);

        List<Number> numbers = new ArrayList<>();

        numberStack.popAll(numbers);

        System.out.println("done");
    }
}
