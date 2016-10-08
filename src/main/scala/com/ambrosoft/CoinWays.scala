package com.ambrosoft

/**
  * Created by jacek on 7/8/16.
  *
  * Approach:
  *
  * can imagine all ways canonicalized by sorting from bigger coins to smaller
  * thus if n > 25, at least one family of ways will start with 25
  * followed by all the ways (n - 25) can be expressed, recursively (which therefore will include
  * starting with the 2nd quarter, etc.
  *
  * Then we need to give a chance to starting with 10 (but not going back to 25!)
  *
  */

object CoinWays extends App {

  // use diminishing coin values
  def countWays(n: Int) = count(List(25, 10, 5, 1), n)

  private def test(n: Int) = {
    val res = countWays(n)
    println(s"$n -> $res")
  }

  private def count(denominations: List[Int], amount: Int): Int = {
    //        println(s"$amount\t\t$denominations")
    if (amount == 0)
      1 // successful recursion bottom: count this change
    else
      denominations match {
        case highest :: smaller =>
          val smallerCoinChangeCount = if (smaller.nonEmpty) count(smaller, amount) else 0
          if (amount >= highest)
            count(denominations, amount - highest) + smallerCoinChangeCount
          else
            smallerCoinChangeCount
      }
  }

  test(20)

  (1 to 30).foreach(amount => test(amount))
  test(3000)
}
