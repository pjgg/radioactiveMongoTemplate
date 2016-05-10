package org.radioactiveMongoTemplate.crud.test

import org.radioactiveMongoTemplate.crud.util.{MongoTemplateUtilsTest}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.sys.process._
import cucumber.api.Scenario

class CommonsHooks extends MongoTemplateUtilsTest{

  private var alreadySetUp = false

  After("@cleanRecords"){ scenario : Scenario =>
    personDao.dropCollection()
  }

  Before("@setupMongo"){ scenario : Scenario =>
    if(!alreadySetUp) {
      Process(Seq("/bin/sh", "-c", "chmod 777 " + dockerFilePath + "start.sh")).run
      Process(Seq("/bin/sh", "-c", "export ACCEPTANCE_TEST_HOME=" + dockerFilePath + ";. " + dockerFilePath + "start.sh")).run.exitValue()
      Thread.sleep(5000)
    }

    alreadySetUp = true
  }

}
