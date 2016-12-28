package com.ambrosoft.exercises;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by jacek on 12/22/16.
 */
public class Hanoi {
    /*
        approach: name stacks, recurse on number of discs to be moved
     */

    static void move(int n, String from, String aux, String to) {
        if (n == 1) {
            System.out.println(String.format("%s -> %s", from, to));
        } else {
            move(n - 1, from, to, aux);
            move(1, from, aux, to);
            move(n - 1, aux, from, to);
        }
    }

    /*
        This produces confusing printouts as stacks are not named and reordered for printing
     */
    static void move(int n, Deque<Integer> from, Deque<Integer> aux, Deque<Integer> to) {
        if (n == 1) {
            System.out.println("------------");
            System.out.println("from = " + from);
            System.out.println("aux  = " + aux);
            System.out.println("to   = " + to);

            // the move
            to.push(from.pop());

            System.out.println("from = " + from);
            System.out.println("aux  = " + aux);
            System.out.println("to   = " + to);
            System.out.println("------------");
        } else {
            move(n - 1, from, to, aux);
            move(1, from, aux, to);
            move(n - 1, aux, from, to);
        }
    }

    public static void main(String[] args) {
        move(3, "A", "C", "B");

        final Deque<Integer> deque = new ArrayDeque<>();
        deque.push(3);
        deque.push(2);
        deque.push(1);
        System.out.println("\nfrom = " + deque);

        final ArrayDeque<Integer> to = new ArrayDeque<>();
        move(deque.size(), deque, new ArrayDeque<>(), to);
        System.out.println("to = " + to);
    }
}
