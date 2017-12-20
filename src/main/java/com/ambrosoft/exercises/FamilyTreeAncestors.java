package com.ambrosoft.exercises;

import java.util.*;

public class FamilyTreeAncestors {
    private static final String SPACES = "           ";
    /*
        Printing a chart horizontally appears tricky because the order of lines is very different
        from the order of visiting the hierarchy

                                           |16 _______
                                |08 _______
                                |          |17 _______
                     |04 _______
                     |          |          |18 _______
                     |          |09 _______
                     |                     |19 _______
          |02 _______
          |          |                     |20 _______
          |          |          |10 _______
          |          |          |          |21 _______
          |          |05 _______
          |                     |          |22 _______
          |                     |11 _______
          |                                |23 _______
01 XXXXX
          |                                |24 _______
          |                     |12 _______
          |                     |          |25 _______
          |          |06 _______
          |          |          |          |26 _______
          |          |          |13 _______
          |          |                     |27 _______
          |03 _______
                     |                     |28 _______
                     |          |14 _______
                     |          |          |29 _______
                     |07 _______
                                |          |30 _______
                                |15 _______
                                           |31 _______


        But, closer analysis uncovers inorder:
        1) recursively output 1st parent
        2) output person
        3) recursively output 2nd parent

        Recursion should take level argument to be used for indentation, and it should return number of lines printed
     */

    static class Person {
        final String _sex;
        final String _name;
        ArrayList<Person> parents;

        Person(String sex, String name) {
            _sex = sex;
            _name = name;
        }

        String sex() {
            return _sex;
        } // "M" or "F"

        String name() {
            return _name;
        }

        List<Person> parents() {
            if (parents != null) {
                return parents;
            } else {
                final ArrayList<Person> people = new ArrayList<>(2);
                people.add(new Person("M", generateName()));
                people.add(new Person("F", generateName()));
                return parents = people;
            }
        }

        private static String generateName() {
            return UUID.randomUUID().toString().substring(0, 6);
        }

        @Override
        public String toString() {
            return String.format("%s: %s", _sex, _name);
        }
    }

    static void explore(Person person, int level) {
        System.out.println("person = " + person);
        if (level > 0) {
            for (Person parent : person.parents()) {
                explore(parent, level - 1);
            }
        }
    }


    private static Person getParent(Person person, String parentSex) {
        if (person != null) {
            for (Person parent : person.parents()) {
                if (parentSex.equals(parent.sex())) {
                    return parent;
                }
            }
        }
        return null;
    }

    private static Person getFather(Person person) {
        return getParent(person, "M");  // "M" could stand for Mother :-)
    }

    private static Person getMother(Person person) {
        return getParent(person, "F");  // "F" could stand for Father :-)
    }

    private static String getName(Person person) {
        return person != null ? person.name() : "_______";
    }

    // first working version which "fixes" the last character of prefix at level to start | at even/father and cancel at mother
    static void printChart(Person person, int number, int level, String prefix, StringBuilder sb) {
        if (level > 0) {
            final List<Person> parents = person != null ? person.parents() : Collections.emptyList();
            Person father = null, mother = null;
            for (Person parent : parents) {
                if (parent.sex().equals("M")) {
                    father = parent;
                } else {
                    mother = parent;
                }
            }

            printChart(father, 2 * number, level - 1, prefix + "           ", sb);

            final int last = prefix.length() - 1;

            if (number % 2 == 0 && last > 0) {
                prefix = prefix.substring(0, last) + "/";
            }
            // print person
            sb.append(prefix).append(String.format("%02d ", number)).append(person != null ? person.name() : "_______").append('\n');

            if (number % 2 == 1 && last > 0) {
                prefix = prefix.substring(0, last) + " ";
            }
            printChart(mother, 2 * number + 1, level - 1, prefix + "          !", sb);
        }
    }

    private static void printPerson2(StringBuilder sb, Deque<String> indents, Person person, int number) {
        for (String indent : indents) {
            sb.append(indent);
        }
        sb.append(String.format("%02d ", number))
                .append(getName(person))
                .append('\n');
    }

    // same as printChart but using a queue/reversed stack instead of modifying strings
    static void printChart2(StringBuilder sb, Deque<String> indents, Person person, int number, int levelsToDo) {
        final int remaining = levelsToDo - 1;

        if (remaining > 0) {
            indents.addLast(SPACES);
            printChart2(sb, indents, getFather(person), 2 * number, remaining);
            indents.removeLast();
        }

        if (number % 2 == 0) {
            if (!indents.isEmpty()) {
                indents.removeLast();
                indents.addLast("          /");
            }
        }

        printPerson2(sb, indents, person, number);

        if (remaining > 0) {
            if (number % 2 != 0) {
                if (!indents.isEmpty()) {
                    indents.removeLast();
                    indents.addLast(SPACES);
                }
            }

            indents.addLast("          !");
            printChart2(sb, indents, getMother(person), 2 * number + 1, remaining);
            indents.removeLast();
        }
    }

