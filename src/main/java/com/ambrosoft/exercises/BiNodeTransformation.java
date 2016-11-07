package com.ambrosoft.exercises;

/**
 * Created by jacek on 10/30/16.
 */
public class BiNodeTransformation {

    static class BiNode {
        int data;
        BiNode node1;
        BiNode node2;

        public BiNode(int val) {
            data = val;
        }

        BiNode insert(final int val) {
            if (val < data) {
                if (node1 != null) {
                    return node1.insert(val);
                } else {
                    return node1 = new BiNode(val);
                }
            } else if (val > data) {
                if (node2 != null) {
                    return node2.insert(val);
                } else {
                    return node2 = new BiNode(val);
                }
            } else {
                return this;
            }
        }

        void stringRep(final StringBuilder sb) {
            if (node1 != null) {
                sb.append('(');
                node1.stringRep(sb);
                sb.append(')');
            }
            sb.append('<').append(data).append('>');
            if (node2 != null) {
                sb.append('(');
                node2.stringRep(sb);
                sb.append(')');
            }
        }
    }

    static BiNode buildRandomTree(int[] a) {
        BiNode root = new BiNode(a[0]);
        for (int i = a.length; --i > 0; ) {
            root.insert(a[i]);
        }
        return root;
    }

    static String stringRep(BiNode node) {
        StringBuilder sb = new StringBuilder();
        node.stringRep(sb);
        return sb.toString();
    }

    static BiNode last(BiNode node) {
        BiNode next;
        while ((next = node.node1) != null) {
            node = next;
        }
        return node;
    }

    static BiNode link(BiNode lft, BiNode rgt) {
        lft.node1 = rgt;
        rgt.node2 = lft;
        return rgt;
    }

    static BiNode tree2list(BiNode root) {
        if (root == null) { // takes care of null children
            return null;
        }

        // recursively convert left & right subtrees to lists and concatenate them
        BiNode left = tree2list(root.node1);
        BiNode right = tree2list(root.node2);

        root.node1 = root.node2 = null;

        // should be possible to implement "left link" to return rightmost end w/o scanning for it
        BiNode node = left != null ? link(last(left), root) : root;
        if (right != null) {
            link(node, right);
        }

        return left != null ? left : root;
    }

    static void printList(BiNode list) {
        while (list != null) {
            System.out.println("list = " + list.data);
            list = list.node1;
        }
    }

    public static void main(String[] args) {
        int size = 20;
        int[] a = Utils.createRandomArray(size, size * 10);

        BiNode root = buildRandomTree(a);
        String rep = stringRep(root);
        System.out.println(rep);

        BiNode list = tree2list(root);
        printList(list);
    }
}
