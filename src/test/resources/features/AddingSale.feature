Feature: Adding a sale to the database

  Scenario: Sale is successfully added in the database
    Given Valid Sale information
    When adding a new Sale
    Then The database should contain that same Sale with id 1

  Scenario: Sale is not added successfully in the database
    Given Empty sale date
    When adding a new Sale
    Then We should be getting an appropriate message

  Scenario: Sale is not added successfully in the database
    Given No sale date
    When adding a new Sale
    Then We should be getting an appropriate message

  Scenario: Sale is not added successfully in the database
    Given Invalid sale date format
    When adding a new Sale
    Then We should be getting an appropriate message

  Scenario: Sale is not added successfully in the database
    Given Negative quantity
    When adding a new Sale
    Then We should be getting an appropriate message
