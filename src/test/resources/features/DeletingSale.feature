Feature: Deleting a Sale from the database

  Scenario: Attempting to delete an existing Sale
    Given The database has a Sale
    When Attempting to delete Sale 1
    Then The database should not have that Sale

  Scenario: Attempting to delete a Sale which does not exist
    Given The database has no Sale
    When Attempting to delete Sale 99
    Then Then an error should occur with an error message

  Scenario: Attempting to delete a Sale using negative Id
    Given Negative Sale id
    When Attempting to delete Sale -1
    Then Then an error should occur with an error message