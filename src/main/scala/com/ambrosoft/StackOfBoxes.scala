package com.ambrosoft

import scala.util.Random

/**
  * Created by jacek on 7/9/16.
  *
  * Find the highest stack (sum of heights; maximize that sum)
  * subject to the constraint that only a strictly smaller block can be placed on another
  * (consequence: equal blocks (duplicates) can be removed)
  *
  * All blocks can be ordered by volume (total ordering)
  * "strictly smaller" is not a total ordering
  * A winning stack will be some sub-sequence of total ordering
  *
  */
object StackOfBoxes extends App {


  case class Box(w: Int, d: Int, h: Int) extends Ordered[Box] {
    val volume = w * d * h

    override def compare(that: Box): Int = volume - that.volume

    def strictlySmallerThan(that: Box) = w < that.w && d < that.d && h < that.h

  }

  /**
    * Given a sorted list of ever smaller boxes
    * return all stackable sublists
    */
  def sequences(blocks: List[Box]): Seq[List[Box]] = {

    blocks match {
      case biggest :: tail => {
        val subseq = sequences(tail)
        println(subseq)
        subseq.flatMap(stack => combine(biggest, stack))
      }

      case Nil => Seq(List())
    }

  }

  def combine(base: Box, stack: List[Box]): List[List[Box]] = {
    stack match {
      case Nil => List(List(base))
      case biggest :: _ if biggest.strictlySmallerThan(base) => List(base :: stack, stack)
      case _ => List(stack)
    }
  }

  def compute(boxes: Seq[Box]): Int = {
    val dedup = boxes.toSet.toList
    val maxHeight = dedup.map(_.h).sum

    val sorted = dedup.sorted.reverse

    val solutions = sequences(sorted)

    println(solutions.map(verifyStack))
    println(solutions.map(sol => sol.map(_.h).sum))

    maxHeight
  }

  def verifyStack(stack: List[Box]): Boolean = {
    stack match {
      case first :: second :: rest => second.strictlySmallerThan(first) && verifyStack(second :: rest)
      case _ => true
    }
  }

  def randomBox(min: Int, max: Int): Box = {
    def nonZeroSize() = Random.nextInt(max - min) + min

    Box(nonZeroSize(), nonZeroSize(), nonZeroSize())
  }

  println(Box(2, 2, 2).compare(Box(2, 2, 2)))
  println(Box(2, 2, 2).strictlySmallerThan(Box(2, 2, 3)))
  println(Box(2, 3, 2).volume)

  val boxes = (1 to 20).map(_ => randomBox(2, 10))

  println(boxes)
  println(compute(boxes))

}
