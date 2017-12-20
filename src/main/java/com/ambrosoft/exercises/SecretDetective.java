package com.ambrosoft.exercises;

import java.util.*;

/*
    recover string that contains all the triplet subsequences
    no repeated chars, so length of output string is equal to cardinality of unique letters in triplets
    the triplets provide constraints on letter ordering
    they can be seen as edges in a graph, the final string corresponding to topological sort
 */
public class SecretDetective {

    static class Node {
        final Character name;
        final ArrayList<Node> out = new ArrayList<>();
        final ArrayList<Node> in = new ArrayList<>();

        Node(Character name) {
            this.name = name;
        }

        int indegree() {
            return in.size();
        }
    }

    static class Graph {
        final Map<Character, Node> nodeMap = new HashMap<>();

        Node getNode(char c) {
            Node node = nodeMap.get(c);
            if (node == null) {
                nodeMap.put(c, node = new Node(c));
            }
            return node;
        }

        void addEdge(char c1, char c2) {
            Node n1 = getNode(c1);
            Node n2 = getNode(c2);
            if (!n1.out.contains(n2)) {
                n1.out.add(n2);
                n2.in.add(n1);
            }
        }

        String topologicalSort() {
            final PriorityQueue<Node> pq = new PriorityQueue<>(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return o1.indegree() - o2.indegree();
                }
            });
            pq.addAll(nodeMap.values());

            final StringBuilder sb = new StringBuilder();
            // simulation of decrease_key by multiple additions of the same Nodes to PriorityQueue
            // not great but works, using existing Java libraries
            final Set<Character> added = new HashSet<>();
            while (!pq.isEmpty()) {
                final Node node = pq.remove();
                if (!added.contains(node.name)) {
                    if (node.indegree() == 0) {
                        sb.append(node.name);
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
            return sb.toString();
        }
    }

    public String recoverSecret(char[][] triplets) {
        final Graph graph = new Graph();
        for (char[] triplet : triplets) {
            graph.addEdge(triplet[0], triplet[1]);
            graph.addEdge(triplet[1], triplet[2]);
        }
        return graph.topologicalSort();
    }


    static class Repository {
        final HashMap<Character, CharNode> nodeMap = new HashMap<>();

        CharNode forChar(char c) {
            CharNode charNode = nodeMap.get(c);
            if (charNode == null) {
                nodeMap.put(c, charNode = new CharNode(c));
            }
            return charNode;
        }

        void addSuccessor(char c1, char c2) {
            final CharNode n1 = forChar(c1);
            final CharNode n2 = forChar(c2);
            if (!n1.successors.contains(n2)) {
                n1.successors.add(n2);
            }
            n1.propagateSequenceNumber(n1.sequenceNo + 1);
        }

        Collection<CharNode> getNodes() {
            return nodeMap.values();
        }
    }

    static class CharNode {
        final char c;
        final ArrayList<CharNode> successors = new ArrayList<>(1);
        int sequenceNo;

        CharNode(char c) {
            this.c = c;
        }

        void propagateSequenceNumber(final int minSeq) {
            for (CharNode succ : successors) {
                if (succ.sequenceNo < minSeq) {
                    succ.sequenceNo = minSeq;
                    succ.propagateSequenceNumber(minSeq + 1);
                }
            }
        }
    }

    public String recoverSecret2(char[][] triplets) {
        final Repository repo = new Repository();
        for (char[] triplet : triplets) {
            repo.addSuccessor(triplet[0], triplet[1]);
            repo.addSuccessor(triplet[1], triplet[2]);
        }
        Collection<CharNode> nodes = repo.getNodes();
        CharNode[] array = nodes.toArray(new CharNode[nodes.size()]);
        Arrays.sort(array, new Comparator<CharNode>() {
            @Override
            public int compare(CharNode o1, CharNode o2) {
                return o1.sequenceNo - o2.sequenceNo;
            }
        });
        StringBuilder sb = new StringBuilder();
        for (CharNode node : array) {
            sb.append(node.c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SecretDetective detective = new SecretDetective();
        char[][] triplets = {
                {'t', 'u', 'p'},
                {'w', 'h', 'i'},
                {'t', 's', 'u'},
                {'a', 't', 's'},
                {'h', 'a', 'p'},
                {'t', 'i', 's'},
                {'w', 'h', 's'}
        };
        String result = detective.recoverSecret2(triplets);
        System.out.println("result = " + result);
    }
}