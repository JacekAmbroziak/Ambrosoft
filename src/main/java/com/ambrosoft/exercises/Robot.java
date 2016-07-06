package com.ambrosoft.exercises;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by jacek on 7/5/16.
 */
public class Robot {

    int rows;
    int columns;

    boolean isObstacle(int x, int y) {
        return (x + y) % 3 == 0;
    }

    Board getBoard(int x, int y) {
        return new Board(x, y);
    }

    class Board {
        final int x;
        final int y;

        Board(int x, int y) {
            this.x = x;
            this.y = y;
        }

        List<LinkedList<Board>> solutions() {
            final List<LinkedList<Board>> result = new ArrayList<>();

            if (x == columns && y == rows) {
                result.add(new LinkedList<>());
            } else {
                int x1 = x + 1;

                if (x1 < columns && !isObstacle(x1, y)) {
                    Board board1 = getBoard(x1, y);
                    for (LinkedList<Board> path : board1.solutions()) {

                    }
                }


            }

            return result;
        }


    }


}
