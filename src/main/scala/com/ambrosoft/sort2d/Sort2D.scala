package com.ambrosoft.sort2d

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random
import scala.util.Sorting.quickSort

/*
  Sorting a 2D Int matrix so that each row and each column is sorted

  The approach:
  1) sort all values
  2) come up with a sequence of cell coords starting with upper left, diagonals
  3) assign sorted values to these coords in order

  In this functional approach coords are materialized
 */


object Sort2D extends App {
  val N = 7
  val M = 4
  //  val matrix = Array.ofDim[Int](N, M)

  //  show(matrix)

  def show(m: Array[Array[Int]]) = {
    m foreach { row =>
      row foreach { num =>
        print(num)
        print(' ')
      }
      println()
    }
    println()
  }

  def randomMatrix = Array.fill(N)(Array.fill(M)(Random.nextInt(N * M)))

  val r = randomMatrix

  show(r)
  //  show(sortRows(r))

  println(isSorted(r))
  //  println(isSorted(matrix))

  val diags = diagonals(N, M)
  //  val ordered = enumerate(r)

  println(sortAll(r).toList)
  println(diags)

  (diags zip sortAll(r)).foreach { case ((row, col), v) => r(row)(col) = v }

  show(r)
  println(isSorted(r))


  def sortRows(m: Array[Array[Int]]): Array[Array[Int]] = {
    m foreach quickSort
    m
  }

  def isSorted(m: Array[Array[Int]]): Boolean = {
    val nRows = m.length
    val nCols = m(0).length

    (0 until nRows).forall { row =>
      (0 until nCols).forall { col =>
        (row + 1 == nRows || m(row)(col) <= m(row + 1)(col)) && (col + 1 == nCols || m(row)(col) <= m(row)(col + 1))
      }
    }
  }

  def diagonals(n: Int, m: Int): List[(Int, Int)] = {
    val leftColumn = (0 until n).toList.map((_, 0))
    val bottomRow = (1 until m).toList.map((n - 1, _)) // w/o right corner

    // leftColumn ++ bottomRow: starting points for diagonals

    println(leftColumn ++ bottomRow)

    (leftColumn ++ bottomRow).flatMap(start => diagonal(start._1, start._2))
  }

  def diagonal(row: Int, col: Int): List[(Int, Int)] = {
    if (row == -1 || col == M)
      Nil
    else
      (row, col) :: diagonal(row - 1, col + 1)
  }

  case class ArrayDatum(a: Array[Int], index: Int) extends Ordered[ArrayDatum] {
    def value = a(index)

    def step: Option[ArrayDatum] = if (index + 1 < a.length) Some(ArrayDatum(a, index + 1)) else None

    override def compare(that: ArrayDatum): Int = that.value compare value
  }

  def enumerate(m: Array[Array[Int]]): List[Int] = {
    val queue = mutable.PriorityQueue[ArrayDatum]()

    m.foreach { a =>
      quickSort(a)
      queue.enqueue(ArrayDatum(a, 0))
    }

    val buf = new ListBuffer[Int]()

    while (queue.nonEmpty) {
      val element = queue.dequeue()
      buf += element.value
      element.step match {
        case Some(datum) => queue.enqueue(datum)
        case None =>
      }
    }

    buf.toList
  }

  def sortAll(m: Array[Array[Int]]): Array[Int] = {
    sortRows(m).reduceLeft((a1: Array[Int], a2: Array[Int]) => (a1 ++ a2).sorted)
  }
}


