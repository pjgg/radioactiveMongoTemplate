package org.radioactiveMongoTemplate.crud.test

import cucumber.api.scala.EN
import org.radioactiveMongoTemplate.crud.util.{Person, MongoTemplateUtilsTest}
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import reactivemongo.bson.{BSONObjectID, BSONDocument}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class UpdateStepDefinition extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest{


  Then("""^I update person with Id "([^"]*)" and swap his name to "([^"]*)" upsert "([^"]*)" and multi "([^"]*)"$"""){
    (id:String, name:String, upsert:Boolean, multi:Boolean) =>
      val person = Person(_id = BSONObjectID(id),name = name, age = 18)
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
    val person = Person(_id = BSONObjectID(id),name = name, age = 18)
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


}
