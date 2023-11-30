Feature: Getting Sale information

  Scenario: Getting information for certain Sale
    Given The database has a Sale record
    When Attempting to get the information for Sale 1
    Then We should get all the data for that Sale

  Scenario: Getting information for all Sales in the database
    Given The database has 3 Sales
    When Attempting to get the information for all Sales in the database
    Then We should get all the data for all the Sales in the database

  Scenario: Getting information for a Sale that does not exist
    Given The database has no Sale record
    When Attempting to get the information for Sale 99
    Then We should be getting Entity ID error

  Scenario: Getting information for an Sale using negative id
    Given The database has 0 Sales
    When Attempting to get the information for Sale -1
    Then We should be getting Entity ID error
