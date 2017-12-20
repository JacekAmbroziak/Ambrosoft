package com.ambrosoft.exercises;

import java.util.*;

/**
 * Created on 12/4/17
 * <p>
 * Amazon phone screen
 * Given alphabet and some pairs relating symbols: x precedes y, can we reconstruct total order?
 * <p>
 * First observations: no dups in alphabet, no dups among pairs, no x < x, no cycles, all symbols covered by pairs
 * These are necessary conditions to recover total order
 * <p>
 * Tao Chen, the interviewer, suggested recursive approach, concentrating on maximum/last element
 * My solution was copied from previously solved SecretDetective CodeWars problem and employs the concept
 * of propagating sequence numbers to guarantee that a successor always has a bigger seqNo than predecessor
 * We process "edges" one by one and have total order when N seqNos have been generated and assigned
 * Fewer than N (alphabet cardinality) and we have ambiguity, more than N and we have a cycle
 * <p>
 * Ideas on Tao's hint: last element will never be a predecessor;
 * identify such MAX element, and consider a smaller problem, with alphabet w/o MAX, and set of pairs
 * w/o pairs ending with that MAX
 * <p>
 * What happens if set of pairs has redundancy? The only redundancy would come from transitivity
 */


class MarsOrder {

    // solution presented to Tao
    static class Graph {
        Map<Character, Node> map = new HashMap<>();

        Node forChar(char c) {
            Node n = map.get(c);
            if (n == null) {
                map.put(c, n = new Node(c));
            }
            return n;
        }

        void addEdge(char c1, char c2) {
            Node n1 = forChar(c1);
            Node n2 = forChar(c2);
            if (!n1.succ.contains(n2)) {
                n1.succ.add(n2);
            }
            n1.propagateSeqNo(n1.seqNo + 1);
        }
    }

    static class Node {
        char symbol;
        ArrayList<Node> succ = new ArrayList<>();
        int seqNo = 0;

        Node(char c) {
            this.symbol = c;
        }

        void propagateSeqNo(int minSeqNo) {
            for (Node s : succ) {
                if (s.seqNo < minSeqNo) {
                    s.seqNo = minSeqNo;
                    s.propagateSeqNo(minSeqNo + 1);
                }
            }
        }
    }

    // recursive solution

    static class Precedes {
        final Character first;
        final Character second;

        Precedes(Character first, Character second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public String toString() {
            return String.format("%c < %c", first, second);
        }

        @Override
        public int hashCode() {
            return first * 31 + second;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Precedes && ((Precedes) obj).first == first && ((Precedes) obj).second == second;
        }
    }

    static List<Character> orderSymbols(Set<Character> symbols, Set<Precedes> pairs) {
        // find symbol that does not occur as first in any of the pairs
        Character last = null;
        {
            final HashSet<Character> firsts = new HashSet<>();
            for (Precedes precedes : pairs) {
                firsts.add(precedes.first);
            }
            for (Character character : symbols) {
                if (!firsts.contains(character)) {
                    if (last != null) { // we already have a last
                        return null;
                    } else {
                        last = character;
                    }
                }
            }
        }
        if (last == null) {
            return null;
        } else {
            symbols.remove(last);

            if (symbols.size() == 0) {
                ArrayList<Character> bottom = new ArrayList<>();
                bottom.add(last);
                return bottom;
            } else {
                final Set<Precedes> pairs1 = new HashSet<>();
                for (Precedes precedes : pairs) {
                    if (!precedes.second.equals(last)) {
                        pairs1.add(precedes);
                    }
                }

                final List<Character> order1 = orderSymbols(symbols, pairs1);
                if (order1 == null) {
                    return null;
                } else {
                    order1.add(last);
                    return order1;
                }
            }
        }
    }

    public static void main(String[] args) {
        char[][] triplets = {
                {'t', 'u', 'p'},
                {'w', 'h', 'i'},
                {'t', 's', 'u'},
                {'a', 't', 's'},
                {'h', 'a', 'p'},
                {'t', 'i', 's'},
                {'w', 'h', 's'}
        };

        HashSet<Precedes> orderings = new HashSet<>();
        HashSet<Character> alphabet = new HashSet<>();
        for (char[] triplet : triplets) {
            orderings.add(new Precedes(triplet[0], triplet[1]));
            orderings.add(new Precedes(triplet[1], triplet[2]));
            alphabet.add(triplet[0]);
            alphabet.add(triplet[1]);
            alphabet.add(triplet[2]);
        }

        List<Character> ordered = orderSymbols(alphabet, orderings);

        System.out.println();
    }
}