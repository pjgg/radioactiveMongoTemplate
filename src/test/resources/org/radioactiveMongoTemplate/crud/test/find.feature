Feature: find a mongo record

  As a developer I would like to retrieve records from mongo

  @setupMongo
  @cleanRecords
  Scenario: find all records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I findAll records and check that 200 records was retrieved with name "Pablo"

  @setupMongo
  @cleanRecords
  Scenario: find one records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I findOne and check that the retrieved person has name "Pablo"

  @setupMongo
  @cleanRecords
  Scenario: count records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I countAll people and check that the amount of people is 200

