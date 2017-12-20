package com.ambrosoft.conference

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}

/* An implementation of a strategy where Items to be packed are ordered from largest to smallest
  * and are added to the first container that can still hold them.
  * This strategy happens to work OK with the example input.
  *
  *  This algorithm encapsulation as a Solver illustrates the fact that it is a general algorithm
  *  that knows nothing about the Conference domain
  * */
object GreedySolver2 extends Solver {

  private class SimpleContainer(val index: Int, val maxCapacity: Int, var usedCapacity: Int = 0) extends Container with Ordered[SimpleContainer] {
    def addIfPossible(item: Item): Boolean = {
      val withAdded = usedCapacity + item.size
      if (withAdded <= maxCapacity) {
        usedCapacity = withAdded
        true
      } else
        false
    }

    def free: Int = maxCapacity - usedCapacity

    override def toString: String = s"[$maxCapacity, $usedCapacity]"

    override def compare(that: SimpleContainer): Int = free - that.free
  }

  override def solve(items: IndexedSeq[Item], inputContainers: IndexedSeq[Container]): Try[Seq[Assignment]] = {
    val mutableContainers = inputContainers.zipWithIndex.map {
      case (cont, idx) => new SimpleContainer(idx, cont.maxCapacity) // store container index from input sequence
    }
    val pq = new mutable.PriorityQueue[SimpleContainer]()

    mutableContainers.foreach(pq.enqueue(_))

    val result = ArrayBuffer.empty[Assignment]
    items.sorted.reverse.foreach { item =>
      println("item " + item.size)
      val smallestContainer = pq.dequeue()
      println("cont " + smallestContainer)
      if (smallestContainer.addIfPossible(item)) {
        val itemIdx = items.indexOf(item) // find original index of the item
        result += Assignment(itemIdx, smallestContainer.index)
        pq.enqueue(smallestContainer)
        println("added")
      } else {
        println("NOT added")

      }
    }
    if (result.size == items.size)
      Success(result.toVector)
    else
      Failure(new Exception("GreedySolver2 failed to assign all items"))
  }
}
