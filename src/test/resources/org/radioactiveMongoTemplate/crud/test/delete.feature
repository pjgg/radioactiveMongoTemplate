@setupMongo
Feature: delete a mongo record

  As a developer I would like to delete records from mongo

  @cleanRecords
  Scenario: delete all records
    Given a test database with a person collection
    When I insert 200 person record with name "Pablo"
    Then I deleteAll records
    Then I findAll records and check that 0 records was retrieved with name "Pablo"




