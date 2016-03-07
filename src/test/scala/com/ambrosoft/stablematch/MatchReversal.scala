package com.ambrosoft.stablematch

import org.scalatest.FlatSpec

import scala.util.Random

/**
  * User: jacek
  * Date: 11/3/15
  * Time: 8:10 AM
  *
  * @author Jacek R. Ambroziak
  */

class MatchReversal extends FlatSpec {
  val n = 1000
  val indexes = (0 until n).toVector

  val (prefsA, prefsB) = {
    def createPreferences() = indexes.map(i => Random.shuffle(indexes))
    (createPreferences(), createPreferences())
  }

  "double reversal of match" should "produce original match" in {
    val match0 = Random.shuffle(indexes).toArray
    assert(StableMatch.reverseMatch(StableMatch.reverseMatch(match0)) === match0)
  }

  "reversal of identity" should "produce identity" in {
    assert(StableMatch.reverseMatch(indexes) === indexes)
  }

}
