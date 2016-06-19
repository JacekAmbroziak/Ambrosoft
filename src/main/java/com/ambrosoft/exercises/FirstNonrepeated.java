package com.ambrosoft.exercises;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jacek on 6/16/16.
 */
public class FirstNonrepeated {
    public static void main(String[] args) {
        char c = foo("salesforce is the best company to work for lihbm");
        System.out.println("c=" + c);
    }

    private static Character foo(String str) {
        HashMap<Character, Integer> indexes = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            if (indexes.get(str.charAt(i)) != null) {
                indexes.put(str.charAt(i), Integer.MAX_VALUE);
            } else {
                indexes.put(str.charAt(i), i);
            }
        }
        TreeMap<Integer, Character> treeMap = new TreeMap<>();
        for (Map.Entry<Character, Integer> entry : indexes.entrySet()) {
            treeMap.put(entry.getValue(), entry.getKey());
        }
        return treeMap.firstEntry().getValue();
    }
}
