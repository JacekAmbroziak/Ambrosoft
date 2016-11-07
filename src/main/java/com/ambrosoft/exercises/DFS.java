package com.ambrosoft.exercises;

import java.util.Random;

/**
 * Created by jacek on 11/4/16.
 */

public class DFS {

    // could be "state" like DISCOVERED, FINISHED
    enum NodeColor {
        WHITE, GRAY, BLACK
    }

    static void dfs(SimpleDigraph digraph) {
        final int nodeCount = digraph.nodeCount();
        final NodeColor[] color = new NodeColor[nodeCount];
        final int[] disTime = new int[nodeCount];
        final int[] finTime = new int[nodeCount];
        final int[] pred = new int[nodeCount];  // predecessor in DFS tree
        for (int i = nodeCount; --i >= 0; ) {
            color[i] = NodeColor.WHITE; // not discovered
            pred[i] = -1;   // not set
        }

        int time = 0;
        for (int i = 0; i < nodeCount; i++) {
            if (color[i] == NodeColor.WHITE) {
                time = dfsVisit(digraph, color, disTime, finTime, pred, i, time);
            }
        }
        System.out.println("time = " + time);
    }

    private static int dfsVisit(SimpleDigraph digraph, NodeColor[] color, int[] disTime, int[] finTime, int[] pred, int node, int time) {
        color[node] = NodeColor.GRAY;   // discovered
        disTime[node] = ++time;
        for (final int v : digraph.adj(node)) {
            if (color[v] == NodeColor.WHITE) {
                pred[v] = node;
                time = dfsVisit(digraph, color, disTime, finTime, pred, v, time);
            }
        }
        color[node] = NodeColor.BLACK;  // finished
        finTime[node] = ++time;
        return time;
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
        SimpleDigraph rdag = createRandomGraph(100, 100);
        System.out.println(rdag);

        dfs(rdag);
    }
}
