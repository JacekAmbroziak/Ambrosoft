name := "InterestingAlgorithms"

version := "1.0"

scalaVersion := "2.12.6"

target := file("/tmp/sbt") / name.value

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.7"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"
libraryDependencies += "junit" % "junit" % "4.12"
libraryDependencies += "joda-time" % "joda-time" % "2.9.9"
libraryDependencies += "com.google.guava" % "guava" % "25.1-jre"
