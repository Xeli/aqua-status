package eu.xeli.aquastatus.cassandra

import io.getquill._

object Helpers {

  def createSessionAndInitKeyspace(): CassandraAsyncContext[Literal] = {

    new CassandraAsyncContext[Literal]("ctx")
  }
}
