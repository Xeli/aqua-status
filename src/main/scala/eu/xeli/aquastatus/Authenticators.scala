package eu.xeli.aquastatus

import eu.xeli.aquastatus.types.User

import java.util.UUID
import scala.util.{Success, Failure}
import scala.concurrent.duration._
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

import akka.http.scaladsl.server.directives.Credentials
import akka.http.scaladsl.server.directives.Credentials._
import io.getquill._

object Authenticators {
  def apiAuthenticator(credentials: Credentials)(implicit cassandraContext: CassandraAsyncContext[Literal]): Future[Option[User]] = credentials match {
    case p@Credentials.Provided(username) => Future {
      val userFuture = Await.result(User.findByUsername(username), Duration.Inf)
      userFuture match {
          case List() => None
          case List(user) => {
            if (p.verify(user.apiKey)) Some(user)
            else None
          }
      }
    }
    case Missing => Future.successful(None)
  }
}
