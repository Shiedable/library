Feature: Getting Book information from public API

  Scenario: Book data is available
    Given Book ISBN 133TEST9018144
    When Getting Book data from API
    Then Get Expected Book and Author Data

  Scenario: Book data is not available
    Given Book ISBN TESTISBN123
    When Getting Book data from API
    Then We should be getting a response with code 404 NOT_FOUND and No book was found with isbn: TESTISBN123 error message

  Scenario: Empty isbn
    Given Empty isbn
    When Getting Book data from API
    Then We should be getting a response with code 400 BAD_REQUEST and Book isbn cannot be empty error message

