package com.ambrosoft.exercises;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by jacek on 10/16/16.
 */

public class CircusTower {


    static final class Person {
        int ht;
        int wt;

        Person(int ht, int wt) {
            this.ht = ht;
            this.wt = wt;
        }

        @Override
        public String toString() {
            return "(" + ht + ", " + wt + ')';
        }
    }



    static Person[] readData(String data) {
        String[] pairs = data.split("\\(");
        Person[] people = new Person[pairs.length - 1];
        for (int i = 1, j = 0; i < pairs.length; ++i) {
            String pair = pairs[i];
            int comma = pair.indexOf(',');
            int ht = Integer.parseInt(pair.substring(0, comma).trim());
            int wt = Integer.parseInt(pair.substring(comma + 1, pair.indexOf(')', comma)).trim());
            people[j++] = new Person(ht, wt);
        }
        return people;
    }

    public static void main(String[] args) {
        Person[] people = readData("(65, 100) (70, 150) (56, 90) (75, 190) (60, 95) (68, 110)");
        System.out.println(Arrays.toString(people));

        Arrays.sort(people, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.wt != o2.wt ? o1.wt - o2.wt : o1.ht - o2.ht;
            }
        });

        System.out.println(Arrays.toString(people));

        // after sorting by weight extract heights and look for longest increasing subseq

    }
}
