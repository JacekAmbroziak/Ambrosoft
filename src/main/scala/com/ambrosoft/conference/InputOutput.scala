package com.ambrosoft.conference

import scala.io.Source
import scala.util.{Failure, Success, Try}

/* Implementation of reading input Talk descriptions from text to data structures
 * Wrapped in Try as reading can fail on faulty data */
object InputOutput extends App {

  def readTalkDescriptions(fileName: String): Try[Seq[Talk]] =
    Try(Source.fromFile(fileName).getLines().map {
      readTalkDescription
    }.map(_.get).toList)

  private def readTalkDescription(line: String): Try[Talk] = {
    val minutesPattern = raw"(\d+)min".r

    // poor man's 'cleaning' of talk title information: some descriptions have dashes, some don't
    // in general, some cleaning/spellchecking will almost always be needed
    // or we can delegate cleaning to client of this code
    def makeTitle(reversedTokens: List[String]): String = reversedTokens match {
      case "-" :: rest => rest.reverse.mkString(" ")
      case _ => reversedTokens.reverse.mkString(" ")
    }

    def durationDescriptionToMinutes(desc: String): Try[Int] = desc match {
      case "lightning" => Success(5)
      case minutesPattern(n) => Success(n.toInt)
      case _ => Failure(new Exception("unknown talk duration format " + desc))
    }

    Option(line).map(_.trim).filterNot(_.isEmpty).map(_.split(" ")).filter(_.length > 1) match {
      case Some(tokens) =>
        tokens.toList.reverse match {
          case durationDesc :: reversedTitleTokens =>
            Try(Talk(makeTitle(reversedTitleTokens), durationDescriptionToMinutes(durationDesc).get))

          case Nil => Failure(new Exception("no talk descriptions found!"))
        }
      case None => Failure(new Exception("can't parse " + line))
    }
  }
}
