Feature: Adding a sale to the database

  Scenario: Sale is successfully added in the database
    Given Sale Date 2000-01-01, Quantity 10
    When adding a new Sale
    Then The database should contain that same Sale with id 1

  Scenario Outline: Sale is not added successfully in the database
    Given Sale Date <saleDate>, Quantity <quantity>
    When adding a new Sale
    Then We should be getting a response with code <errorCode> and <message> error message

    Examples:
      | saleDate   | quantity | errorCode       | message                                                                                         |
      |            | 10       | 400 BAD_REQUEST | Sale date cannot be empty                                                                       |
      | 2000-01-01 | -10      | 400 BAD_REQUEST | Sale quantity cannot be negative                                                                |
      | 02-02-2000 | 10       | 400 BAD_REQUEST | Could not parse Sale date: 02-02-2000 reason: Text '02-02-2000' could not be parsed at index 0 |

  Scenario: Sale is not added successfully in the database
    Given Null sale date
    When adding a new Sale
    Then We should be getting a response with code 400 BAD_REQUEST and saleDate cannot be null/empty error message

