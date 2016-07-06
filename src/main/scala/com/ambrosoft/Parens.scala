package com.ambrosoft

/**
  * Created by jacek on 7/4/16.
  *
  * 1 -> ()
  * 2 -> ()(), (())
  *
  *
  */
object Parens extends App {


  def parens(n: Int): Seq[List[Char]] = {
    n match {
      case 0 => Seq()
      case 1 => Seq(List('(', ')'))
      case 2 => Seq(List('(', ')', '(', ')'), List('(', '(', ')', ')'))
      case _ => parens(n - 1).flatMap(addPair)

    }
  }

  def addPair(patterns: List[Char]): Seq[List[Char]] = {
     Seq()
  }

}
