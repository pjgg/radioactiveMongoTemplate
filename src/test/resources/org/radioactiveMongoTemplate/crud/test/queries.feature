@setupMongo
Feature: query a mongo record

  As a developer I would like to retrieve records by mongo queries

  @cleanRecords
  Scenario Outline: find by mongoQuery
    Given a test database with a person collection
    When I insert <amountType1> of people with name <nameType1> and age of <ageType1>
    When I insert <amountType2> of people with name <nameType2> and age of <ageType2>
    When I insert <amountType3> of people with name <nameType3> and age of <ageType3>
    Then I make the count "<Query>" and check that the result is <expectedResult>

  Examples:
 | amountType1 | nameType1| ageType1| amountType2 | nameType2| ageType2| amountType3 | nameType3| ageType3|Query                                     | expectedResult |
 #| 1           | "Pablo"  | 18      | 1           | "Miguel" |   19    | 1           | "Elena"  | 20      |{'name':'Pablo'}                         | 1              |
 #| 10          | "Pablo"  | 18      | 15          | "Miguel" |   19    | 10          | "Elena"  | 20      |{'name':'Pablo'}                         | 10             |
 #| 10          | "Pablo"  | 18      | 15          | "Miguel" |   19    | 10          | "Elena"  | 20      |{'name':'Miguel'}                        | 15             |
 #| 10          | "Pablo"  | 18      | 15          | "Miguel" |   19    | 10          | "Elena"  | 20      |{'name':'Elena'}                         | 10             |
 | 10          | "Pablo"  | 18      | 15          | "Miguel" |   19    | 10          | "Elena"  | 20       |{name:{$in: ['Pablo','Elena']}}          | 20             |


