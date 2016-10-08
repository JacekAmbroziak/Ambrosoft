package com.cyft

import scala.io.Source


/**
  * Created by jacek on 8/24/16.
  *
  * The elevator system is a linear sequence of states organized by time
  * Each state is characterized by mapping of elevators to their current floor
  * The actual implementation of a state can use tuples, maps, etc.
  *
  * A solution to the problem of starting on an Elevator and finishing on a given floor at given time
  * is a sequence of elevator selections
  * In general, we look for a set of such solutions, and that set can be empty.
  *
  *
  * First task would be to read problem statement. There can be any number of errors in input data:
  * bad formatting, elevators duplicated, missing, etc.
  * Assuming elevators.txt file is valid, it is still possible for the solution set to be empty
  * if eg. requested target time is longer than what the elevator system describes.
  *
  * To solve the problem one can start at the target time & floor
  * and move back in time, exploring admissible switches
  * to see if one can find a path to starting elevator at time T0.
  *
  * This looks like a search in a tree where depth in the tree
  * corresponds to time counted backwards from target time
  *
  *
  *
  *
  */


object Elevators extends App {

  case class ElevatorState(name: String, time: Int, floor: Int)

  // snapshot in time: elevator positions using 2 mappings
  // 1) mapping elevator to its current floor
  // 2) mapping floor to list of elevators currently on this floor
  case class Snapshot(whichFloor: Map[String, Int], elevatorsAtFloor: Map[Int, List[String]])

  // a position representing being on a floor at ceratin time
  case class Goal(time: Int, floor: Int)

  // an action of taking a named elevator from one position to another
  case class Ride(start: Goal, elevator: String, target: Goal)

  // list of elevators to take in subsequent time units
  case class Solution(path: List[String])

  def readElevatorStates(inputLines: Iterator[String]): Vector[Map[Int, List[ElevatorState]]] =
    readStateInTime(inputLines)
      .map(eStates => eStates.groupBy(_.floor)).toVector


  // main function to search for a solution
  // the heavy lifting to be performed by helper functions inside
  def search(snapshots: Vector[Snapshot], initial: Goal, goal: Goal): Option[Solution] = {

    // for a given goal find all Rides that support that Goal, i.e. terminate at Goal
    // each such Ride will introduce a prerequisite goal from preceding time slice
    def findRides(goal: Goal): List[Ride] =
    if (goal.time == 0)
      Nil
    else {
      val prevTime = goal.time - 1
      val elevators = snapshots(goal.time).elevatorsAtFloor.get(goal.floor)

      elevators match {
        case Some(list) =>
          val prevState = snapshots(prevTime)
          list.map(elevator => Ride(Goal(prevTime, prevState.whichFloor.getOrElse(elevator, -1)), elevator, goal))

        case None => Nil
      }
    }

    def unfoldPath(goal: Goal, supportedRides: Map[Goal, Ride]): List[String] =
      supportedRides.get(goal) match {
        case Some(ride) => ride.elevator :: unfoldPath(ride.target, supportedRides)
        case None => Nil
      }

    // 'path'
    def searchAux(goals: List[Goal], supportedRides: Map[Goal, Ride]): Option[Solution] = {
      // find Rides supporting current set of goals
      val allRides = goals.flatMap(findRides)

      if (allRides.nonEmpty) {
        val newSupportedRides = allRides.foldLeft(supportedRides) { (p, r) => p.updated(r.start, r) }
        val newGoals = allRides.map(_.start).distinct

        if (newGoals.contains(initial)) // found a path from target to initial state
          Some(Solution(unfoldPath(initial, newSupportedRides)))
        else
          searchAux(newGoals, newSupportedRides)
      }
      else
        None // stuck: run out of rides
    }

    // call the auxiliary method
    searchAux(List(goal), Map())
  }

  // returns a list of ElevatorStates grouped by time
  def readStateInTime(inputLines: Iterator[String]): List[List[ElevatorState]] = {
    val list = inputLines.map(line => readFloorLine(line)).toList
    val states = buildStates(list, Nil, Nil)
    states.zipWithIndex.map {
      case (state, time) =>
        state.zipWithIndex.flatMap {
          case (elevators, floor) => elevators.map((name: String) => ElevatorState(name, time, floor + 1))
        }
    }
  }

  private def buildStates(data: List[Option[List[String]]], state: List[List[String]], states: List[List[List[String]]]): List[List[List[String]]] =
    data match {
      case Nil => states.reverse
      case Some(seq) :: rest => buildStates(rest, seq :: state, states)
      case None :: rest => buildStates(rest, Nil, state :: states)
    }


  // if non-empty, return Some list of elevators found
  def readFloorLine(line: String): Option[List[String]] =
  if (line.isEmpty)
    None
  else
    Some(line.filter(_.isUpper).map(_.toString).toList)


  def readTimeStates(lines: Iterator[String]) = {
    readElevatorStates(lines).map { arg =>
      val map2 = arg.mapValues(es => es.map(_.name))
      val map1 = map2.toList.flatMap {
        case (floor, elevators) => elevators.map((_, floor))
      }.toMap

      Snapshot(map1, map2)
    }
  }


  def solve(fname: String, initial: Goal, goal: Goal): Option[Solution] = {
    val elevatorSystem = getClass.getResource(fname)
    val lines = Source.fromURL(elevatorSystem).getLines()
    val timeStates = readTimeStates(lines)
    val solution = search(timeStates, initial, goal)
    solution
  }


  println(solve("/elevators.txt", Goal(0, 1), Goal(5, 3)))

  if (args.length > 0) {
    println(args)
  }
}