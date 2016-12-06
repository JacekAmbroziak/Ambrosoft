package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/6/16.
 */
class ListNode {
    final int data;
    final ListNode next;

    ListNode(int data) {
        this(data, null);
    }

    ListNode(int data, ListNode next) {
        this.data = data;
        this.next = next;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(data);
        for (ListNode ptr = next; ptr != null; ptr = ptr.next) {
            sb.append(',').append(' ').append(ptr.data);
        }
        return sb.toString();
    }

    static ListNode fromArray(int[] numbers) {
        int index = numbers.length - 1;
        ListNode list = new ListNode(numbers[index]);
        while (--index >= 0) {
            list = new ListNode(numbers[index], list);
        }
        return list;
    }
}
