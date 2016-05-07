package org.radioactiveMongoTemplate.crud.test

import cucumber.api.scala.{EN, ScalaDsl}
import org.junit.Assert._
import org.radioactiveMongoTemplate.crud.util.MongoTemplateUtilsTest
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class FindStepDefinition extends Matchers with ShouldVerb with ScalaDsl with ScalaFutures with EN {

  var mongoTemplateUtilsTest = new MongoTemplateUtilsTest

  Then("""^I request to findAll records$"""){ () =>
    mongoTemplateUtilsTest.people =  mongoTemplateUtilsTest.personDao.findAll()
  }
  Then("""^check that (\d+) records was stored with name "([^"]*)"$"""){ (expectedAmount:Int, expectedName:String) =>
    whenReady(mongoTemplateUtilsTest.people, timeout(5 seconds)){
      result => {
        assertTrue(result.size == expectedAmount)
        result.toStream.map(person=> person.name.equalsIgnoreCase(expectedName))
      }
    }
  }

}
