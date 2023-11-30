Feature: Updating the information saved for a certain Sale

  Scenario: Updating the sale date of a Sale
    Given Sale Date to be updated to 2022-02-02
    When Attempting to update the information for Sale 1
    Then The Sale should have been updated

  Scenario: Updating the sale date of a Sale
    Given Invalid sale date format to be updated
    When Attempting to update the information for Sale 1
    Then We should get an error message

  Scenario: Updating the quantity of a Sale
    Given Quantity to be updated to 100
    When Attempting to update the information for Sale 1
    Then The Sale should have been updated

  Scenario: Updating the quantity of a Sale
    Given Invalid quantity to be updated to -100
    When Attempting to update the information for Sale 1
    Then We should get an error message

  Scenario: Updating the book of a Sale
    Given New book to be updated
    When Attempting to update the information for Sale 1
    Then The Sale should have been updated

  Scenario: Updating a Sale that does not exist
    Given Empty Sale database
    When Attempting to update the information for Sale 99
    Then The Sale should have been updated

  Scenario: Updating information for a Sale using negative id
    Given The Sale database is empty
    When Attempting to update the information for Sale -1
    Then The Sale should have been updated
