Feature: Updating the information saved for a certain Sale

  Scenario: Updating the sale date of a Sale
    Given Sale Date to be updated to 2022-02-02
    When Attempting to update the information for Sale 1
    Then The Sale should have been updated

  Scenario: Updating the quantity of a Sale
    Given Quantity to be updated to 100
    When Attempting to update the information for Sale 1
    Then The Sale should have been updated

  Scenario Outline: Updating the with invalid data
    Given Invalid Sale Date <saleDate>, Quantity <quantity>
    When Attempting to update the information for Sale 1
    Then We should be getting a response with code <errorCode> and <message> error message

    Examples:
      | saleDate   | quantity | errorCode       | message                                                                                        |
      | 02-02-2000 | 10       | 400 BAD_REQUEST | Could not parse Sale date: 02-02-2000 reason: Text '02-02-2000' could not be parsed at index 0 |
      | 2000-01-01 | -10      | 400 BAD_REQUEST | Sale quantity cannot be negative                                                               |

  Scenario Outline: Updating a Sale that does not exist
    Given The database has a Sale
    When Attempting to update the information for Sale <id>
    Then We should be getting a response with code 404 NOT_FOUND and Entity with such ID does not exist error message

    Examples:
      | id |
      | 99 |
      | -1 |
