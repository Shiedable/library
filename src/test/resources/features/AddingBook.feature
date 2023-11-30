Feature: Adding a book to the database

  Scenario: Book is successfully added in the database
    Given Valid book information
    When adding a new Book
    Then The database should contain that same Book with id 1

  Scenario: Book is not added successfully in the database
    Given Empty title
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given No title
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given Empty ISBN
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given No ISBN
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given Empty publication date
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given No publication date
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given Invalid publication date format to be updated
    When adding a new Book
    Then We should be getting an appropriate error

  Scenario: Book is not added successfully in the database
    Given Negative book price
    When adding a new Book
    Then We should be getting an appropriate error