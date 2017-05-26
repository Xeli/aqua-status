package eu.xeli.aquastatus

import actors._
import routes._
import eu.xeli.aquastatus.cassandra.{ServerInfo, Helpers, Pillar}

import akka.actor.ActorSystem
import akka.actor.Props

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._

import akka.stream.ActorMaterializer

object Main extends App {
  println("Starting...")

  val uri = ServerInfo("cassandra://localhost:9042/aquaStatus")
  implicit val cassandraContext = Helpers.createSessionAndInitKeyspace()
  Pillar.runMigrations(uri, 1)

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  val http = Http()

  val metricsActorRef = system.actorOf(Props(new MetricsActor()), name = "metricsactor")

  val metricsRoute = new MetricsRoute(metricsActorRef)

  val routes = metricsRoute.getRoute

  val bindingFuture = http.bindAndHandle(routes, "localhost", 8080)
}
