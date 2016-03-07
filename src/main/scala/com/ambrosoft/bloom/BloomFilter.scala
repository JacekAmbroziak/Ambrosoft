package com.ambrosoft.bloom

import java.nio.ByteBuffer
import java.security.MessageDigest

import scala.collection.mutable
import scala.collection.mutable.BitSet
import scala.io.Source

/**
  * Created by jacek on 3/4/16.
  */


/** Simple implementation of a mutable Bloom Filter as specified in
  * http://codekata.com/kata/kata05-bloom-filters/
  *
  * Strings can be added to the set but not removed!
  *
  * Scala allows for an extremely concise expression
  * at the cost of some (small) runtime inefficiencies
  *
  * @param  setSize       size of the filter's storage in bits
  * @param  multiHashFuns sequence of String hashing functions returning Seqs of integer hash values
  */

class BloomFilter(setSize: Int, multiHashFuns: Seq[String => Seq[Int]]) {
  private val bitSet = new mutable.BitSet(setSize)

  // apply hash functions to input word
  private def normalizedHashes(word: String) =
    multiHashFuns.flatMap(_ (word)).map(_.abs % setSize)

  /**
    * add the argument String to the set represented by this filter
    *
    * @param word the String to add.
    */
  def add(word: String) {
    normalizedHashes(word).foreach(bitSet.add)
  }

  /**
    * check if the argument word belongs to the set of seen words
    *
    * @param word the String to test for membership.
    * @return false iff the argument DOES NOT belong to the set
    *         true result might be a false positive
    */
  def contains(word: String): Boolean =
    normalizedHashes(word).forall(bitSet.contains)

  /**
    * add all String to the set represented by this filter
    *
    * @param words Iterator of Strings to add.
    */
  def addAll(words: Iterator[String]) {
    words.foreach(add)
  }

  /**
    * empty the set
    */
  def clear() {
    bitSet.clear()
  }
}

// test a filter
object BloomFilter extends App {

  // MD5 value: array of 16 bytes (128 bits)
  private def md5(word: String): Array[Byte] =
    MessageDigest.getInstance("MD5").digest(word.getBytes)

  // from 16-byte array extract 4 integers into a list
  private def toInts(array: Array[Byte]) = {
    val buffer = ByteBuffer.wrap(array)
    List(buffer.getInt, buffer.getInt, buffer.getInt, buffer.getInt)
  }


  private val filterSize = 1024 * 1024 * 8

  // create a filter to use the supplied hashing functions
  val filter = new BloomFilter(filterSize, Seq(
    (word: String) => toInts(md5(word)),
    (word: String) => List(word.hashCode)
  ))

  private val wordlistURL = getClass.getResource("/wordlist.txt")

  filter.addAll(Source.fromURL(wordlistURL).getLines())

  def containsTest(word: String) {
    println(s"\'$word\' is member? ${filter.contains(word)}")
  }

  containsTest("hurry")
  containsTest("atom")
  containsTest("atox") // shouldn't be there
  containsTest("atomm") // shouldn't be there

  println("test all")
  println(Source.fromURL(wordlistURL).getLines().forall(filter.contains))
}