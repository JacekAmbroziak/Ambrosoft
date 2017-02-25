package com.ambrosoft.exercises;

/**
 * Created by jacek on 2/24/17.
 */
public class ListReverse {
    static ListNode reverse(ListNode list) {
        if (list == null) {
            return null;
        } else {
            ListNode next = list;
            ListNode prev = null;
            while (next != null) {
                final ListNode nextNext = next.next;
                next.next = prev;
                prev = next;
                next = nextNext;
            }
            return prev;
        }
    }

    static void test(ListNode a) {
        System.out.println("a = " + a);
        System.out.println("a = " + reverse(a));
    }

    public static void main(String[] args) {
        test(ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6}));
        test(ListNode.fromArray(new int[]{1, 2}));
        test(ListNode.fromArray(new int[]{1}));
    }
}