    static String chart(Person person, int levels) {
        final StringBuilder sb = new StringBuilder();
        printChart2(sb, new ArrayDeque<>(), person, 1, levels);
        return sb.toString();
    }

    static String chart3(Person person, int levelsToOpen) {
        final StringBuilder sb = new StringBuilder();
        final char[] indentation = new char[128];
        Arrays.fill(indentation, ' ');
        printChart3(sb, indentation, 0, person, 1, levelsToOpen);
        return sb.toString();
    }

    static String chart3EvenOdd(Person person, int levelsToOpen) {
        final StringBuilder sb = new StringBuilder();
        final char[] indentation = new char[levelsToOpen * 10];
        Arrays.fill(indentation, ' ');
        printChartMother(sb, indentation, 0, person, 1, levelsToOpen);
        return sb.toString();
    }

    // same as previous but reusing an array of indentation chars where bar is started/canceled depending on even/odd status of arg
    private static void printChart3(StringBuilder sb, char[] indentation, int indent, Person person, int number, int levelsToOpen) {
        final int remaining = levelsToOpen - 1;
        if (remaining > 0) {
            printChart3(sb, indentation, indent + 10, getFather(person), 2 * number, remaining);
        }

        if (number % 2 == 0) {
            if (indent > 0) {
                indentation[indent - 1] = '|';
            }
        }

        sb.append(indentation, 0, indent+1)
                .append(String.format("%02d ", number))
                .append(getName(person))
                .append('\n');

        if (remaining > 0) {
            if (number % 2 != 0) {
                if (indent > 0) {
                    indentation[indent - 1] = ' ';
                }
            }

            indentation[indent + 10] = '|';
            printChart3(sb, indentation, indent + 11, getMother(person), 2 * number + 1, remaining);
        }
    }

    // same as above but using the fact that we know ahead of time if we process from even or odd perspective
    private static void printChartFather(StringBuilder sb, char[] indentation, int indent, Person person, int number, int levelsToOpen) {
        final int remainingLevels = levelsToOpen - 1;
        if (remainingLevels > 0) {
            printChartFather(sb, indentation, indent + 11, getFather(person), 2 * number, remainingLevels);
        }
        if (indent > 0) {
            indentation[indent - 1] = '|';  // start bar at this level
        }
        // print person using indentation
        sb.append(indentation, 0, indent).append(String.format("%02d ", number)).append(getName(person)).append('\n');
        if (remainingLevels > 0) {
            indentation[indent + 10] = '|';
            printChartMother(sb, indentation, indent + 11, getMother(person), 2 * number + 1, remainingLevels);
        }
    }

    private static void printChartMother(StringBuilder sb, char[] indentation, int indent, Person person, int number, int levelsToOpen) {
        final int remainingLevels = levelsToOpen - 1;
        if (remainingLevels > 0) {
            printChartFather(sb, indentation, indent + 11, getFather(person), 2 * number, remainingLevels);
        }
        // print person using indentation
        sb.append(indentation, 0, indent).append(String.format("%02d ", number)).append(getName(person)).append('\n');
        if (remainingLevels > 0) {
            if (indent > 0) {
                indentation[indent - 1] = ' ';  // cancel bar at this level
            }
            indentation[indent + 10] = '|';
            printChartMother(sb, indentation, indent + 11, getMother(person), 2 * number + 1, remainingLevels);
        }
    }


    public static void main(String[] args) {
        Person person = new Person("F", "Marzena");
//        explore(person, 3);
//        System.out.println(chart(person, 5));
        System.out.println(chart3EvenOdd(person, 5));

        final String expected =
                "                                           |16 Frederick James\n" +
                        "                                |08 Frederick\n" +
                        "                                |          |17 Jessie\n" +
                        "                     |04 Geoffery\n" +
                        "                     |          |          |18 _______\n" +
                        "                     |          |09 Thelma\n" +
                        "                     |                     |19 _______\n" +
                        "          |02 Peter\n" +
                        "          |          |                     |20 _______\n" +
                        "          |          |          |10 Alan\n" +
                        "          |          |          |          |21 _______\n" +
                        "          |          |05 Yvonne\n" +
                        "          |                     |          |22 James\n" +
                        "          |                     |11 Violet\n" +
                        "          |                                |23 Violet Lillian\n" +
                        "01 Samantha\n" +
                        "          |                                |24 _______\n" +
                        "          |                     |12 _______\n" +
                        "          |                     |          |25 _______\n" +
                        "          |          |06 William\n" +
                        "          |          |          |          |26 _______\n" +
                        "          |          |          |13 _______\n" +
                        "          |          |                     |27 _______\n" +
                        "          |03 Karyn\n" +
                        "                     |                     |28 _______\n" +
                        "                     |          |14 _______\n" +
                        "                     |          |          |29 _______\n" +
                        "                     |07 Jean\n" +
                        "                                |          |30 _______\n" +
                        "                                |15 _______\n" +
                        "                                           |31 _______\n";

        System.out.println(expected);

    }

}
