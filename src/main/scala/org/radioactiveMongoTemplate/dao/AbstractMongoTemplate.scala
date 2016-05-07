package org.radioactiveMongoTemplate.dao

import reactivemongo.api.QueryOpts
import reactivemongo.api.collections.bson.BSONCollection
import reactivemongo.api.indexes.Index
import reactivemongo.bson._
import reactivemongo.api.commands.{ GetLastError, WriteResult }

import scala.concurrent.{ExecutionContext, Future}

abstract class AbstractMongoTemplate[E,K]
( val dbName:String, val collectionName:String)
(implicit entityReader: BSONDocumentReader[E], entityWriter: BSONDocumentWriter[E],
 idWriter: BSONWriter[K, _ <: BSONValue],
 idReader: BSONReader[_ <: BSONValue, K],
  ec: ExecutionContext) extends RadioactiveMongoTemplate[E,K] {

  val db = MongoContext.connection(dbName)

  val collection = db[BSONCollection](collectionName)

  def find(query:BSONDocument = BSONDocument.empty, projections:BSONDocument = BSONDocument.empty, sort: BSONDocument
  = BSONDocument("_id" -> 1), page: Int, pageSize: Int)(implicit ec: ExecutionContext):Future[List[E]] = {
    val from = (page - 1) * pageSize
    collection
      .find(query)
      .sort(sort)
      .options(QueryOpts(skipN = from, batchSizeN = pageSize))
      .cursor[E](MongoContext.connectionOptions.readPreference)
      .collect[List](pageSize)
  }

  def findOne(query: BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext): Future[Option[E]] = {
    collection.find(query).one[E]
  }

  def findById(id:K, projections:BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext):Future[Option[E]] = {
    collection.find(BSONDocument("_id" -> id), projections).one[E]
  }

  def findByIds(ids: K*)(implicit ec: ExecutionContext): Future[List[E]] = {
   collection.find(BSONDocument("$in" -> ids)).cursor[E](MongoContext.connectionOptions.readPreference).collect[List]()
  }

  def findAndUpdate(query: BSONDocument, update: BSONDocument, sort: BSONDocument = BSONDocument.empty,
                     fetchNewObject: Boolean = false,upsert: Boolean = false)(implicit ec: ExecutionContext): Future[Option[E]] ={
    collection.findAndUpdate(query, update, fetchNewObject, upsert).map(_.result[E])
  }

  def count(query: BSONDocument = BSONDocument.empty)(implicit ec: ExecutionContext): Future[Int] = {
    collection.count(Some(query))
  }

  def findAll(query: BSONDocument = BSONDocument.empty,sort: BSONDocument = BSONDocument("_id" -> 1))(implicit ec: ExecutionContext):
  Future[List[E]] = {
    collection.find(query).sort(sort).cursor[E](MongoContext.connectionOptions.readPreference).collect[List]()
  }

  def insert(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext):Future[WriteResult] = {
    collection.insert(entity, writeConcern).map( writeResult => writeResult)
  }

  def update(query: BSONDocument,update: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern,
             upsert: Boolean = false,multi: Boolean = false)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(query, update, writeConcern, upsert, multi)
  }

  def updateById(id: K,update: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.update(BSONDocument("_id" -> id), update, writeConcern)
  }

  def retrieveIndexes()(implicit ec: ExecutionContext): Future[List[Index]] = {
    collection.indexesManager.list()
  }

  def ensureIndexes(index: Index)(implicit ec: ExecutionContext): Future[Boolean] = {
    collection.indexesManager.ensure(index)
  }

  def save(entity: E, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext):Future[WriteResult] = {

    for {
      doc <- Future(entityWriter write entity)
      id <- Future(doc.getAs[K]("_id").get)
      res <- {
        collection.update(BSONDocument("_id" -> id), entity, writeConcern, true)
      }
    } yield res

  }

  def dropCollection()(implicit ec: ExecutionContext): Future[Boolean] = {
    collection.drop(false)
  }

  def createCollection(): Unit = {
    collection.create(true)
  }

  def removeById(id: K, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext):Future[WriteResult] = {
    collection.remove(BSONDocument("_id" -> id), writeConcern = MongoContext.connectionOptions.writeConcern) map { res =>  res }
  }

  def remove(query: BSONDocument, writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern, firstMatchOnly: Boolean = false)(implicit ec: ExecutionContext):
  Future[WriteResult] = {
    collection.remove(query, writeConcern, firstMatchOnly)
  }

  def removeAll(writeConcern: GetLastError = MongoContext.connectionOptions.writeConcern)(implicit ec: ExecutionContext): Future[WriteResult] = {
    collection.remove(query = BSONDocument.empty, writeConcern = writeConcern, firstMatchOnly = false)
  }

  def bulkInsert( documents: TraversableOnce[E], bulkSize: Int = MongoContext.maxBulkSize,
                  bulkByteSize: Int = MongoContext.maxBsonSize)(implicit ec: ExecutionContext): Future[Int] = {

    collection.bulkInsert(documents.map(entityWriter.write(_)).toStream,
      true, MongoContext.connectionOptions.writeConcern, bulkSize, bulkByteSize) map(result => result.n)
  }
}
