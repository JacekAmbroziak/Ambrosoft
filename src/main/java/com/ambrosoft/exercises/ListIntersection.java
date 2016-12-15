package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/7/16.
 */

public class ListIntersection {
    /*
        iff lists intersect, they share tail
        one way to discover this is to walk both till the end and see if last is the same
            drawback is potentially long walk till the end
        it does not make sense to start walking the 2 lists 1 node at a time as lengths before join can be very different
        one could walk one list and store nodes in a Set, then look for first node in the set from the 2nd list
        How to find node of join? Check lengths, step longer till equal, walk both
     */


    static ListNode findJoin(ListNode a, ListNode b) {
        if (a.last() == b.last()) {
            final int alen = ListNode.length(a);
            final int blen = ListNode.length(b);
            if (alen != blen) {
                if (alen > blen) {
                    a = ListNode.skip(a, alen - blen);
                } else {
                    b = ListNode.skip(b, blen - alen);
                }
            }

            while (a != b) {
                a = a.next;
                b = b.next;
            }
            return a;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        ListNode a = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6});
        ListNode b = ListNode.fromArray(new int[]{10, 20, 30});

        b.last().next = a.find(4);  // join at 4

        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("findJoin(a,b) = " + findJoin(a, b));
    }
}
