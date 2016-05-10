package org.radioactiveMongoTemplate.dao

import com.typesafe.config.ConfigFactory
import reactivemongo.api.commands._
import reactivemongo.api._
import scala.collection.JavaConversions._



object MongoContext {

  private val driver = new MongoDriver
  private val conf = ConfigFactory.load()

  val connectionOptions = MongoConnectionOptions(
    conf.getInt("connectTimeoutMS"),
    Option(conf.getString("authSource")),
    conf.getBoolean("sslEnabled"),
    conf.getBoolean("sslAllowsInvalidCert"),
    getAuthMethod,
    conf.getBoolean("tcpNoDelay"),
    conf.getBoolean("keepAlive"),
    conf.getInt("nbChannelsPerNode"),
    getWriteConcern,
    getReadPreference
  )

   val connection = driver.connection(nodes = getHosts, options =  connectionOptions)

   val maxBulkSize = getMaxBulkSize().getOrElse(1000)

   val maxBsonSize = getMaxBsonSize().getOrElse(6000000)

  private def getAuthMethod():AuthenticationMode={
    val auth:String = conf.getString("authMode")
    auth match {
      case "ScramSha1Authentication" => ScramSha1Authentication
      case _ => CrAuthentication
    }
  }

  private def getWriteConcern():GetLastError = {
    val writeConcern:String = conf.getString("writeConcern")
    writeConcern match {
      case "Acknowledged" => WriteConcern.Acknowledged
      case "Journaled" => WriteConcern.Journaled
      case "Unacknowledged" =>  WriteConcern.Unacknowledged
      case _ =>  WriteConcern.Default
    }
  }

  private def getReadPreference():ReadPreference={
    val readPreference:String = conf.getString("readPreference")
    readPreference match {
      case "primaryPreferred" => ReadPreference.primaryPreferred
      case "secondary" => ReadPreference.secondary
      case "secondaryPreferred" =>  ReadPreference.secondaryPreferred
      case "nearest" =>  ReadPreference.nearest
      case _ =>  ReadPreference.primary
    }
  }

  private def getMaxBulkSize():Option[Int] = {
    val maxBulkSize = conf.getInt("maxBulkSize")
    if(maxBulkSize == 0) None else Some(maxBulkSize)
  }

  private def getMaxBsonSize():Option[Int] = {
    val maxBsonSize = conf.getInt("maxBsonSize")
    if(maxBsonSize == 0) None else Some(maxBsonSize)
  }

  private def getHosts(): java.util.List[String] = {
    val l = conf.getStringList("hosts").toList
    if(l.isEmpty) List("localhost:27017") else l
  }
}
