package com.ambrosoft.learning

import org.scalatest.FlatSpec

/**
  * Created by jacek on 3/12/17.
  */
class TestPoint extends FlatSpec {
  "origin" should "be at zero" in {
    assert(Zero.dist === 0.0)
  }

  "identical points" should "not be apart" in {
    val x = util.Random.nextInt(1000)
    val y = util.Random.nextInt(1000)
    assert(Point(x, y).dist(Point(x, y)) === 0.0)
  }

  "dist" should "be distance from Zero" in {
    val x = util.Random.nextInt(1000)
    val y = util.Random.nextInt(1000)
    assert(Point(x, y).dist === Point(x, y).dist(Zero))
    assert(Point(x, y).dist === Zero.dist(Point(x, y)))
  }

}
