Feature: Getting Author information

  Scenario: Getting information for certain Author
    Given The database has an Author record
    When Attempting to get the information for an Author
    Then We should get all the data for that Author

  Scenario: Getting information for all Authors in the database
    Given The database has 3 Authors
    When Attempting to get the information for all Authors in the database
    Then We should get all the data for all the Authors in the database

  Scenario: Getting information for an Author that does not exist
    Given The database has no Author record
    When Attempting to get the information for Author 99
    Then We should be getting Entity ID error response

  Scenario: Getting information for an Author using negative id
    Given The database has 0 Authors
    When Attempting to get the information for Author -1
    Then We should be getting Entity ID error response
