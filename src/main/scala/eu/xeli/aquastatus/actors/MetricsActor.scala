package eu.xeli.aquastatus.actors

import eu.xeli.aquastatus.types.Metrics
import scala.concurrent.ExecutionContext
import akka.actor.Actor
import io.getquill._

class MetricsActor(implicit cassandraContext: CassandraAsyncContext[Literal]) extends Actor {

  def receive = {
    case metrics: Metrics => handleMetrics(metrics)
    case _ => println("he?")
  }

  def handleMetrics(metrics: Metrics) {
    metrics.insertInDB
  }
}
