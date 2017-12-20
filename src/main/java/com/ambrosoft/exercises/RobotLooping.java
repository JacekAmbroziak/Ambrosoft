package com.ambrosoft.exercises;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by jacek on 10/31/17.
 */
public class RobotLooping {

    enum Direction {
        RIGHT(1, 0),
        LEFT(-1, 0),
        UP(0, 1),
        DOWN(0, -1);

        int x;
        int y;

        Direction(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Direction turnLeft() {
            switch (this) {
                case RIGHT:
                    return UP;
                case LEFT:
                    return DOWN;
                case UP:
                    return LEFT;
                case DOWN:
                    return RIGHT;
                default:
                    throw new IllegalArgumentException();
            }
        }

        Direction turnRight() {
            switch (this) {
                case RIGHT:
                    return DOWN;
                case LEFT:
                    return UP;
                case UP:
                    return RIGHT;
                case DOWN:
                    return LEFT;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }


    private static class Point {
        final int x;
        final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        Point advance(Direction dir) {
            return new Point(x + dir.x, y + dir.y);
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Point) {
                final Point p = (Point) obj;
                return x == p.x && y == p.y;
            }
            return false;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ')';
        }
    }


    public static boolean isLoop(String route) {
        final Set<Point> visited = new HashSet<>();

        Point current = new Point(0, 0);
        Direction dir = Direction.RIGHT;

        visited.add(current);

        for (char op : route.toCharArray()) {
            switch (op) {
                case 'G':
                    current = current.advance(dir);
                    System.out.println("current = " + current);
                    if (!visited.add(current)) {
                        return true;
                    }
                    break;

                case 'L':
                    dir = dir.turnLeft();
                    break;

                case 'R':
                    dir = dir.turnRight();
                    break;

                default:
                    throw new IllegalArgumentException();
            }
        }
        return false;
    }

    static void test(String route) {
        System.out.println("route = " + route + '\t' + isLoop(route));
    }

    public static void main(String[] args) {
        test("GGG");
        test("GRGGGRGRGGGRG");
        test("GGGGRRRRRRRRRRRRRRR");
    }
}
