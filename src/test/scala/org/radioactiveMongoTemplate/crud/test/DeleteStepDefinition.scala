package org.radioactiveMongoTemplate.crud.test

import cucumber.api.scala.EN
import org.junit.Assert._
import org.radioactiveMongoTemplate.crud.util.MongoTemplateUtilsTest
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class DeleteStepDefinition extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest {

  Then("""^I deleteAll records$"""){ () =>
    val result = personDao.removeAll()
    whenReady(result, timeout(5 seconds)){
      result => {
        result.hasErrors should be (false)
      }
    }
  }
}
