package com.ambrosoft.spelling

import scala.io.Source
import scala.util.matching.Regex

object Spelling {
  lazy val NWORDS: Map[String, Int] = train(Source.fromURL(getClass.getResource("/big.txt")))

  val alphabet = "abcdefghijklmnopqrstuvwxyz"

  val wordPattern: Regex = "\\w+".r

  def words(text: String) = wordPattern.findAllIn(text.toLowerCase)

  def train(features: Seq[String]) = {
    val m = collection.mutable.Map[String, Int]().withDefaultValue(0)
    features.foreach(m(_) += 1) // count word occurrences
    m.toMap // immutable
  }

  def train(source: Source) = {
    val m = collection.mutable.Map[String, Int]().withDefaultValue(0)
    source.getLines().foreach {
      words(_).foreach {
        m(_) += 1
      }
    }
    m.toMap // immutable
  }

  def edits1(word: String) = {
    val splits = 0.to(word.length).map(word.splitAt)
    val deletes = splits.collect {
      case (a, b) if b.length > 0 => a + b.drop(1)
    }
    val transposes = splits.collect {
      case (a, b) if b.length > 1 => a + b(1) + b(0) + b.drop(2)
    }
    val replaces = for {
      (a, b) <- splits
      if b.length > 0
      c <- alphabet
    } yield a + c + b.drop(1)
    val inserts = for {(a, b) <- splits; c <- alphabet} yield a + c + b
    deletes.toSet ++ transposes ++ replaces ++ inserts
  }

  def known_word(word: String) = NWORDS.contains(word)

  def known(words: Set[String]) = words.filter(known_word)

  def known_edits2(word: String) =
    for {
      e1 <- edits1(word)
      e2 <- edits1(e1)
      if NWORDS.contains(e2)
    } yield e2

  def best(candidates: Set[String]) =
    if (candidates.isEmpty)
      None
    else
      Some(candidates.reduceLeft((l, r) => if (NWORDS(l) > NWORDS(r)) l else r))

  def correct(word: String) =
    if (known_word(word))
      word
    else
      best(known(edits1(word))).getOrElse {
        best(known_edits2(word)).getOrElse {
          word
        }
      }
}
