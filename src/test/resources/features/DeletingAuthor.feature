Feature: Deleting an Author from the database

  Scenario: Attempting to delete an existing Author
    Given The database has an Author
    When Attempting to delete that Author
    Then The database should be empty

  Scenario: Attempting to delete an Author which does not exist
    Given The database has an Author
    When Attempting to delete an Author that does not exist
    Then Then an error should occur with a response for Author not existing