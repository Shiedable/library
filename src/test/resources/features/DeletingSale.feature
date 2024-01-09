Feature: Deleting a Sale from the database

  Scenario: Attempting to delete an existing Sale
    Given The database has a Sale
    When Attempting to delete Sale 1
    Then The database should not have that Sale

  Scenario Outline: Attempting to delete a Sale which does not exist
    Given The database has a Sale
    When Attempting to delete Sale <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
      | id |
      | 99 |
      | -1 |