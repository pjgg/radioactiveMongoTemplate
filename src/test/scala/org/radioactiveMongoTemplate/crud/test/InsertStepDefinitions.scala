package org.radioactiveMongoTemplate.crud.test

import cucumber.api.{Scenario, PendingException}
import cucumber.api.scala.{EN, ScalaDsl}
import org.radioactiveMongoTemplate.crud.util.{Person, MongoTemplateUtilsTest}
import org.junit.Assert._
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import reactivemongo.bson.BSONDocument
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class InsertStepDefinitions extends Matchers with ShouldVerb with ScalaDsl with ScalaFutures with EN  {

  var mongoTemplateUtilsTest = new MongoTemplateUtilsTest

  Given("""^a test database with a person collection$"""){ () =>
    mongoTemplateUtilsTest.personDao.createCollection()
  }

  When("""^I insert a person record with name "([^"]*)"$""") { (name: String) =>
    val person = Person(name = name, age = 18)
    mongoTemplateUtilsTest.booleanResult = mongoTemplateUtilsTest.personDao.insert(person).map(result => result.hasErrors)
  }

  When("""^I insert (\d+) person record with name "([^"]*)"$"""){ (amount:Int, name:String) =>

    var people = Set[Person]()
    for(age <- 1 to amount) {
      people += Person(name = name, age = age)
    }

    mongoTemplateUtilsTest.intResult =  mongoTemplateUtilsTest.personDao.bulkInsert(people).map(amountInserted => amountInserted)
  }
  Then("""^I check that (\d+) records was stored$"""){ (expectedAmount:Int) =>
    whenReady(mongoTemplateUtilsTest.intResult, timeout(5 seconds)){
      result => assertTrue(result == expectedAmount)
    }
  }

  Then("""^I check that the record was stored$""") { () =>
    whenReady(mongoTemplateUtilsTest.booleanResult, timeout(5 seconds)){
      result => assertFalse(result)
    }
  }

  Then("""^the stored person name is "([^"]*)"$""") { (expectedName: String) =>
    var personPromise = mongoTemplateUtilsTest.personDao.findOne(BSONDocument("name" -> expectedName))
    personPromise.futureValue.get.name should equal(expectedName)

  }



}
