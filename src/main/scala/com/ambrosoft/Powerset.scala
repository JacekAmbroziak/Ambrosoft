package com.ambrosoft

/**
  * Created by jacek on 7/6/16.
  */
object Powerset extends App {


  println(powerset(List(1, 2, 3)))

  val subsets = powerset(List(1, 2, 3, 4))

  val count = subsets.map(_.length).sum

  println(subsets.length)
  println(count)

  def powerset[T](set: List[T]): List[List[T]] =
    set match {
      case first :: rest => {
        val ps1 = powerset(rest)
        ps1.map(first :: _) ++ ps1
      }
      case Nil => List(List())
    }


}
