package org.radioactiveMongoTemplate.crud.util

import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


class MongoTemplateUtilsTest extends ScalaDsl {

  var personDao = new PersonDaoImpl()

  var booleanResult: Future[Boolean] = Future(true)

  var intResult: Future[Int] = Future(0)

  var people :Future[List[Person]] = Future { List[Person]()}

  After("@cleanRecords"){ scenario : Scenario =>
    personDao.dropCollection()
  }
}
