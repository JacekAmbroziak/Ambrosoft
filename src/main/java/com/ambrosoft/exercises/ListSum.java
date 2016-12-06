package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/6/16.
 */
public class ListSum {
    /*
        sum list of digits given in reverse order so that least significant digits are heads

        if order of digits is not reversed this is much more complicated
        need to measure list length, pad shorted with zeros
        recurse to bottom of both lists and perform addition while popping the stack
     */


    static ListNode sum(ListNode a, ListNode b) {
        return sum(a, b, 0);
    }

    private static ListNode sum(ListNode a, ListNode b, int carry) {
        if (a != null && b != null) {
            final int result = a.data + b.data + carry;
            return new ListNode(result % 10, sum(a.next, b.next, result / 10));
        } else if (a == null) {
            return sum(b, carry);
        } else {
            return sum(a, carry);
        }
    }

    private static ListNode sum(ListNode a, int carry) {
        if (carry == 0) {
            return a;
        } else if (a == null) {
            return new ListNode(carry, null);
        } else {
            final int result = a.data + carry;
            return new ListNode(result % 10, sum(a.next, result / 10));
        }
    }

    public static void main(String[] args) {
        ListNode a = ListNode.fromArray(new int[]{7, 1, 6});
        ListNode b = ListNode.fromArray(new int[]{5, 9, 2});

        System.out.println("sum(a,b) = " + sum(a, b));
    }
}
