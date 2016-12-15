package com.ambrosoft.exercises;

/**
 * Created by jacek on 12/6/16.
 */
class ListNode {
    final int data;
    ListNode next;

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

    static ListNode fromArray(final int[] numbers) {
        int index = numbers.length - 1;
        ListNode list = new ListNode(numbers[index]);
        while (--index >= 0) {
            list = new ListNode(numbers[index], list);
        }
        return list;
    }

    ListNode find(final int value) {
        if (data == value) {
            return this;
        } else if (next == null) {
            return null;
        } else {
            return next.find(value);
        }
    }

    ListNode last() {
        return next == null ? this : next.last();
    }

    static int length(ListNode node) {
        int count = 0;
        while (node != null) {
            node = node.next;
            ++count;
        }
        return count;
    }

    static ListNode skip(ListNode node, int count) {
        while (node != null && count > 0) {
            node = node.next;
            --count;
        }
        return node;
    }

    private final static class List {
        final ListNode first;
        private ListNode last;

        List(ListNode initial) {
            this.first = this.last = initial;
        }

        List append(final ListNode node) {
            last.next = node;
            node.next = null;
            last = node;
            return this;
        }
    }

    static ListNode reverse(final ListNode list) {
        if (list != null) {
            return list.next == null ? list : reverseAux(list).first;
        } else {
            return null;
        }
    }

    private static List reverseAux(final ListNode list) {
        return list.next != null ? reverseAux(list.next).append(list) : new List(list);
    }

    public static void main(String[] args) {
        ListNode list = ListNode.fromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        System.out.println("reverse(list) = " + reverse(list));
    }
}
