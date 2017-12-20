package com.ambrosoft.exercises;

import java.util.HashMap;

/**
 * Created on 12/9/17
 */
public class LoopSize {

    static class Node {
        Node getNext() {
            return null;
        }
    }

    public int loopSize(Node node) {
        HashMap<Node, Integer> map = new HashMap<>();
        int n = 0;
        map.put(node, n++);
        while (true) {
            node = node.getNext();
            if (map.containsKey(node)) {
                return n - map.get(node);
            } else {
                map.put(node, n++);
            }
        }
    }
}
