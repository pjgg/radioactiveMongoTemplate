@setupMongo
Feature: update a mongo record

  As a developer I would like to update records from mongo

  @cleanRecords
  Scenario: update a records
    Given a test database with a person collection
    When I a person record with name "Pablo" an Id "507f191e810c19729de860ea"
    Then I update person with Id "507f191e810c19729de860ea" and swap his name to "Miguel" upsert "false" and multi "false"




