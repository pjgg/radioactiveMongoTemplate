Feature: find a mongo record

  As a developer I would like to retrieve records from mongo

  @setupMongo
  @cleanRecords
  Scenario: find all records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I check that 200 records was stored
    Then I request to findAll records
    And check that 200 records was retrieved with name "Pablo"

  @setupMongo
  @cleanRecords
  Scenario: find one records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I check that 200 records was stored
    Then I request to findOne
    And check that person has name "Pablo"


  @setupMongo
  @cleanRecords
  Scenario: count records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I check that 200 records was stored
    Then I request to countAll
    And check that person collection has 200 of records

