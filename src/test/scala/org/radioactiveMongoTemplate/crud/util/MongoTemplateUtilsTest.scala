package org.radioactiveMongoTemplate.crud.util


import com.github.limansky.mongoquery.core.MongoQueryMacro
import cucumber.api.scala.{EN, ScalaDsl}
import com.github.limansky.mongoquery.reactive._
import reactivemongo.bson._


trait MongoTemplateUtilsTest extends ScalaDsl with AcceptanceTestConfiguration{

  val personDao = new PersonDaoImpl()

  def toBsonDocument(query:String):BSONDocument = {
    println("ecooooo" + query.replace("$","$$"))
    val customQuery = "{name:{$$in: ['item1','item2']}}"
    /*mq_impl(customQuery)
    val a = ReactiveQueryHelper(StringContext(customQuery.toString))
    a.mq()*/
   // mq"""{name:{$$in: ['Pablo','Elena']}}"""
    mqt"{name:{$$in: ['Pablo','Elena']}}"[Person]
  }

  def writeObject(obj: Map[String,Any]): BSONDocument = BSONDocument(obj.map(writePair))

  def writePair(p: (String, Any)): (String, BSONValue) = (p._1, p._2 match     {
    case value: String  => BSONString(value)
    case value: Double  => BSONDouble(value)
    case value: Int     => BSONInteger(value)
    case value: Boolean => BSONBoolean(value)
    case value: Long    => BSONLong(value)
    case value: BigInt => BSONInteger(value.toInt)
    case other => BSONArray(other.toString)
  })
}
