package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created by jacek on 12/9/16.
 */
public class BuildOrder {

    static class Graph {
        final ArrayList<Node> nodes = new ArrayList<>();
        final HashMap<String, Node> map = new HashMap<>();

        void addNode(final String nodeName) {
            final Node node = new Node(nodeName);
            if (map.containsKey(nodeName)) {
                throw new IllegalArgumentException("node exists " + nodeName);
            } else {
                map.put(nodeName, node);
                nodes.add(node);
            }
        }

        void addEdge(String n1, String n2) {
            final Node source = map.get(n1);
            if (source != null) {
                final Node target = map.get(n2);
                if (target != null) {
                    source.out.add(target);
                    target.in.add(source);
                } else {
                    throw new IllegalArgumentException("unknown node " + n2);
                }
            } else {
                throw new IllegalArgumentException("unknown node " + n1);
            }
        }

        String[] topologicalSort() {
            final PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.indegree() - o2.indegree();
                }
            });
            pq.addAll(nodes);

            final String[] result = new String[nodes.size()];
            int index = 0;
            // simulation of decrease_key by multiple additions of the same Nodes to PriorityQueue
            // not great but works, using existing Java libraries
            final Set<String> added = new HashSet<>();
            while (!pq.isEmpty() && index < nodes.size()) {
                final Node node = pq.remove();
                if (!added.contains(node.name)) {
                    if (node.indegree() == 0) {
                        result[index++] = node.name;
                        added.add(node.name);
                        for (final Node target : node.out) {
                            target.in.remove(node);
                            pq.add(target);
                        }
                    } else {
                        return null;
                    }
                }
            }
            return result;
        }
    }

    static class Node {
        final String name;
        final ArrayList<Node> out = new ArrayList<>();
        final ArrayList<Node> in = new ArrayList<>();

        Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        int indegree() {
            return in.size();
        }
    }

    static Graph buildGraph(String[] projects, String[][] dependencies) {
        final Graph graph = new Graph();
        // vertices
        for (final String project : projects) {
            graph.addNode(project);
        }
        // edges
        for (final String[] dependency : dependencies) {
            final String target = dependency[0];
            final String source = dependency[1];
            graph.addEdge(source, target);
        }
        return graph;
    }

    public static void main(String[] args) {
        String[] projects = {"a", "b", "c", "d", "e", "f"};
        String[][] dependencies = {{"d", "a"}, {"b", "f"}, {"d", "b"}, {"a", "f"}, {"c", "d"},};

        Graph graph = buildGraph(projects, dependencies);

        String[] ordering = graph.topologicalSort();

        System.out.println(Arrays.toString(ordering));
    }
}
