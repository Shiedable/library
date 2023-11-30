Feature: Deleting a Book from the database

  Scenario: Attempting to delete an existing Book
    Given The database has a Book
    When Attempting to delete Book 1
    Then The database should not have that Book

  Scenario: Attempting to delete a Book which does not exist
    Given The database has no Books
    When Attempting to delete Book 99
    Then Then an error should occur with response

  Scenario: Attempting to delete a Book using negative Id
    Given Negative id for a Book
    When Attempting to delete Book -1
    Then Then an error should occur with response