Feature: create a mongo record

  As a developer I would like to insert records into mongo

  @setupMongo
  @cleanRecords
  Scenario: successful insert record
    Given a test database with a person collection
    When I insert a person record with name "Pablo"
    Then I check that the record was stored
    And the stored person name is "Pablo"

  @setupMongo
  @cleanRecords
  Scenario: successful insert bulk record
    Given a test database with a person collection
    When I insert 20 person record with name "Pablo"
    Then I check that 20 records was stored
