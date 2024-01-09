Feature: Getting Book information

  Scenario: Getting information for certain Book
    Given The database has a Book
    When Attempting to get the information for Book 1
    Then We should get all the data for that Book

  Scenario: Getting information for all Books in the database
    Given The database has 3 Books
    When Attempting to get the information for all Books in the database
    Then We should get all the data for all the Books in the database

  Scenario Outline: Getting information for a Book that does not exist
    Given The database has a Book
    When Attempting to get the information for Book <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
      | id |
      | 99 |
      | -1 |
