package eu.xeli.aquastatus.routes

import eu.xeli.aquastatus.types.{Metrics, User}
import eu.xeli.aquastatus.unmarshallers.MetricsUnmarshaller
import eu.xeli.aquastatus.Authenticators

import akka.actor.{ActorSystem, ActorRef}
import akka.stream.ActorMaterializer

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._

import io.getquill._

class MetricsRoute(actor: ActorRef)(implicit system: ActorSystem,
                                             materializer: ActorMaterializer,
                                             cassandraContext: CassandraAsyncContext[Literal]) {
  import system.dispatcher

  implicit val unmarshaller = MetricsUnmarshaller.avroUnmarshaller

  def getRoute() = {
    path("metrics") {
      put {
        authenticateBasicAsync("aquastatus", Authenticators.apiAuthenticator) { user: User =>
          entity(as[Metrics]) { metrics: Metrics =>
            val newMetrics = metrics.copy(userId = user.userId)
            actor ! newMetrics
            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>hi!</h1>"))
          }
        }
      }
    }
  }
}
