@setupMongo
Feature: delete a mongo record

  As a developer I would like to delete records from mongo

  @cleanRecords
  Scenario: delete all records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I deleteAll records
    Then I findAll records and check that 0 records was retrieved with name "Pablo"

  @cleanRecords
  Scenario: deleteByID records
    Given a test database with a person collection
    When I a person record with name "Pablo" an Id "507f191e810c19729de860ea"
    Then I deleteById "507f191e810c19729de860ea"
    Then I countAll people and check that the amount of people is 0


