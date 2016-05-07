package org.radioactiveMongoTemplate.crud.util

import org.radioactiveMongoTemplate.dao.AbstractMongoTemplate
import reactivemongo.bson.BSONObjectID
import scala.concurrent.ExecutionContext.Implicits.global


class PersonDaoImpl extends AbstractMongoTemplate[Person,BSONObjectID]("test","person") {


}
