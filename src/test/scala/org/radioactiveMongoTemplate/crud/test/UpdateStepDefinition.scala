package org.radioactiveMongoTemplate.crud.test


import reactivemongo.bson._


import cucumber.api.scala.EN
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.radioactiveMongoTemplate.crud.util.{Person, MongoTemplateUtilsTest}
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import scala.concurrent.duration._

import scala.concurrent.ExecutionContext.Implicits.global

class UpdateStepDefinition extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest{

  Then("""^I update person with Id "([^"]*)" and swap his name to "([^"]*)" upsert "([^"]*)" and multi "([^"]*)"$"""){
    (id:String, name:String, upsert:Boolean, multi:Boolean) =>
      val person = Person(_id = Option(BSONObjectID(id)),name = name, age = 18)
      val result = personDao.update(BSONDocument("_id" -> BSONObjectID(id)),person,upsert,multi)
      whenReady(result, timeout(5 seconds)){
        result =>
          result.hasErrors should be (false)
          personDao.findById(BSONObjectID(id)) onSuccess {
            case Some(p:Person)=> p.name should be (name)
          }
      }
  }

  Then("""^I updateById "([^"]*)" a peson and swap his name to "([^"]*)"$"""){ (id:String, name:String) =>
    val person = Person(_id = Option(BSONObjectID(id)),name = name, age = 18)
    val result = personDao.updateById(BSONObjectID(id),person)
    whenReady(result, timeout(5 seconds)){
      result =>
        result.hasErrors should be (false)
        result.id.get should be (BSONObjectID(id))
        personDao.findById(result.id.get) onSuccess {
          case Some(p:Person)=> p.name should be (name)
        }
    }
  }


  Then("""^I findAndUpdate name to "([^"]*)" and age to (\d+) by "([^"]*)" and check that (\d+) was updated$"""){
    (updatedName: String, updatedAge: Int, query: String, expectedNumberOfEntities: Int) =>

      val person = Person(_id = None, name = updatedName, age = updatedAge)
      implicit val formats = org.json4s.DefaultFormats
      val map = parse(query.replace("'","\"")).extract[Map[String, Any]]
      val bsonQuery:BSONDocument = writeObject(map)

      val result = personDao.findAndUpdate(bsonQuery, person)
      whenReady(result, timeout(5 seconds)) {
        result =>
          result.size should be (expectedNumberOfEntities)
          result.forall(p => p.name.equalsIgnoreCase(updatedName)) should be (true)
      }
  }


  Then("""^I Update name to "([^"]*)" and age to (\d+) by "([^"]*)" and check that (\d+) was updated$"""){
    (updatedName: String, updatedAge: Int, query: String, expectedNumberOfEntities: Int) =>

      val person = Person(_id = None, name = updatedName, age = updatedAge)
      implicit val formats = org.json4s.DefaultFormats
      val map = parse(query.replace("'","\"")).extract[Map[String, Any]]
      val bsonQuery:BSONDocument = writeObject(map)

      val result = personDao.update(bsonQuery, person, true)
      whenReady(result, timeout(5 seconds)) {
        result =>
          result.hasErrors should be (false)
          val person = personDao.findOne( writeObject(parse(s"""{"name":"$updatedName"}""").extract[Map[String, Any]]))
          whenReady(person, timeout(5 seconds)){
            updatedPerson => {
              updatedPerson.get.name should be (updatedName)
            }
          }
      }

  }
}
