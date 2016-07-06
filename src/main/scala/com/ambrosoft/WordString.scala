package com.ambrosoft

/**
  * Created by jacek on 7/2/16.
  */
object WordString extends App {


  /*

    recursion bottom cases

    -- input is a single known word

    "he" -> { {"he"} }

    -- input is unsolvable

    "eh" -> { }

    case "the"

    "the" + "" => append "the"


   */


  def solutions(input: String, dict: String => Boolean): Seq[List[String]] =
    if (input.isEmpty)
      Seq(List())
    else
      (1 to input.length) // better only up to longest word
        .map(input.substring(0, _)) // cut off the first word candidate
        .filter(dict) // is it a known word?
        .flatMap {
        // append the first word to all solutions for string after first word
        word => solutions(input.substring(word.length), dict).map(word :: _)
      }

  var result = solutions("alamakota", Set("ala", "ma", "kota", "mak", "ota", "o", "ta"))

  println(result)
  println(result.size)

}
