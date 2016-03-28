package org.radioactiveMongoTemplate.dao

import reactivemongo.api.commands.{WriteResult, GetLastError}
import reactivemongo.api.indexes.Index
import reactivemongo.bson.BSONDocument

import scala.concurrent.Future

trait RadioactiveMongoTemplate[E,K]  {

  def find(query:BSONDocument = BSONDocument.empty, projections:BSONDocument = BSONDocument.empty, sort: BSONDocument
  = BSONDocument("_id" -> 1), page: Int, pageSize: Int):Future[List[E]]

  def findOne(query: BSONDocument = BSONDocument.empty): Future[Option[E]]

  def findById(id:K, projections:BSONDocument = BSONDocument.empty):Future[Option[E]]

  def findByIds(ids: K*): Future[List[E]]

  def findAndUpdate(query: BSONDocument, update: BSONDocument, sort: BSONDocument = BSONDocument.empty,
                    fetchNewObject: Boolean = false,upsert: Boolean = false): Future[Option[E]]

  def count(query: BSONDocument = BSONDocument.empty): Future[Int]

  def findAll(query: BSONDocument = BSONDocument.empty,sort: BSONDocument = BSONDocument("_id" -> 1)): Future[List[E]]

  def insert(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern):Future[WriteResult]

  /*def update(query: BSONDocument,update: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern,
             upsert: Boolean = false,multi: Boolean = false): Future[WriteResult] */

  def updateById(id: K,update: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern): Future[WriteResult]

  def retrieveIndexes(): Future[List[Index]]

  def ensureIndexes(index: Index): Future[Boolean]

  def save(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern):Future[WriteResult]

  def dropCollection(): Future[Boolean]

  def createCollection(): Unit

  def removeById(id: K, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern):Future[WriteResult]

  def remove(query: BSONDocument, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern, firstMatchOnly: Boolean = false):
  Future[WriteResult]

  def removeAll(writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern): Future[WriteResult]

  def bulkInsert( documents: TraversableOnce[E], bulkSize: Int = MongoContext.maxBulkSize,
                  bulkByteSize: Int = MongoContext.maxBsonSize): Future[Int]
}
