package com.ambrosoft.exercises;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 11/4/17.
 * <p>
 * Deep copy of a linked list where list values can point to arbitrary nodes on the list
 * Approach:
 * crete same number of new Nodes
 * linearize both old and new nodes to be indexed by consecutive integers
 * replicate value structure using node indices as proxies for their identities
 * the process proceeds in 2 phases because of possible forward references of values
 */
public class ListDeepCopy {

    private static class Node {
        String name;    // for debugging
        Node value;
        Node next;

        @Override
        public String toString() {
            return name;
        }
    }

    private static String describeNode(final Node node) {
        return String.format("%s, value: %s, next: %s", node, node.value, node.next);
    }

    private static void printList(final Node head) {
        for (Node node = head; node != null; node = node.next) {
            System.out.println(describeNode(node));
        }
    }

    // create a linked list from provided Node objects retaining order
    private static Node linkNodes(final List<Node> nodeList) {
        for (int index = nodeList.size(); --index > 0; ) {
            nodeList.get(index - 1).next = nodeList.get(index);
        }
        return nodeList.get(0);
    }

    private static Node deepCopy(final Node head) {
        if (head == null) {
            return null;
        } else {
            final List<Node> origNodes = new ArrayList<>();
            final List<Node> newNodes = new ArrayList<>();
            final Map<Node, Integer> indexOfOrig = new HashMap<>();

            // map original nodes to their indexes, allocate new nodes for the copy
            int counter = 0;
            for (Node node = head; node != null; node = node.next) {
                origNodes.add(node);
                indexOfOrig.put(node, counter++);
                newNodes.add(new Node());
            }
            // counter holds the number of nodes of the original and new lists
            // count back and link nodes into a list
            while (--counter > 0) {
                newNodes.get(counter - 1).next = newNodes.get(counter);
            }
            // now lookup and set values on new nodes to replicate original topology
            for (int i = origNodes.size(); --i >= 0; ) {
                final Node origNode = origNodes.get(i);
                final int indexOfValue = indexOfOrig.get(origNode.value);
                final Node newNode = newNodes.get(i);
                newNode.value = newNodes.get(indexOfValue);
                newNode.name = origNode.name;
            }
            return newNodes.get(0); // new root
        }
    }

    private static int[] extractValueIndices(final Node head) {
        final Map<Node, Integer> nodeIndices = new HashMap<>();
        int counter = 0;
        for (Node node = head; node != null; node = node.next) {
            nodeIndices.put(node, counter++);
        }
        final int[] result = new int[counter];
        int index = 0;
        for (Node node = head; node != null; node = node.next) {
            final Node value = node.value;
            if (value != null) {
                final Integer indexOfValue = nodeIndices.get(value);
                if (indexOfValue != null) {
                    result[index++] = indexOfValue;
                } else {
                    throw new IllegalStateException("value node is not on the list");
                }
            } else {
                result[index++] = -1;
            }
        }
        return result;
    }

    // test for logical equality of a list and its deep copy
    private static void testDeepCopy(final Node head) {
        final int[] origIndices = extractValueIndices(head);
        final int[] copyIndices = extractValueIndices(deepCopy(head));
        if (Arrays.equals(origIndices, copyIndices)) {
            System.out.println("original and copy are the same");
        } else {
            System.out.println("original and copy differ");
        }
    }

    private static void printOrigAndCopy(final Node head) {
        System.out.println("------  ORIG ------");
        printList(head);
        System.out.println("------  COPY ------");
        printList(deepCopy(head));
        // perform formal test
        testDeepCopy(head);
    }

    static void testCase() {
        // manually construct the list
        Node a = new Node();
        Node b = new Node();
        Node c = new Node();

        a.name = "A";
        a.value = c;
        a.next = b;

        b.name = "B";
        b.value = a;
        b.next = c;

        c.name = "C";
        c.value = b;
        c.next = null;

        printOrigAndCopy(a);
    }

    // create list of give size with values randomly chosen among its nodes
    private static Node randomList(final int size) {
        final List<Node> nodeList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            final Node node = new Node();
            node.name = Integer.toString(i);
            nodeList.add(node);
        }
        final ThreadLocalRandom random = ThreadLocalRandom.current();
        for (Node node : nodeList) {
            node.value = nodeList.get(random.nextInt(size));
        }
        return linkNodes(nodeList);
    }

    static void randomListCase(int size) {
        printOrigAndCopy(randomList(size));
    }

    public static void main(String[] args) {
        testCase();
        randomListCase(7);
    }
}
