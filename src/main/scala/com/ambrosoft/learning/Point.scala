package com.ambrosoft.learning

/**
  * Created by jacek on 3/12/17.
  */
case class Point(x: Int, y: Int) {
  lazy val dist: Double = euclideanDist(x, y)

  def dist(p: Point): Double = euclideanDist(p.x - x, p.y - y)
}

object Zero extends Point(0, 0)

object PointMain extends App {
  println(Zero)
  println(Zero.x)
  println(Point(3, 4))
}