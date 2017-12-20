package com.ambrosoft.conference

/*
* This is the fundamental object to provide coupling between the "conference domain" and packing algorithms
* that operate on abstract items and containers (that could be applicable to other domains, eg. transport)
* ADAPTER or WRAPPER pattern
* */

object Adapter {

  /* Wrapper around Session exposing it is a Container */
  class SessionAdapter(val session: Session) extends Container {
    override def maxCapacity: Int = session.maxDuration

    def usedCapacity: Int = session.talksDuration

    override def toString: String = s"[$maxCapacity, $usedCapacity]"
  }

  /* Wrapper around Talk exposing it as an Item to be packed */
  class TalkAdapter(val talk: Talk) extends Item {
    override def size: Int = talk.duration

    override def toString: String = s"($size)"

    override def compare(that: Item): Int = size - that.size
  }

  /* We extract "Containers" and "Items" from the ConferenceProgram as indexed sequences
  so that assignments of items to containers (figured out by packing algorithms)
  can use these indexes to refer to wrappers and therefore to original domain objects (sessions and talks)
  * */

  def allTalks(program: ConferenceProgram): IndexedSeq[TalkAdapter] =
    program.talksToSchedule.map(new TalkAdapter(_)).toVector

  def allSessions(program: ConferenceProgram): IndexedSeq[SessionAdapter] =
    program.tracks.flatMap(_.sessions).map(new SessionAdapter(_)).toVector

  /* uses wrapped domain objects to add a Talk to a Session */
  def scheduleTalk(sessionAdapter: SessionAdapter, talkAdapter: TalkAdapter): Unit = {
    sessionAdapter.session.extendSchedule(talkAdapter.talk)
  }
}
