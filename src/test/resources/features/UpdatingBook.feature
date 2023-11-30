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

  Scenario: Updating a book that does not exist
    Given Empty Book database
    When Attempting to update the information for Book 99
    Then We should be getting an error

  Scenario: Updating information for a Book using negative id
    Given The Book database is empty
    When Attempting to update the information for Book -1
    Then We should be getting an error

  Scenario: Book is not updated successfully in the database
    Given Invalid publication date format
    When Attempting to update the information for Book 1
    Then We should be getting an error

  Scenario: Book is not updated successfully in the database
    Given Negative price
    When Attempting to update the information for Book 1
    Then We should be getting an error