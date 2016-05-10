package org.radioactiveMongoTemplate.crud.util

import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.sys.process._


class MongoTemplateUtilsTest extends ScalaDsl with AcceptanceTestConfiguration{

  var personDao = new PersonDaoImpl()

  var booleanResult: Future[Boolean] = Future(true)

  var intResult: Future[Int] = Future(0)

  var people :Future[List[Person]] = Future (List[Person]())

  var person : Future[Option[Person]] = Future(None)

  After("@cleanRecords"){ scenario : Scenario =>
    personDao.dropCollection()
  }

  Before("@setupMongo"){ scenario : Scenario =>
    Process(Seq("/bin/sh", "-c", "chmod 777 " +dockerFilePath + "start.sh")).run
    Process(Seq("/bin/sh", "-c","export ACCEPTANCE_TEST_HOME=" + dockerFilePath + ";. " + dockerFilePath + "start.sh")).run.exitValue()
    Thread.sleep(5000)
  }

}
