package org.radioactiveMongoTemplate.crud.test


import cucumber.api.scala.EN
import org.radioactiveMongoTemplate.crud.util.MongoTemplateUtilsTest
import org.scalatest.Matchers
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.words.ShouldVerb
import reactivemongo.bson._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import com.github.limansky.mongoquery.reactive.ReactiveQueryHelper
import reactivemongo.bson.{ BSONDocument, BSONDocumentWriter}


class QueryStepDefinition extends Matchers with ShouldVerb with ScalaFutures with EN with MongoTemplateUtilsTest{

  Then("""^I make the count "([^"]*)" and check that the result is (\d+)$"""){
    (query:String, expectedAmount:Int) =>

      val result = personDao.count(toBsonDocument(query))
      whenReady(result, timeout(5 seconds)){
        result => {
          result should be (expectedAmount)
        }
      }
  }


}
