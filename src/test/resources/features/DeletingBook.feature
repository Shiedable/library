Feature: Deleting a Book from the database

  Scenario: Attempting to delete an existing Book
    Given The database has a Book
    When Attempting to delete Book 1
    Then The database should not have that Book

  Scenario Outline: Attempting to delete a Book which does not exist
    Given The database has a Book
    When Attempting to delete Book <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
    | id |
    | 99 |
    | -1 |
