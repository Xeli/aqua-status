package eu.xeli.aquastatus.routes

import eu.xeli.aquastatus.types.Metrics
import eu.xeli.aquastatus.unmarshallers.MetricsUnmarshaller

import akka.actor.{ActorSystem, ActorRef}
import akka.stream.ActorMaterializer

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

class MetricsRoute(actor: ActorRef)(implicit val system: ActorSystem,
                                             val materializer: ActorMaterializer) {
  import system.dispatcher

  implicit val unmarshaller = MetricsUnmarshaller.avroUnmarshaller

  def getRoute() = {
    path("metrics") {
      put {
        decodeRequest {
          entity(as[Metrics]) { metrics: Metrics =>
            println("got a metric")
            actor ! metrics
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>hi!</h1>"))
          }
        }
      }
    }
  }

}
