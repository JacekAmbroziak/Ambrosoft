package com.ambrosoft

import scala.collection.mutable

/**
  * Created by jacek on 7/5/16.
  */


object Robot extends App {
  val board = new Board(5, 5, (x, y) => x < y)
  val sol = board.getProblem(0, 0).solutions
  println(sol)
}

// a Board with dimension and obstacle predicate
class Board(rows: Int, columns: Int, isObstacle: (Int, Int) => Boolean) {
  val problems = mutable.Map[(Int, Int), Problem]()

  def getProblem(xy: (Int, Int)) = {
    problems.get(xy) match {
      case Some(problem) => problem
      case None => {
        val newProblem = Problem.tupled(xy)
        problems.update(xy, newProblem)
        newProblem
      }
    }
  }

  case class Problem(x: Int, y: Int) {
    lazy val solutions: Seq[List[(Int, Int)]] =
      if (x == columns - 1 && y == rows - 1)
        Seq(List())
      else Seq(moveDown(), moveRight()).flatten flatMap {
        xy => combine(xy, getProblem(xy))
      }

    def combine(xy: (Int, Int), problem: Problem): Seq[List[(Int, Int)]] =
      problem.solutions.map(xy :: _)

    def moveDown(): Option[(Int, Int)] =
      Option(y + 1).filter(_ < rows).filterNot(isObstacle(x, _)).map((x, _))

    def moveRight(): Option[(Int, Int)] =
      Option(x + 1).filter(_ < columns).filterNot(isObstacle(_, y)).map((_, y))
  }

}
