package com.ambrosoft.conference

import scala.util.{Success, Try}

/* Only used during debugging */
object DummySolver extends Solver {
  override def solve(items: IndexedSeq[Item], containers: IndexedSeq[Container]): Try[Seq[Assignment]] = {
    Success(List(Assignment(0, 0), Assignment(1, 1), Assignment(2, 2), Assignment(3, 3)))
  }
}
