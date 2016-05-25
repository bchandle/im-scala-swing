name := "im-scala-swing"

description := "InteractiveMesh Scala Swing components"

organizationName := "InteractiveMesh"

organizationHomepage := Some(url("http://interactivemesh.org/testspace/j3dmeetsscala.html"))

version := "1.0"

organization := "io.github.bchandle"

scalaVersion := "2.11.7"

crossScalaVersions := Seq(scalaVersion.value, "2.10.6")

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on needed scala modules
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-swing" % "1.0.2")
    case _ =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang" % "scala-swing" % scalaVersion.value)
  }
}

licenses += ("BSD New", url("https://opensource.org/licenses/BSD-3-Clause"))

bintrayRepository := "maven"

