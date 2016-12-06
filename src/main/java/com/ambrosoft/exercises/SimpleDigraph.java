package com.ambrosoft.exercises;

import java.util.Collections;
import java.util.Random;

/**
 * Created by jacek on 11/2/16.
 */

public class SimpleDigraph {
    private final int nodeCount;
    private final IntegerArray[] adjLists;
    private final int[] indegree;

    SimpleDigraph(final int nodeCount) {
        this.nodeCount = nodeCount;
        adjLists = new IntegerArray[nodeCount];
        indegree = new int[nodeCount];
    }

    void addEdge(int u, int v) {
        if (u < 0 || v < 0 || u >= nodeCount || v >= nodeCount) {
            throw new IllegalArgumentException();
        }
        if (adjLists[u] == null) {
            adjLists[u] = new IntegerArray();
        }
        adjLists[u].add(v);
        ++indegree[v];
    }

    boolean removeEdge(int u, int v) {
        if (adjLists[u] != null && adjLists[u].remove(v)) {
            --indegree[v];
            return true;
        }
        return false;
    }

    int nodeCount() {
        return nodeCount;
    }

    int indegree(int node) {
        return indegree[node];
    }

    Iterable<Integer> adj(int v) {
        return adjLists[v] != null ? adjLists[v].asIterable() : Collections.emptyList();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final int V = nodeCount();
        sb.append("V = ").append(V).append('\n');
        for (int i = 0; i < V; i++) {
            if (adjLists[i] != null) {
                sb.append(i).append(':').append(adjLists[i].toString()).append('\n');
            }
        }
        return sb.toString();
    }

    static SimpleDigraph createRandomGraph(int V, int E) {
        final SimpleDigraph digraph = new SimpleDigraph(V);
        final Random random = new Random(System.currentTimeMillis());
        for (int i = E; --i >= 0; ) {
            digraph.addEdge(random.nextInt(V), random.nextInt(V));
        }
        return digraph;
    }

    public static void main(String[] args) {
        SimpleDigraph digraph = new SimpleDigraph(10);
        digraph.addEdge(2, 3);
        digraph.addEdge(3, 4);
        System.out.println("digraph = " + digraph);
    }
}
