package com.ambrosoft.stablematch

import scala.collection.mutable
import scala.util.Random

/**
  * User: jacek
  * Date: 10/30/15
  * Time: 2:45 PM
  *
  * @author Jacek R. Ambroziak
  */

object StableMatch extends App {
  val problem = new StableMatch(10)
  problem.findMatchings()

  def reverseMatch(matching: Seq[Int]) = matching.zipWithIndex.sortBy(_._1).map(_._2)

  def rotate[A](values: Seq[A]) = values.tail :+ values.head

  def rotated[A](values: Seq[A]) = {
    def aux(seq: Seq[A], n: Int): List[Seq[A]] =
      if (n == 0) Nil
      else {
        val rotated = rotate(seq)
        rotated :: aux(rotated, n - 1)
      }

    (values :: aux(values, values.size - 1)).toVector
  }
}

class StableMatch(n: Int) {
  val (prefsA, prefsB) = {
    val indexes = (0 until n).toVector
    def createPreferences() = indexes.map(i => Random.shuffle(indexes))
    (createPreferences(), createPreferences())
  }

  def ranksHigher(n1: Int, n2: Int, prefs: Seq[Int]) =
    prefs.indexOf(n1) < prefs.indexOf(n2)

  def findMatchings() = {

    matchCost(prefsA, prefsB, "random AB")
    matchCost(prefsB, prefsA, "random BA")
    matchCost(prefsA, prefsA, "random AA")
    matchCost(prefsB, prefsB, "random BB")

    val indexes = (0 until n).toVector
    val prefs = indexes.map(i => indexes)
    val prefsRev = indexes.map(i => indexes.reverse)

    matchCost(prefs, prefs)
    matchCost(prefs, prefsRev)
    matchCost(prefsRev, prefs)
    matchCost(prefsRev, prefsRev)

    val rotated = StableMatch.rotated(indexes)
    // no compromise
    matchCost(rotated, rotated)
  }

  // returns matches for b-side
  def findMatching(prefsA: Seq[Seq[Int]], prefsB: Seq[Seq[Int]]) = {
    // index: b, value: a
    // nobody is engaged yet; no engagement for b represented by -1
    val matchesForB = Array.fill(n)(-1)
    // proposalIndex is an array indexed by a giving index into a's preference list
    // nobody proposed yet: -1
    val proposalIndex = Array.fill(n)(-1)
    // queue of free a-s
    val freeA = mutable.Queue.empty[Int]
    // initialize all men are free
    freeA ++= 0 until n

    while (freeA.nonEmpty) {
      // take first free a
      val a = freeA.dequeue()
      val newChoiceIndex = proposalIndex(a) + 1
      // any choices left for a?
      if (newChoiceIndex < n) {
        val b = prefsA(a)(newChoiceIndex)
        val currentAMatch = matchesForB(b)

        if (currentAMatch == -1) {
          matchesForB(b) = a
        } else if (ranksHigher(a, currentAMatch, prefsB(b))) {
          matchesForB(b) = a
          freeA += currentAMatch
        } else {
          freeA += a
        }
        proposalIndex(a) = newChoiceIndex
      }
    }

    matchesForB
  }

  def matchCost(prefsA: Seq[Seq[Int]], prefsB: Seq[Seq[Int]], msg: String = "") = {
    val matchesForB = findMatching(prefsA, prefsB)
    val matchesForA = StableMatch.reverseMatch(matchesForB)

    def cost(t: Tuple2[Seq[Int], Int]) = t._1.indexOf(t._2)

    val costsA = prefsA.zip(matchesForA).map(cost)
    val costsB = prefsB.zip(matchesForB).map(cost)

    println(msg + "\ta " + costsA.sum + "\tb " + costsB.sum)
    println(matchesForA.zipWithIndex)
    println(matchesForB.zipWithIndex)
  }
}
