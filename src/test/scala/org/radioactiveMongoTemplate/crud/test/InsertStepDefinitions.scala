package org.radioactiveMongoTemplate.crud.test

import cucumber.api.{Scenario, PendingException}
import cucumber.api.scala.{EN, ScalaDsl}
import org.radioactiveMongoTemplate.crud.util.{Person, MongoTemplateUtilsTest}
import org.junit.Assert._
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import reactivemongo.api.commands.UpdateWriteResult
import reactivemongo.bson.BSONObjectID
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class InsertStepDefinitions extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest {

  Given("""^a test database with a person collection$"""){ () =>
    personDao.createCollection()
  }

  When("""^I insert a person record with name "([^"]*)"$""") { (name: String) =>
    val person = Person(name = name, age = 18)
    val errors = personDao.insert(person).map(result => result.hasErrors)
    whenReady(errors, timeout(5 seconds)){
      errors => errors should be (false)
    }
  }

  When("""^I insert (\d+) person record with name "([^"]*)"$"""){ (amount:Int, name:String) =>

    var people = Set[Person]()
    for(age <- 1 to amount) {
      people += Person(name = name, age = age)
    }

    val amtInserted = personDao.bulkInsert(people).map(amountInserted => amountInserted)
    whenReady(amtInserted, timeout(5 seconds)){
      result => assertTrue(result == amount)
    }
  }

  When("""^I a person record with name "([^"]*)" an Id "([^"]*)"$"""){
    (name:String, id:String) =>
      val person = Person(_id = BSONObjectID(id),name = name, age = 18)
      val errors = personDao.insert(person).map(result => result.hasErrors)
      whenReady(errors, timeout(5 seconds)){
        errors => errors should be (false)
      }
  }

  When("""^I upsert (\d+) person record with name "([^"]*)"$"""){
    (amount:Int, name:String) =>
      val person = Person(name = name, age = 18)
      val result = personDao.save(person)
      whenReady(result, timeout(5 seconds)){
        result => {
          result.hasErrors should be (false)
          result.id.get should not be None
        }
      }
  }

  When("""^I upsert a person record with name "([^"]*)" and Id "([^"]*)"$"""){
    (name:String, id:String) =>
      val person = Person(_id= BSONObjectID(id), name = name, age = 18)
      val result = personDao.save(person)
      whenReady(result, timeout(5 seconds)){
        result => {
          result.hasErrors should be (false)
          result.id.get should be (BSONObjectID(id))
        }
      }
  }

}
