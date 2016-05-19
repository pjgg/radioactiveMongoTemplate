package org.radioactiveMongoTemplate.crud.util


import cucumber.api.scala.{EN, ScalaDsl}
import reactivemongo.bson._


trait MongoTemplateUtilsTest extends ScalaDsl with AcceptanceTestConfiguration{

  val personDao = new PersonDaoImpl()

  def writeObject(obj: Map[String,Any]): BSONDocument = BSONDocument(obj.map(writePair))

  def writePair(p: (String, Any)): (String, BSONValue) = (p._1, p._2 match     {
    case value: String  => BSONString(value)
    case value: Double  => BSONDouble(value)
    case value: Int     => BSONInteger(value)
    case value: Boolean => BSONBoolean(value)
    case value: Long    => BSONLong(value)
    case other => BSONNull
  })
}
