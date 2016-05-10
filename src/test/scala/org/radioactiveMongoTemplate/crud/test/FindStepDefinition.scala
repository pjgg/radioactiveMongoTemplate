package org.radioactiveMongoTemplate.crud.test

import cucumber.api.scala.{EN}
import org.junit.Assert._
import org.radioactiveMongoTemplate.crud.util.MongoTemplateUtilsTest
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class FindStepDefinition extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest {

  Then("""^I findAll records and check that (\d+) records was retrieved with name "([^"]*)"$"""){
    (expectedAmount:Int, expectedName:String) =>

      val people = personDao.findAll()
      whenReady(people, timeout(5 seconds)){
        result => {
          assertTrue(s"retieve:${result.size} expected: $expectedAmount",result.size == expectedAmount)
          result.toStream.map(person=> person.name.equalsIgnoreCase(expectedName))
        }
      }
  }


  Then("""^I findOne and check that the retrieved person has name "([^"]*)"$"""){
    (expectedName:String) =>

      val person = personDao.findOne()
      whenReady(person, timeout(5 seconds)){
        result => {
          result.get.name should be (expectedName)
        }
      }
  }


  Then("""^I countAll people and check that the amount of people is (\d+)$"""){
    (expectedAmount:Int) =>
      val peopleAmount = personDao.count()
      whenReady(peopleAmount, timeout(5 seconds)){
        result => {
          result should be (expectedAmount)
        }
      }
  }

}
