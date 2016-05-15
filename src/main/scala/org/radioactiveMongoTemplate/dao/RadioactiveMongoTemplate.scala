package org.radioactiveMongoTemplate.dao

import reactivemongo.api.commands.{WriteResult, GetLastError}
import reactivemongo.api.indexes.Index
import reactivemongo.bson.BSONDocument

import scala.concurrent.{ExecutionContext, Future}

trait RadioactiveMongoTemplate[E,K]  {

  def find(query:BSONDocument = BSONDocument.empty, projections:BSONDocument = BSONDocument.empty, sort: BSONDocument
  = BSONDocument("_id" -> 1), page: Int, pageSize: Int)(implicit ec: ExecutionContext):Future[List[E]]

  def findOne(query: BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext): Future[Option[E]]

  def findById(id:K, projections:BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext):Future[Option[E]]

  def findByIds(ids: K*)(implicit ec: ExecutionContext): Future[List[E]]

  def findAndUpdate(query: BSONDocument, update: BSONDocument, sort: BSONDocument = BSONDocument.empty,
                    fetchNewObject: Boolean = false,upsert: Boolean = false)(implicit ec: ExecutionContext): Future[Option[E]]

  def count(query: BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext): Future[Int]

  def findAll(query: BSONDocument = BSONDocument.empty,sort: BSONDocument = BSONDocument("_id" -> 1))(implicit ec: ExecutionContext): Future[List[E]]

  def insert(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext):Future[WriteResult]

  def update(query: BSONDocument,update: E,
             upsert: Boolean = false,multi: Boolean = false, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext): Future[WriteResult]

  def updateById(id: K,update: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext): Future[SimpleRespone[K]]
  
  def retrieveIndexes()(implicit ec: ExecutionContext): Future[List[Index]]

  def ensureIndexes(index: Index)(implicit ec: ExecutionContext): Future[Boolean]

  def save(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec:ExecutionContext):Future[SimpleRespone[K]]

  def dropCollection()(implicit ec: ExecutionContext): Future[Boolean]

  def createCollection(): Unit

  def removeById(id: K, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext):Future[WriteResult]

  def remove(query: BSONDocument, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern, firstMatchOnly: Boolean = false)(implicit ec: ExecutionContext):
  Future[WriteResult]

  def removeAll(writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext): Future[WriteResult]

  def bulkInsert( documents: TraversableOnce[E], bulkSize: Int = MongoContext.maxBulkSize,
                  bulkByteSize: Int = MongoContext.maxBsonSize)(implicit ec: ExecutionContext): Future[Int]
}

case class SimpleRespone[K](code:Option[Int], hasErrors:Boolean, msg:Option[String], id:Option[K])
