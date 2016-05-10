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

  Then("""^I request to findOne$"""){ () =>
    mongoTemplateUtilsTest.person = mongoTemplateUtilsTest.personDao.findOne()
  }

  Then("""^check that (\d+) records was retrieved with name "([^"]*)"$"""){ (expectedAmount:Int, expectedName:String) =>
    whenReady(mongoTemplateUtilsTest.people, timeout(5 seconds)){
      result => {
        assertTrue(result.size == expectedAmount)
        result.toStream.map(person=> person.name.equalsIgnoreCase(expectedName))
      }
    }
  }

  Then("""^check that person has name "([^"]*)"$"""){ (expectedName:String) =>
    whenReady(mongoTemplateUtilsTest.person, timeout(5 seconds)){
      result => {
        result.get.name should be (expectedName)
      }
    }
  }

  Then("""^I request to countAll$"""){ () =>
    mongoTemplateUtilsTest.intResult = mongoTemplateUtilsTest.personDao.count()
  }

  Then("""^check that person collection has (\d+) of records$"""){ (expectedAmount:Int) =>
    whenReady(mongoTemplateUtilsTest.intResult, timeout(5 seconds)){
      result => assertTrue(result == expectedAmount)
    }
  }

}
