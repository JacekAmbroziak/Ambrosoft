package com.ambrosoft

/**
  * Created by jacek on 7/7/16.
  */
object Multiplication extends App {

  def multiply(a: Int, b: Int): Int =
    if (a == 0 || b == 0) 0
    else mult(a, b, 0)

  def mult(a: Int, b: Int, acc: Int): Int =
    if (b == 0)
      acc
    else if ((b & 0x01) != 0)
      mult(a << 1, b >> 1, acc + a)
    else
      mult(a << 1, b >> 1, acc)

  def test(a: Int, b: Int) = {
    val res = multiply(a, b)
    println(s"$a * $b = $res")
  }

  test(3, 5)
  test(4, 4)
  test(10, 20)
  test(0, 10)
  test(10, 0)
  test(1, 1)
  test(1, 123)
  test(123, 1)
}
