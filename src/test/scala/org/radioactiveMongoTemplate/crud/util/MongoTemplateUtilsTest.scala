package org.radioactiveMongoTemplate.crud.util

import cucumber.api.Scenario
import cucumber.api.scala.{EN, ScalaDsl}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.sys.process._


trait MongoTemplateUtilsTest extends ScalaDsl with AcceptanceTestConfiguration{

  val personDao = new PersonDaoImpl()

  After("@cleanRecords"){ scenario : Scenario =>
    personDao.dropCollection()
  }

  Before("@setupMongo"){ scenario : Scenario =>
    Process(Seq("/bin/sh", "-c", "chmod 777 " +dockerFilePath + "start.sh")).run
    Process(Seq("/bin/sh", "-c","export ACCEPTANCE_TEST_HOME=" + dockerFilePath + ";. " + dockerFilePath + "start.sh")).run.exitValue()
    Thread.sleep(5000)
  }

}
