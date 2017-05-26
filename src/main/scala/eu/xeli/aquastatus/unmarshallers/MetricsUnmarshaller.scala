package eu.xeli.aquastatus.unmarshallers

import eu.xeli.aquastatus.types.{Metrics, LightChannel}
import eu.xeli.aquariumPI.avro.{Metrics => AvroMetrics, Light => AvroLightChannel}

import akka.http.scaladsl.unmarshalling._
import akka.http.scaladsl.model.HttpRequest
import akka.stream.scaladsl.StreamConverters
import akka.stream.Materializer

import java.util.concurrent.TimeUnit
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Future, ExecutionContext}
import java.util.UUID

import org.apache.avro.io.{DecoderFactory, BinaryDecoder}
import org.apache.avro.specific.SpecificDatumReader
import org.apache.avro.specific.SpecificRecord

object MetricsUnmarshaller {
  val avroUnmarshaller: FromRequestUnmarshaller[Metrics] =
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

  private def convertMetrics(avro: AvroMetrics): Metrics = {
    val lights = avro.light.map(avro => LightChannel(avro.name, avro.value))
    Metrics(UUID.randomUUID, avro.time,
                             avro.waterLevel,
                             avro.temperature,
                             avro.pH
    )
  }
}
