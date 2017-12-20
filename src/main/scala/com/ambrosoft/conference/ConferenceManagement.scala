package com.ambrosoft.conference


import scala.util.{Failure, Success, Try}

/*
    Analysis/brainstorming:

    Problem underspecified: days? preferences?

    ConferenceProgram can in general contain multiple Days
    Days contain Tracks
    Tracks contain 2 Sessions; Lunch, Networking Event are common
    Sessions contain Talks assigned to time slots
    Talk has title and length

    Specification: list of talks to be read from input

    How many days/tracks is unspecified, but we can add days or tracks to output

    Output is a Program with talks assigned (Assignment) to Sessions (exact ordering up to strategy)
    There can be problems with reading data or eg. talks longer than sessions or no talks etc.

    We would like to be able to plug in different packing algorithms, including additional ordering rules
    Separate concerns of domain modelling vs pluggable algorithms
    We may also plug in "program consumers" to test, rate, print Programs

    Which objects should be immutable?

    Session partially filled can be immutable
    Talks are immutable; can't be assigned to more than one session (test); all have to be assigned test
    Input is immutable
    Assignment can be an object pointing to Talk and to Session time slot
    Session: seq of Assignments

    It is possible to run several strategies and run evaluators and pick best
 */

object ConferenceManagement {
  val default_number_of_tracks = 2

  def createProgram(fileName: String, nTracks: Int): Try[ConferenceProgram] = {
    InputOutput.readTalkDescriptions(fileName).map(ConferenceProgram.createEmptyProgram(_, nTracks))
  }

  def scheduleTalks(program: ConferenceProgram): Unit = {
    val allTalks = Adapter.allTalks(program)
    val allSessions = Adapter.allSessions(program)
    GreedySolver2.solve(allTalks, allSessions) match {
      case Success(assignments) =>
        // apply the generated assignments, modifying sessions
        assignments.foreach {
          case Assignment(talkIdx, sessionIdx) => Adapter.scheduleTalk(allSessions(sessionIdx), allTalks(talkIdx))
        }
        program.scheduleNetworkingEvent()
        println(program)

      case Failure(reason) => println(reason)
    }
  }

  def scheduleAndPrintConferenceProgram(fileName: String, nTracks: Int): Unit = {
    createProgram(fileName, nTracks) match {
      case Success(program) => scheduleTalks(program.copy()) // schedule modifies a copy of the original program template
      case Failure(reason) => println(reason)
    }
  }

  def main(args: Array[String]): Unit = {
    scheduleAndPrintConferenceProgram("/Users/jacek/Downloads/conference_talks.txt", default_number_of_tracks)
  }
}
