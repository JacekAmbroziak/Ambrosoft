package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created by jacek on 11/2/16.
 */

public class RequiresProvides {
    // a node of files specify that they provide some modules but that they also require some other modules
    // problem for topological sort
    // data sanity: a single file cannot both provide and require the same module
    // if some required modules are never provided, the system cannot function
    // some files must provide something but not require

    // data modeling: files or modules are nodes?
    // BOTH: so TWO types of nodes: this is a little tricky aspect, but CAN have both

    // code style
    // what needs to happen:
    // 1) build graph
    //     but first register nodes -> registry needed abstraction!
    // to some extent it should be possible to FIRST figure out objects & APIs
    // then even write top-down (save time at interview!)


    static class Node {
        private final String name;

        Node(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node && name.equals(((Node) obj).name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class NodeFile extends Node {
        final Set<Module> requires = new HashSet<>();
        final Set<Module> provides = new HashSet<>();

        NodeFile(String name) {
            super(name);
        }

        void addRequires(Module module) {
            requires.add(module);
        }

        void addProvides(Module module) {
            provides.add(module);
        }
    }

    static class Module extends Node {
        Module(String name) {
            super(name);
        }
    }

    static class NodeRegistry {
        private final Map<Node, Integer> numbering = new HashMap<>();
        private final List<Node> nodes = new ArrayList<>();

        boolean registerNode(Node node) {
            if (numbering.containsKey(node)) {
                return false;
            } else {
                numbering.put(node, nodes.size());
                nodes.add(node);
                return true;
            }
        }

        int nodeCount() {
            return nodes.size();
        }

        int nodeNumber(Node node) {
            Integer num = numbering.get(node);
            if (num != null) {
                return num;
            } else {
                throw new Error("node not found");
            }
        }

        Node getNode(int number) {
            return nodes.get(number);
        }
    }

    static void registerNodes(Collection<NodeFile> files, NodeRegistry registry) {
        for (NodeFile file : files) {
            registry.registerNode(file);

            for (Module module : file.requires) {
                registry.registerNode(module);
            }
            for (Module module : file.provides) {
                registry.registerNode(module);
            }
        }
    }

    static SimpleDigraph buildGraph(Collection<NodeFile> files, NodeRegistry registry) {
        final SimpleDigraph graph = new SimpleDigraph(registry.nodeCount());
        for (NodeFile file : files) {
            final int fileNo = registry.nodeNumber(file);

            for (Module module : file.requires) {
                graph.addEdge(registry.nodeNumber(module), fileNo);
            }
            for (Module module : file.provides) {
                graph.addEdge(fileNo, registry.nodeNumber(module));
            }
        }
        return graph;
    }

    static int[] topologicalSort(final SimpleDigraph graph) {
        final int nodeCount = graph.nodeCount();

        // find nodes with indegree == 0
        // move them to output order
        // remove their edges

        IntegerArray next = new IntegerArray();


        for (int i = nodeCount; --i >= 0; ) {
            if (graph.indegree(i) == 0) {
                next.add(i);
            }
        }

        final int[] order = new int[nodeCount];
        int index = 0;
        while (next.size() > 0) {
            final int node = next.removeLast();
            order[index++] = node;
            for (Integer v : graph.adj(node)) {
                graph.removeEdge(node, v);
                if (graph.indegree(v) == 0) {
                    next.add(v);
                }
            }
        }
        return order;
    }

    static void findOrdering(Collection<NodeFile> files) {
        final NodeRegistry registry = new NodeRegistry();

        registerNodes(files, registry);

        final SimpleDigraph graph = buildGraph(files, registry);

        final int[] order = topologicalSort(graph);

        for (int i = 0; i < order.length; i++) {
            Node node = registry.getNode(order[i]);
            if (node instanceof NodeFile) {
                System.out.println("file = " + node);
            }
        }
    }

    public static void main(String[] args) {
        Module a = new Module("A");
        Module b = new Module("B");
        Module c = new Module("C");

        NodeFile f1 = new NodeFile("f1");
        NodeFile f2 = new NodeFile("f2");
        NodeFile f3 = new NodeFile("f3");

        f1.addProvides(a);
        f1.addRequires(c);

        f2.addRequires(a);
        f2.addProvides(b);

        f3.addRequires(b);

        findOrdering(Arrays.asList(f3, f2, f1));

    }
}
