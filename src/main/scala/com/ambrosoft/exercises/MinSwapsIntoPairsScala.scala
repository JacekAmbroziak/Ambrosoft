package com.ambrosoft.exercises

/**
  * Created by jacek on 10/10/16.
  */
object MinSwapsIntoPairsScala extends App {

  /**
    * Minimum number of swaps to rearrange to adjacent pairs
    * http://www.geeksforgeeks.org/minimum-number-of-swaps-required-for-arranging-pairs-adjacent-to-each-other/
    *
    * solution by a user
    *
    * @param arr      Array of integers
    * @param pairings Map of pairs
    * @return Tuple of the minimum number of swaps and the rearrangements with minimal
    *         swaps such that each rearrangement is a list of adjacent pairs
    */
  def minSwapsForAdjacentPairs(arr: Array[Int], pairings: Map[Int, Int]): (Int, Seq[Seq[Int]]) = {
    // Add each (v -> k) to map
    val pairs = pairings ++ (pairings map { case (k, v) => (v, k) })

    def swapEm(i: Int, lastElem: Int, rest: Seq[Int], changes: Int): Seq[(Int, Seq[Int])] = {
      if (rest.isEmpty) Seq((changes, Seq()))
      else {
        val head = rest.head
        for {
          elem <- rest
          if i % 2 == 0 || (i % 2 == 1 && pairs(elem) == lastElem) // Prune if not a pair
          remaining = rest.tail.map(e => if (e == elem) head else e) // Swap elem
          diff = if (elem != head) 1 else 0 // Add 1 if swapped
          (chgs, suffix) <- swapEm(i + 1, elem, remaining, changes + diff)
        } yield (chgs, elem +: suffix)
      }
    }

    val swaps = swapEm(0, 0, arr, 0)

    val min = swaps.map(_._1).min
    (min, swaps.filter(_._1 == min).map(_._2))
  }


  val pairings = (1 until 14 by 2).map(n => (n, n + 1)).toMap
  val two = pairings.get(1)
  val augm = pairings ++ (pairings map (_.swap))
  val answer = minSwapsForAdjacentPairs(Array(14, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13), pairings)
  println(answer)

}
