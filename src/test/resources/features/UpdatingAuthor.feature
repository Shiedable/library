Feature: Updating the information saved for a certain Author

  Scenario: Updating the first name of an Author
    Given First Name to be updated to Chocho
    When Attempting to update the information for Author 1
    Then The Author should have been updated

  Scenario: Updating the last name of an Author
    Given Last Name to be updated to Chochkovich
    When Attempting to update the information for Author 1
    Then The Author should have been updated

  Scenario: Updating the birth date of an Author
    Given Birth Date to be updated to 2022 02 02
    When Attempting to update the information for Author 1
    Then The Author should have been updated

  Scenario Outline: Updating an author that does not exist
    Given The database has an Author
    When Attempting to update the information for Author <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
    | id |
    | 99 |
    | -1 |

  Scenario: Author is not updated successfully in the database
    Given Invalid birthdate format
    When Attempting to update the information for Author 1
    Then We should be getting a response with code 400 BAD_REQUEST and Could not parse Birth date: 20-12-2000 reason: Text '20-12-2000' could not be parsed at index 0 error message