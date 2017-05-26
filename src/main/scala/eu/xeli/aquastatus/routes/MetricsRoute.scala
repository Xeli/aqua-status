package eu.xeli.aquastatus.routes

import eu.xeli.aquastatus.actors.MetricsActor
import eu.xeli.aquastatus.types.Metrics

import eu.xeli.aquariumPI.avro.{Metrics => AvroMetrics, Light => AvroLightChannel}
import eu.xeli.aquastatus.types.{Metrics, LightChannel}

import scala.concurrent.duration.{FiniteDuration}
import scala.concurrent.{Future, ExecutionContext}
import java.util.concurrent.TimeUnit
import java.util.UUID

import akka.actor.{ActorSystem, ActorRef}
import akka.stream.ActorMaterializer
import akka.stream.Materializer
import akka.stream.scaladsl.StreamConverters

import akka.http.scaladsl.HttpExt
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling._

import org.apache.avro.io.{DecoderFactory, BinaryDecoder}
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificRecord

class MetricsRoute(actor: ActorRef)(implicit val system: ActorSystem,
                                             val materializer: ActorMaterializer) {
  import system.dispatcher

  def getRoute() = {
    println("router")
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

  implicit val avroUnmarshaller: FromRequestUnmarshaller[Metrics] =
    Unmarshaller.withMaterializer {
      implicit ex: ExecutionContext =>
      implicit mat: Materializer =>
        request: HttpRequest => {
          val inputStream = request.entity.dataBytes.runWith(
            StreamConverters.asInputStream(FiniteDuration(3, TimeUnit.SECONDS))
          )

          val avroMetrics:AvroMetrics = AvroMetrics(0, 0, 0, 0, List())
          val reader = new SpecificDatumReader[AvroMetrics](avroMetrics.getSchema)
          val decoder:BinaryDecoder = DecoderFactory.get().binaryDecoder(inputStream, null)
          reader.read(avroMetrics, decoder)

          Future {
            convertMetrics(avroMetrics)
          }
        }
  }

  def convertLightChannel(avro: AvroLightChannel): LightChannel = {
    LightChannel(avro.name, avro.value)
  }

  def convertMetrics(avro: AvroMetrics): Metrics = {
    val lights = avro.light.map(convertLightChannel)
    Metrics(UUID.randomUUID, avro.time,
                             avro.waterLevel,
                             avro.temperature,
                             avro.pH
    )
  }
}
