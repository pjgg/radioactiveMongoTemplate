@setupMongo
Feature: create a mongo record

  As a developer I would like to insert records into mongo

  @cleanRecords
  Scenario: successful insert record
    Given a test database with a person collection
    When I insert a person record with name "Pablo"
    Then I findAll records and check that 1 records was retrieved with name "Pablo"

  @cleanRecords
  Scenario: successful insert bulk record
    Given a test database with a person collection
    When I insert 20 person record with name "Pablo"
    Then I findAll records and check that 20 records was retrieved with name "Pablo"

  @cleanRecords
  Scenario: successful upsert not exist record
    Given a test database with a person collection
    When I upsert 1 person record with name "Pablo"
    Then I findAll records and check that 1 records was retrieved with name "Pablo"

  @cleanRecords
  Scenario: successful upsert already saved record
    Given a test database with a person collection
    When I a person record with name "Pablo" an Id "507f191e810c19729de860ea"
    When I upsert a person record with name "PabloTwo" and Id "507f191e810c19729de860ea"
    Then I findAll records and check that 1 records was retrieved with name "PabloTwo"