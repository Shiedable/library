Feature: Deleting an Author from the database

  Scenario: Attempting to delete an existing Author
    Given The database has an Author
    When Attempting to delete Author 1
    Then The database should not have that Author

  Scenario Outline: Attempting to delete an Author which does not exist
    Given The database has an Author
    When Attempting to delete Author <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
    | id |
    | 99 |
    | -1 |