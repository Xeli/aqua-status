package eu.xeli.aquastatus.cassandra

import eu.xeli.aquastatus.utils.JarUtils

import com.chrisomeara.pillar.core._
import com.datastax.driver.core._

object Pillar {

  private val registry = Registry(loadMigrationsFromJarOrFilesystem())
  private val migrator = Migrator(registry)

  private def loadMigrationsFromJarOrFilesystem() = {
    val migrationsDir = "migrations/"
    val migrationNames = JarUtils.getResourceListing(getClass, migrationsDir).toList.filter(_.nonEmpty)
    val parser = Parser()

    migrationNames.map(name => getClass.getClassLoader.getResourceAsStream(migrationsDir + name)).map {
      stream =>
        try {
          parser.parse(stream)
        } finally {
          stream.close()
        }
    }.toList
  }

  def runMigrations(uri: ServerInfo, replicationFactor: Int): Unit = {
    val cluster = new Cluster.Builder().
      addContactPoints(uri.hosts.toArray: _*).
      withPort(uri.port).
      withQueryOptions(new QueryOptions().setConsistencyLevel(QueryOptions.DEFAULT_CONSISTENCY_LEVEL)).build

    val session = cluster.connect
    session.execute(s"USE ${uri.keyspace}")

    migrator.initialize(
      session,
      uri.keyspace,
      new ReplicationOptions(Map("class" -> "SimpleStrategy", "replication_factor" -> replicationFactor))
    )

    migrator.migrate(session)
  }
}
