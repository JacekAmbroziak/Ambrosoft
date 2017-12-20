package com.ambrosoft.exercises;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created on 12/8/17
 */
public class FlattenTree {
    static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int value;

        TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        TreeNode(int value) {
            this(value, null, null);
        }
    }

    static class ListNode {
        public TreeNode data;
        public ListNode next;

        ListNode(TreeNode data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        ListNode(TreeNode data) {
            this(data, null);
        }
    }

    private static void readTree(TreeNode node, TreeSet<Integer> values) {
        if (node != null) {
            values.add(node.value);
            readTree(node.left, values);
            readTree(node.right, values);
        }
    }

    static TreeNode flatten(ListNode head) {
        final TreeSet<Integer> values = new TreeSet<>();
        for (ListNode node = head; node != null; node = node.next) {
            readTree(node.data, values);
        }

        final Iterator<Integer> iter = values.iterator();
        final TreeNode newHead = new TreeNode(iter.next());
        final Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addFirst(newHead);
        while (iter.hasNext()) {
            TreeNode current = queue.removeLast();
            queue.addFirst(current.left = new TreeNode(iter.next()));
            if (iter.hasNext()) {
                queue.addFirst(current.right = new TreeNode(iter.next()));
            }
        }
        return newHead;
    }

    @Test
    public void exampleList() {
        TreeNode t1 = new TreeNode(1, null, new TreeNode(2));
        TreeNode t2 = new TreeNode(4);
        TreeNode t3 = new TreeNode(3, new TreeNode(4), new TreeNode(2));
        TreeNode t4 = new TreeNode(6, null, new TreeNode(5));

        ListNode head = new ListNode(t1, new ListNode(t2, new ListNode(t3, new ListNode(t4))));
        TreeNode expected = new TreeNode(1, new TreeNode(2, new TreeNode(4), new TreeNode(5)), new TreeNode(3, new TreeNode(6), null));
//        Helpers.testFlatten(head, expected);
    }


    public static void main(String[] args) {
        TreeNode t1 = new TreeNode(1, null, new TreeNode(2));
        TreeNode t2 = new TreeNode(4);
        TreeNode t3 = new TreeNode(3, new TreeNode(4), new TreeNode(2));
        TreeNode t4 = new TreeNode(6, null, new TreeNode(5));

        ListNode head = new ListNode(t1, new ListNode(t2, new ListNode(t3, new ListNode(t4))));

        TreeNode flattened = flatten(head);

        System.out.println();

    }

}
