package com.ambrosoft.races

import scala.util.Random

/*
     “Out of 25 horses, pick the fastest 3 horses.
     In each race, only 5 horses can run at the same time.
     What is the minimum number of races required?”
 */

/*
Discussion.

Screw the horses... The problem is more interesting than horse races.
I like to think about it as computing some global property of a data set
with the constraint, such as limited memory, allowing only partial comparisons.
Which 3 "horses" are fastest is such a global property.
Because of the constraint, we cannot just sort them all and take the winning 3.
Comparing 5 at a time also allows for parallelism; the initial 5 races can be run in parallel.

Some people were suggesting just measuring "times" during the initial races and picking 3 best.
This is, of course, not allowed and might not be possible.
Our only measurement is direct comparison of horses w/i a race.
It is assumed however that the horses compare consistently: if A > B & B > C then A > C

The solution below is not generalized to N horses & M horses per race
Rather, after "compilation by hand" on paper, it is encoded in Scala

The analysis leading to finding the 3 best horses w/ minimal computation
tries to quickly eliminate horses that can be inferred to not have a chance to displace current favorites
For example 2 last horses in any race can always be dropped
 */

class HorseRaces[T](implicit ordering: Ordering[T]) {

  case class Race(seq: Seq[T]) {
    lazy val sorted = seq.sorted

    lazy val results = sorted.take(3)
  }

  def select3best(all: Seq[T]) = {
    // divide data into groups of 5
    val groups = all.grouped(5).map(new Race(_)).toVector
    // run 5 races, perhaps in parallel
    val phase1 = groups.map(_.results)
    // 10 horses eliminated, 15 still competing
    val bestInGroups = phase1.map(_.head)
    val groupsByBestHorse = bestInGroups.zip(groups).toMap
    // to quickly find #1 horse, race 5 winners of initial races
    val phase2 = Race(bestInGroups).results
    val firstHorse = phase2(0)
    // now that #1 is found, we still need to find #2 & #3
    // from 15 still in competition after phase1 we eliminate #1 and 2 last in phase2
    // that leaves 12 in the first approximation
    // but here just reasoning will help eliminate 7
    // we order original groups by results of their winners
    val origGroupOfFirst = groupsByBestHorse(firstHorse)
    val origGroupOfSecond = groupsByBestHorse(phase2(1))
    // and it turns out only these cherry picked horses can produce #2 and #3
    val finalGroup = Race(Vector(phase2(1), phase2(2), origGroupOfFirst.sorted(1), origGroupOfFirst.sorted(2), origGroupOfSecond.sorted(1)))

    val phase3 = finalGroup.results

    val secondHorse = phase3(0)
    val thirdHorse = phase3(1)

    (firstHorse, secondHorse, thirdHorse)
  }

}

object RacesTest extends App {
  val races = new HorseRaces[Int]
  val horses = Random.shuffle(1 to 25).toVector
  val phase1 = races.select3best(horses)

  println(horses.grouped(5).toList)
  println(phase1)
}