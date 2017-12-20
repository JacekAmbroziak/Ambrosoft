package com.ambrosoft.conference

import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}

/* An implementation of a strategy where Items to be packed are ordered from largest to smallest
  * and are added to the first container that can still hold them.
  * This strategy happens to work OK with the example input.
  *
  *  This algorithm encapsulation as a Solver illustrates the fact that it is a general algorithm
  *  that knows nothing about the Conference domain
  * */
object GreedySolver extends Solver {

  private class SimpleContainer(val index: Int, val maxCapacity: Int, var usedCapacity: Int = 0) extends Container {
    def addIfPossible(item: Item): Boolean = {
      val withAdded = usedCapacity + item.size
      if (withAdded <= maxCapacity) {
        usedCapacity = withAdded
        true
      } else
        false
    }
  }

  override def solve(items: IndexedSeq[Item], inputContainers: IndexedSeq[Container]): Try[Seq[Assignment]] = {
    val mutableContainers = inputContainers.zipWithIndex.map {
      case (cont, idx) => new SimpleContainer(idx, cont.maxCapacity)  // store container index from input sequence
    }

    val result = ArrayBuffer.empty[Assignment]
    items.sorted.reverse.foreach { item =>
      val containerIdx = mutableContainers.indexWhere(_.addIfPossible(item))  // find first fit
      if (containerIdx >= 0) {
        val itemIdx = items.indexOf(item) // find original index of the item
        result += Assignment(itemIdx, containerIdx)
      }
    }
    if (result.size == items.size)
      Success(result.toVector)
    else
      Failure(new Exception("GreedySolver failed to assign all items"))
  }
}
