package eu.xeli.aquastatus.types

import java.util.UUID
import io.getquill._
import scala.concurrent.ExecutionContext
import ExecutionContext.Implicits.global

case class Metrics(userId: UUID,
                   timeAdded: Long,
                   waterLevel: Double,
                   temperature: Double,
                   pH: Double){

  def insertInDB(implicit cassandraContext: CassandraAsyncContext[Literal]) {
      import cassandraContext._
      cassandraContext.run(query[Metrics].insert(lift(this)))
  }
}
