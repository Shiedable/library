Feature: Getting Sale information

  Scenario: Getting information for certain Sale
    Given The database has a Sale
    When Attempting to get the information for Sale 1
    Then We should get all the data for that Sale

  Scenario: Getting information for all Sales in the database
    Given The database has 3 Sales
    When Attempting to get the information for all Sales in the database
    Then We should get all the data for all the Sales in the database

  Scenario Outline: Getting information for a Sale that does not exist
    Given The database has a Sale
    When Attempting to get the information for Sale <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
      | id |
      | 99 |
      | -1 |
