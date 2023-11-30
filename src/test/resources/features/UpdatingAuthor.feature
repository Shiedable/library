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

  Scenario: Updating an author that does not exist
    Given Empty Author database
    When Attempting to update the information for Author 99
    Then We should be getting an error message

  Scenario: Updating information for an Author using negative id
    Given The Author database is empty
    When Attempting to update the information for Author -1
    Then We should be getting an error message

  Scenario: Author is not updated successfully in the database
    Given Invalid birthdate format
    When Attempting to update the information for Author 1
    Then We should be getting an error message