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
