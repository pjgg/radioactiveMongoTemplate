package org.radioactiveMongoTemplate.crud.util

import reactivemongo.bson._


case class Person( _id: Option[BSONObjectID] = Option(BSONObjectID.generate),
                   name: String,
                   age: Int)

object Person {
  implicit val personHandler = Macros.handler[Person]
}