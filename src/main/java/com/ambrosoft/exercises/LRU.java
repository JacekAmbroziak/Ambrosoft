package com.ambrosoft.exercises;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacek on 11/30/16.
 */

public class LRU<K, V> {

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    static class Node<V> {
        V datum;
        Node<V> next;
        Node<V> prev;

        Node(V datum) {
            this.datum = datum;
        }
    }

    Map<K, Node<V>> map = new HashMap<>();
    Node<V> head = new Node<V>(null);
    final int capacity;
    int count;

    void insert(K key, V value) {
        final Node<V> extant = map.get(key);
        if (extant != null) {
            if (extant.datum.equals(value)) {
                // do nothing
            } else {
                extant.datum = value;
            }
            moveToFront(extant);
        } else {
            Node<V> node = new Node<V>(value);
            map.put(key, node);

        }
    }

    public V get(K key) {
        final Node<V> extant = map.get(key);
        if (extant != null) {
            moveToFront(extant);
            return extant.datum;
        } else {
            return null;
        }
    }

    private void moveToFront(Node<V> node) {

    }


}
