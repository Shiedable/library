Feature: Deleting an Author from the database

  Scenario: Attempting to delete an existing Author
    Given The database has an Author
    When Attempting to delete Author 1
    Then The database should not have that Author

  Scenario: Attempting to delete an Author which does not exist
    Given The database has no Author
    When Attempting to delete Author 99
    Then Then an error should occur with an error response

  Scenario: Attempting to delete an Author using negative Id
    Given Negative id
    When Attempting to delete Author -1
    Then Then an error should occur with an error response