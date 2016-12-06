package com.ambrosoft.exercises;

import java.util.LinkedList;

/**
 * Created by jacek on 11/12/16.
 */

public class BFS {
    enum NodeColor {
        UNDISCOVERED, DISCOVERED, FINISHED
    }

    static void bfs(SimpleDigraph digraph, int source) {
        final int nodeCount = digraph.nodeCount();
        final NodeColor[] color = new NodeColor[nodeCount];
        final int[] disTime = new int[nodeCount];
        final int[] finTime = new int[nodeCount];
        final int[] dist = new int[nodeCount];
        final int[] pred = new int[nodeCount];  // predecessor in BFS tree
        for (int node = nodeCount; --node >= 0; ) {
            color[node] = NodeColor.UNDISCOVERED; // not discovered
            pred[node] = -1;   // not set
            dist[node] = Integer.MAX_VALUE;
        }

        color[source] = NodeColor.DISCOVERED; // discover source
        dist[source] = 0;
        pred[source] = -1;  // NIL

        final LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        int time = 0;
        while (queue.size() > 0) {
            final int u = queue.remove();
            System.out.println("u = " + u);
            for (final int v : digraph.adj(u)) {
                if (color[v] == NodeColor.UNDISCOVERED) {   // only consider undiscovered neighbors
                    color[v] = NodeColor.DISCOVERED;
                    dist[v] = dist[u] + 1;
                    pred[v] = u;    // add edge to the BST tree
                    disTime[v] = ++time;
                    queue.add(v);   // enqueue the newly discovered node
                }
            }
            color[u] = NodeColor.FINISHED;
            finTime[u] = ++time;
        }

        System.out.println("time = " + time);
    }

    public static void main(String[] args) {
        SimpleDigraph rdag = SimpleDigraph.createRandomGraph(100, 100);
        System.out.println(rdag);

        bfs(rdag, 0);
    }
}
