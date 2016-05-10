package org.radioactiveMongoTemplate.crud.util


import cucumber.api.scala.{EN, ScalaDsl}



trait MongoTemplateUtilsTest extends ScalaDsl with AcceptanceTestConfiguration{

  val personDao = new PersonDaoImpl()

}
