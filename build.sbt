import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "eu.xeli",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "AquaStatus",
    mainClass in (Compile, run) := Some("eu.xeli.aquastatus.Main"),
    sbtavrohugger.SbtAvrohugger.specificAvroSettings,
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "com.typesafe.akka" %% "akka-http" % "10.0.6",
      "org.apache.avro" % "avro" % "1.8.2",
      "com.chrisomeara" % "pillar-core_2.12" % "3.0.0",
      "io.getquill" %% "quill-cassandra" % "1.2.1",
      "ch.qos.logback" % "logback-classic" % "1.1.7",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0"
    )
  )
mainClass in assembly := Some("eu.xeli.aquastatus.Main")

pillarConfigFile in ThisBuild := file("src/main/resources/application.conf")
pillarMigrationsDir in ThisBuild := file("src/main/resources/migrations")
