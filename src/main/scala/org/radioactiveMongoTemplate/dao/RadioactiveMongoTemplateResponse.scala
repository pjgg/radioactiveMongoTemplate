package org.radioactiveMongoTemplate.dao

import reactivemongo.api.commands.{WriteResult, GetLastError}
import reactivemongo.api.indexes.Index
import reactivemongo.bson.BSONDocument

import scala.concurrent.{ExecutionContext, Future}

sealed trait TemplateResponse {
  def code:Option[Int]
  def hasErrors:Boolean
  def msg:Option[String]
}

case class SimpleRespone[K](val code:Option[Int], val hasErrors:Boolean, val msg:Option[String], id:Option[K]) extends TemplateResponse

case class UpsertSimpleRespone[K](val code:Option[Int], val hasErrors:Boolean, val msg:Option[String], upsertIds:List[K]) extends TemplateResponse
