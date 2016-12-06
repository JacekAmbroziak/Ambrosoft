package com.ambrosoft.exercises;

/**
 * Created by jacek on 11/4/16.
 */

public class DFS {

    // could be "state" like UNDISCOVERED, DISCOVERED, FINISHED
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
        for (int node = 0; node < nodeCount; node++) {
            if (color[node] == NodeColor.WHITE) {
                time = dfsVisit(digraph, color, disTime, finTime, pred, node, time);
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

    public static void main(String[] args) {
        SimpleDigraph rdag = SimpleDigraph.createRandomGraph(100, 100);
        System.out.println(rdag);

        dfs(rdag);
    }
}
