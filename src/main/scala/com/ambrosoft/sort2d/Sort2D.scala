package com.ambrosoft.sort2d

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.util.Random
import scala.util.Sorting.quickSort

object Sort2D extends App {
  val N = 7
  val M = 4
  val matrix = Array.ofDim[Int](N, M)

  show(matrix)

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
  show(sortRows(r))

  println(isSorted(r))
  println(isSorted(matrix))

  val diags = diagonals(N, M)
  val ordered = enumerate(r)


  //  (diags zip ordered).foreach { case ((x, y), v) => r(x)(y) = v }
  (diags zip sortAll(r)).foreach { case ((x, y), v) => r(x)(y) = v }

  show(r)
  println(isSorted(r))


  def sortRows(m: Array[Array[Int]]): Array[Array[Int]] = {
    m foreach quickSort
    m
  }


  def isSorted(m: Array[Array[Int]]): Boolean = {
    val nn = m.length
    val mm = m(0).length

    (0 until nn).forall { x =>
      (0 until mm).forall { y =>
        (x + 1 == nn || m(x)(y) <= m(x + 1)(y)) && (y + 1 == mm || m(x)(y) <= m(x)(y + 1))
      }
    }
  }

  def diagonals(n: Int, m: Int): List[(Int, Int)] = {
    val topRow = (0 until n).toList.map((_, 0))
    val rightColumn = (1 until m).toList.map((n - 1, _))

    println(topRow ++ rightColumn)

    val points = (topRow ++ rightColumn).flatMap(start => diagonal(start._1, start._2))
    println(points)
    println(points.length)
    points
  }

  def diagonal(i: Int, j: Int): List[(Int, Int)] = {
    if (i == -1 || j == M)
      Nil
    else
      (i, j) :: diagonal(i - 1, j + 1)
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

  def sortAll(m: Array[Array[Int]]) = {
    sortRows(m).reduceLeft((a1: Array[Int], a2: Array[Int]) => (a1 ++ a2).sorted)
  }

}


