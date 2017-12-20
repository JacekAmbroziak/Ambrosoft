package com.ambrosoft.conference

import scala.collection.mutable.ArrayBuffer

/* Talk description coming from input, not yet scheduled */
case class Talk(title: String, duration: Int)

/* Tracks have 2 sessions and are initialized with common shared events */
case class ConferenceTrack(title: String,
                           s1: MorningSession = MorningSession(),
                           lunch: Lunch,
                           s2: AfternoonSession = AfternoonSession(),
                           networkingEvent: NetworkingEvent) {

  def sessions: Seq[Session] = Vector(s1, s2)

  def allTalksEndTime: Int = if (s2.isEmpty) s1.talksEndTime else s2.talksEndTime

  override def toString: String = {
    title + ":\n" + s1 + "\n\n" + lunch + "\n\n" + s2 + "\n\n" + networkingEvent
  }
}

/* Main object initialized with talks to be scheduled into a set number of tracks */
case class ConferenceProgram(talksToSchedule: Seq[Talk], tracks: Seq[ConferenceTrack]) {
  def scheduleNetworkingEvent(): Unit = {
    val maxTrackEndTime = tracks.map(_.allTalksEndTime).max
    if (maxTrackEndTime > hoursToMinutes(16)) {
      tracks.head.networkingEvent.setStartHour(17)
    }
  }

  override def toString: String = tracks.map(_.toString).mkString("\n\n")
}

/* companion object */
object ConferenceProgram {
  // common events to be shared among all tracks
  val lunch = new Lunch
  val networkingEvent = new NetworkingEvent(4)

  def createEmptyProgram(talks: Seq[Talk], nTracks: Int): ConferenceProgram = {
    ConferenceProgram(talks,
      (1 to nTracks).map(trackNo => ConferenceTrack(s"Track $trackNo", lunch = lunch, networkingEvent = networkingEvent)))
  }
}

/* A general interface to several objects seen as intervals in time */
trait Event {
  def startTime: Int

  def duration: Int

  def after: Int = startTime + duration
}

/* Lunch to be shared between Tracks */
class Lunch extends Event {
  override def startTime: Int = hoursToMinutes(12)

  override def duration = 60

  override def toString: String = s"${minutesToTimeString(startTime)}: Lunch"
}

/* Networking Event to be shared between tracks */
class NetworkingEvent(var startHour: Int) extends Event {
  def setStartHour(hour: Int): Unit = {
    startHour = hour
  }

  override def startTime: Int = hoursToMinutes(startHour)

  // example rule: networking can be longer if starts earlier
  override def duration: Int = if (startHour == 16) hoursToMinutes(2) else hoursToMinutes(1)

  override def toString: String = s"${minutesToTimeString(startTime)}: Networking Event"
}

/* Abstract session to be extended into Morning- and Afternoon session types w/ possibly different behaviors */
abstract class Session(talks: ArrayBuffer[ScheduledTalk]) extends Event {
  def maxDuration: Int // depends on concrete session type

  def timeRemaining: Int = startTime + maxDuration - availableNextTalkStartTime

  def availableNextTalkStartTime: Int =
    if (talks.isEmpty)
      startTime
    else
      talks.last.after

  def extendSchedule(talk: Talk): Unit =
    talks += ScheduledTalk(talk, availableNextTalkStartTime, this)

  // assuming talks packed w/o breaks
  def talksDuration: Int = talks.map(_.duration).sum

  def talksEndTime: Int = startTime + talksDuration

  def isEmpty: Boolean = talks.isEmpty

  protected def toString(title: String): String = {
    val sb = new StringBuilder(title)
    talks.foreach(talk => sb.append(talk.toString).append('\n'))
    sb.toString()
  }
}

/* A separate type of a Session (may have different behaviors than Afternoon session)

Ideally I'd prefer this object to be immutable */

case class MorningSession(talks: ArrayBuffer[ScheduledTalk] = ArrayBuffer.empty) extends Session(talks) {
  override def startTime: Int = hoursToMinutes(9)

  override def maxDuration: Int = hoursToMinutes(3)

  override def duration: Int = maxDuration

  override def toString: String = toString("Morning Session\n")
}

case class AfternoonSession(talks: ArrayBuffer[ScheduledTalk] = ArrayBuffer.empty) extends Session(talks) {
  override def startTime: Int = hoursToMinutes(13)

  override def maxDuration: Int = hoursToMinutes(4)

  //TODO round to next hour?
  override def duration: Int = talksDuration

  override def toString: String = toString("Afternoon Session\n")
}

/* Representation of a Talk already assigned to a Session and given start time */
case class ScheduledTalk(talk: Talk, startTime: Int, session: Session) extends Event {
  override def duration: Int = talk.duration

  override def toString: String = s"${minutesToTimeString(startTime)}: ${talk.title} - ${talk.duration}min"
}
