package com.ambrosoft

/**
  * Created by jacek on 7/7/16.
  *
  * need to be able to formulate recursive goals with towers
  *
  * N disks from source to target
  * solve problem of moving N-1 top disks from SOURCE to AUX
  */

object TowersOfHanoi extends App {

  def move(disksToMove: Int, source: String, aux: String, target: String): Unit = {
    if (disksToMove == 1) {
      println(s"$source -> $target")
    } else {
      move(disksToMove - 1, source, target, aux)
      move(1, source, aux, target)
      move(disksToMove - 1, aux, source, target)
    }
  }

  move(4, "A", "B", "C")


  def move2[T](source: List[T], aux: List[T], target: List[T]): Unit = {
    println(s"$source $target")
    if (source.length > 1) {
      val subtask = source.init
      move2(subtask, target, aux)

      move2(aux, source, source.last :: target)
    } else {
      move2(Nil, aux, source)
    }
  }

  //  move2(List(1, 2, 3), Nil, Nil)
}
