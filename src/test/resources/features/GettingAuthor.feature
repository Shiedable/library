Feature: Getting Author information

  Scenario: Getting information for certain Author
    Given The database has an Author
    When Attempting to get the information for Author 1
    Then We should get all the data for that Author

  Scenario: Getting information for all Authors in the database
    Given The database has 3 Authors
    When Attempting to get the information for all Authors in the database
    Then We should get all the data for all the Authors in the database

  Scenario Outline: Getting information for an Author that does not exist
    Given The database has an Author
    When Attempting to get the information for Author <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

  Examples:
    | id |
    | 99 |
    | -1 |
