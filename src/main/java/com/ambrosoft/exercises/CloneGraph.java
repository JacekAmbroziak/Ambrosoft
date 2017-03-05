package com.ambrosoft.exercises;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by jacek on 2/28/17.
 */

public class CloneGraph {
    static class Node {
        final String name;
        final List<Node> outgoing = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        void addSuccesor(Node succ) {
            outgoing.add(succ);
        }

        Iterable<Node> successors() {
            return outgoing;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static Node buildRandomGraph(final int n, final int maxFanOut) {
        final Node[] nodes = new Node[n];
        final int[] parent = new int[n];

        for (int i = 0; i < n; i++) {
            nodes[i] = new Node("n" + i);
        }
        Arrays.fill(parent, -1);    // "not set"

        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        for (int i = 0; i < n; i++) {
            final Node thisNode = nodes[i];
            int nSucc = rand.nextInt(1, maxFanOut);
            do {
                final int idx = rand.nextInt(0, n);
                thisNode.addSuccesor(nodes[idx]);
                parent[idx] = i;
            } while (nSucc-- > 0);
        }

        // ensure each Node is reachable from nodes[0]
        for (int i = n; --i > 0; ) {
            if (parent[i] < 0) {    // not set?
                // select some preceding node as predecessor
                nodes[rand.nextInt(0, i)].addSuccesor(nodes[i]);
            }
        }
        return nodes[0];
    }

    enum NodeColor {
        UNDISCOVERED, DISCOVERED, FINISHED
    }

    static int countReachable(final Node root) {
        final HashSet<Node> discovered = new HashSet<>();
        final HashSet<Node> expanded = new HashSet<>();
        final Deque<Node> queue = new LinkedList<>();

        discovered.add(root);
        queue.addLast(root);

        while (!queue.isEmpty()) {
            final Node node = queue.removeFirst();
            if (!expanded.contains(node)) {
                for (Node succ : node.successors()) {
                    if (!discovered.contains(succ)) {
                        discovered.add(succ);
                        queue.addLast(succ);
                    }
                }
                expanded.add(node);
            }
        }
        return discovered.size();
    }

    static int dfs(final Node root) {
        int count = 0;
        final HashMap<Node, NodeColor> state = new HashMap<>();
        final Deque<Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final Node current = stack.pop();
            final NodeColor nodeColor = state.get(current);
            if (nodeColor == null) {    // undiscovered
                count += 1;
                state.put(current, NodeColor.DISCOVERED);
                for (final Node succ : current.successors()) {
                    if (!state.containsKey(succ)) {
                        stack.push(succ);
                    }
                }
            }
        }
        System.out.println("state.size = " + state.size());
        return count;
    }

    private static Node getCopy(final Node orig, final Map<Node, Node> orig2new) {
        final Node extant = orig2new.get(orig);
        if (extant != null) {
            return extant;
        } else {
            final Node node = new Node(orig.name + ".copy");
            orig2new.put(orig, node);
            return node;
        }
    }

    static Node dfsClone(final Node root) {
        final HashMap<Node, Node> nodeMap = new HashMap<>();
        int count = 0;
        final HashMap<Node, NodeColor> state = new HashMap<>();
        final Deque<Node> stack = new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            final Node current = stack.pop();
            final NodeColor nodeColor = state.get(current);
            if (nodeColor == null) {    // undiscovered
                final Node newNode = getCopy(current, nodeMap);
                count += 1;
                state.put(current, NodeColor.DISCOVERED);
                for (final Node succ : current.successors()) {
                    newNode.addSuccesor(getCopy(succ, nodeMap));
                    if (!state.containsKey(succ)) {
                        stack.push(succ);
                    }
                }
            }
        }
        System.out.println("state.size = " + state.size());
        System.out.println("count = " + count);
        return nodeMap.get(root);
    }

    public static void main(String[] args) {
        final Node root = buildRandomGraph(100, 3);
        System.out.println("root = " + root);
        System.out.println("n reachable = " + countReachable(root));
        int count = dfs(root);
        System.out.println("count = " + count);
        final Node newRoot = dfsClone(root);
        System.out.println("newRoot = " + newRoot);
        dfs(newRoot);
    }
}
