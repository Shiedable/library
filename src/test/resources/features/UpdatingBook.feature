Feature: Updating the information saved for a certain Book

  Scenario: Updating the title of a Book
    Given Book title to be updated to Java Book
    When Attempting to update the information for Book 1
    Then The Book should have been updated

  Scenario: Updating the ISBN of a Book
    Given ISBN to be updated to DASDAS
    When Attempting to update the information for Book 1
    Then The Book should have been updated

  Scenario: Updating the publication date of a Book
    Given Publication Date to be updated to 2022 02 02
    When Attempting to update the information for Book 1
    Then The Book should have been updated

  Scenario: Updating the price of a Book
    Given Price to be updated to 100.60
    When Attempting to update the information for Book 1
    Then The Book should have been updated

  Scenario Outline: Updating a book that does not exist
    Given The database has a Book
    When Attempting to update the information for Book <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
      | id |
      | 99 |
      | -1 |

  Scenario: Book is not updated successfully in the database
    Given Invalid publication date format
    When Attempting to update the information for Book 1
    Then We should be getting a response with code 400 BAD_REQUEST and Could not parse Publication date: 02-02-2000 reason: Text '02-02-2000' could not be parsed at index 0 error message

  Scenario: Book is not updated successfully in the database
    Given Negative price
    When Attempting to update the information for Book 1
    Then We should be getting a response with code 400 BAD_REQUEST and Book price cannot be negative error message