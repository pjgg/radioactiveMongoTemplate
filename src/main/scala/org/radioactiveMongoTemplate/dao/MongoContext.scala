package org.radioactiveMongoTemplate.dao

import reactivemongo.api.commands._
import reactivemongo.api.{ReadPreference, CrAuthentication, MongoConnectionOptions, MongoDriver}


object MongoContext {

  private val driver = new MongoDriver

  val connectionOptions = MongoConnectionOptions(
    0,
    None,
    false,
    false,
    CrAuthentication,
    false,
    false,
    10,
    WriteConcern.Default,
    ReadPreference.primary
  )

   val connection = driver.connection(List("mongodb:27017"), options =  connectionOptions)

   val maxBulkSize = 1000

   val maxBsonSize = 6000000
}
