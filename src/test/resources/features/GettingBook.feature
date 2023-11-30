Feature: Getting Book information

  Scenario: Getting information for certain Book
    Given The database has a Book record
    When Attempting to get the information for Book 1
    Then We should get all the data for that Book

  Scenario: Getting information for all Books in the database
    Given The database has 3 Books
    When Attempting to get the information for all Books in the database
    Then We should get all the data for all the Books in the database

  Scenario: Getting information for a Book that does not exist
    Given The database has no Book record
    When Attempting to get the information for Book 99
    Then We should be getting Entity ID error message

  Scenario: Getting information for a Book using negative id
    Given The database has 0 Books
    When Attempting to get the information for Book -1
    Then We should be getting Entity ID error message
