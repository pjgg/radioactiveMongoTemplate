@setupMongo
Feature: update a mongo record

  As a developer I would like to update records from mongo

   @cleanRecords
   Scenario: update a records
     Given a test database with a person collection
     When I a person record with name "Pablo" an Id "507f191e810c19729de860ea"
     Then I update person with Id "507f191e810c19729de860ea" and swap his name to "Miguel" upsert "false" and multi "false"

   @cleanRecords
   Scenario: updateById a records
     Given a test database with a person collection
     When I a person record with name "Pablo" an Id "507f191e810c19729de860ea"
     Then I updateById "507f191e810c19729de860ea" a peson and swap his name to "Miguel"

   @cleanRecords
   Scenario Outline: findAndupdate
     Given a test database with a person collection
     When I create person with age 18 and name "Pablo"
     Then I findAndUpdate name to "Miguel" and age to 19 by "<Query>" and check that <numberOfEntities> was updated

   Examples:
   | Query           | numberOfEntities |
   | {'name':'Pablo'}|  1               |
   | {'age':18}      |  1               |


   @cleanRecords
   Scenario Outline: update by query
     Given a test database with a person collection
     When I create person with age 18 and name "Pablo"
     Then I Update name to "Miguel" and age to 19 by "<Query>" and check that <numberOfEntities> was updated

   Examples:
   | Query           | numberOfEntities |
   | {'name':'Pablo'}|  1               |
   | {'age':18}      |  1               |


