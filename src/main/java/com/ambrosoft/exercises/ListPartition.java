package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/6/16.
 */
public class ListPartition {
    /*
        partition reorganizes list so that nodes smaller than a given value come before nodes >= that value
        approach: functional solution
     */

    static ListNode partition(final ListNode list, final int value, final ListNode bigger) {
        if (list == null) {
            return bigger;
        } else if (list.data < value) {
            return new ListNode(list.data, partition(list.next, value, bigger));
        } else {
            return partition(list.next, value, new ListNode(list.data, bigger));
        }
    }

    static boolean testPartition(final ListNode list, final int value) {
        if (list == null) {
            return true;
        } else if (list.data < value) {
            return testPartition(list.next, value);
        } else {
            return testBigger(list.next, value);
        }
    }

    private static boolean testBigger(ListNode list, final int value) {
        while (list != null) {
            if (list.data < value) {
                return false;
            } else {
                list = list.next;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] a = Utils.createRandomArray(50, 1000);
        ListNode head = ListNode.fromArray(a);
        System.out.println("head = " + head);
        int pivot = a[10];
        System.out.println("pivot = " + pivot);
        System.out.println("head = " + partition(head, pivot, null));
        System.out.println("test = " + testPartition(partition(head, pivot, null), pivot));
    }
}
