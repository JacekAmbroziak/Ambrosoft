package com.ambrosoft.exercises;

import java.util.TreeSet;

/**
 * Created on 12/9/17
 * <p>
 * https://www.codewars.com/kata/which-are-in
 */
public class WhichAreIn {
    public static String[] inArray(String[] array1, String[] array2) {
        final TreeSet<String> okSet = new TreeSet<>();
        for (String s1 : array1) {
            boolean found = false;
            for (String s2 : array2) {
                if (s2.contains(s1)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                okSet.add(s1);
            }
        }
        return okSet.toArray(new String[okSet.size()]);
    }
}
