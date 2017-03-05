package com.ambrosoft.exercises

/**
  * Created by jacek on 2/26/17.
  */
object Int2String extends App {
  def digit(n: Int): Char = (if (n < 10) '0' + n else 'a' + (n - 10)).toChar

  def int2String(n: Int, base: Int): String =
    if (n < base) digit(n).toString
    else int2String(n / base, base) + digit(n % base)

  def test(n: Int, base: Int): Unit = {
    val result = int2String(n, base)
    println(s"$n base $base -> $result")
  }

  test(101, 10)
  test(101, 2)
  test(101, 16)
  test(101, 8)
}
