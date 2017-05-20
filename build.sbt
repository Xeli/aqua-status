import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "eu.xeli",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "AquaStatus",
    libraryDependencies += scalaTest % Test
  )

mainClass in assembly := Some("eu.xeli.Main")
