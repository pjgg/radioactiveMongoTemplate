package org.radioactiveMongoTemplate.crud.util

import org.radioactiveMongoTemplate.dao.AbstractMongoTemplate
import reactivemongo.bson.BSONObjectID


class PersonDaoImpl extends AbstractMongoTemplate[Person,BSONObjectID]("test","person") {


}
