# RadioactiveMongoTemplate
Status: [![Build Status](https://travis-ci.org/pjgg/radioactiveMongoTemplate.svg?branch=master)](https://travis-ci.org/pjgg/radioactiveMongoTemplate)
[![Coverage Status](https://coveralls.io/repos/github/pjgg/radioactiveMongoTemplate/badge.svg?branch=master)](https://coveralls.io/github/pjgg/radioactiveMongoTemplate?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.pjgg/radioactiveMongoTemplate/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.pjgg/radioactiveMongoTemplate/)

How to use
----------

1. Setup your application.conf (or use this one as a default)

```Text

radioactiveMongoTemplate {
  connectTimeoutMS = 0
  authSource = None
  sslEnabled = false
  sslAllowsInvalidCert = false
  authMode = CrAuthentication
  tcpNoDelay = false
  keepAlive = false
  nbChannelsPerNode = 10
  writeConcern = Default
  readPreference = primary

  maxBulkSize = 1000
  maxBsonSize = 6000000
  hosts = ["localhost:27017"]
}

```

2. Create your domain objects (MongoDocuments)

```Scala
import reactivemongo.bson._

case class Person( _id: Option[BSONObjectID] = Option(BSONObjectID.generate),
                   name: String,
                   age: Int)

object Person {
  implicit val personHandler = Macros.handler[Person]
}
```

3. Write down your repository layer

```Scala
import org.radioactiveMongoTemplate.dao.{AbstractMongoTemplate}
import reactivemongo.bson.BSONObjectID
import scala.concurrent.ExecutionContext.Implicits.global


class PersonDaoImpl extends AbstractMongoTemplate[Person,BSONObjectID]("yourDbName","yourCollectionName") {

}
```

Installation
------------
###### Maven

```
  <dependency>
     <groupId>com.github.pjgg</groupId>
     <artifactId>radioactiveMongoTemplate</artifactId>
     <version>1.0.1</version>
   </dependency>    
```

###### Sbt
```
   libraryDependencies += "com.github.pjgg" % "radioactiveMongoTemplate" % "1.0.1"
```