package com.ambrosoft.conference

import scala.util.Try

/*
  This is the interface of bin packing algorithms to be plugged in (as in STRATEGY pattern)

  An algorithm will be given an indexed sequence of Items to be assigned to Containers
 */

trait Container {
  def maxCapacity: Int
}

trait Item extends Ordered[Item] {
  def size: Int
}

/* Solver generated assignment of an item to a container, both referred to by their respective indexes in input sequences */
case class Assignment(itemIdx: Int, containerIdx: Int)

/* General interface to any and all packing algorithms decoupled from domain of application
 * Assignments will use indexes to refer to items and containers */
trait Solver {
  def solve(items: IndexedSeq[Item], containers: IndexedSeq[Container]): Try[Seq[Assignment]]
}
