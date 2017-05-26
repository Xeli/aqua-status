package eu.xeli.aquastatus.cassandra

import java.net.URI

case class ServerInfo(connectionString: String) {
  private val uri = new URI(connectionString)

  private val additionalHosts:Seq[String] = Option(uri.getQuery) match {
    case Some(query) => query.split('&')
      .map(_.split('='))
      .filter(param => param(0) == "host")
      .map(_(1))
      .toSeq
    case None => Seq.empty
  }

  val host = uri.getHost
  val hosts = uri.getHost +: additionalHosts
  val port = uri.getPort
  val keyspace = uri.getPath.substring(1)
}
