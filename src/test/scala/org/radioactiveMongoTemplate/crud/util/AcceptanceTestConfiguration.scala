package org.radioactiveMongoTemplate.crud.util

import java.io.File
import java.util.ResourceBundle

import org.apache.commons.configuration.PropertiesConfiguration


trait AcceptanceTestConfiguration  {

  val classLoader = this.getClass.getClassLoader
  val conf = new PropertiesConfiguration(classLoader.getResource("common-acceptance.properties").getPath)

  def dockerFilePath =  conf.getString("dockerFilePath")

}
