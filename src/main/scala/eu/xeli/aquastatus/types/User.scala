package eu.xeli.aquastatus.types

import java.util.UUID
import io.getquill._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

case class User(userId: UUID, name: String, apiKey: String) {
}

object User {
  def findByUsername(name: String)(implicit cassandraContext: CassandraAsyncContext[Literal]): Future[List[User]] = {
      import cassandraContext._

      val q = quote {
        query[User].filter(p => p.name == lift(name))
      }
      val result = cassandraContext.run(q)
      result
  }
}
