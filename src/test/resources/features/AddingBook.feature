Feature: Adding a book to the database

  Scenario: Book is successfully added in the database
    Given Title BookTitle, ISBN BT, publication date 2000-01-01 and price 10.50
    When adding a new Book
    Then The database should contain that same Book with id 1

  Scenario Outline: Book is not added successfully in the database
    Given Title <title>, ISBN <isbn>, publication date <publicationDate> and price <price>
    When adding a new Book
    Then We should be getting a response with code <errorCode> and <message> error message

    Examples:
      | title | isbn | publicationDate | price | errorCode       | message                                                                                         |
      |       | TST  | 2000-01-01      | 10.50 | 400 BAD_REQUEST | Book title cannot be empty                                                                      |
      | test  |      | 2000-01-01      | 10.50 | 400 BAD_REQUEST | Book ISBN cannot be empty                                                                       |
      | test  | TST  |                 | 10.50 | 400 BAD_REQUEST | Book publication date cannot be empty                                                           |
      | test  | TST  | 02-02-2000      | 10.50 | 400 BAD_REQUEST | Could not parse Publication date: 02-02-2000 reason: Text '02-02-2000' could not be parsed at index 0 |
      | test  | TST  | 2000-01-01      | -10.5 | 400 BAD_REQUEST | Book price cannot be negative                                                                   |

  Scenario: Book is not added successfully in the database
    Given Null title
    When adding a new Book
    Then We should be getting a response with code 400 BAD_REQUEST and title cannot be null/empty error message

  Scenario: Book is not added successfully in the database
    Given Null ISBN
    When adding a new Book
    Then We should be getting a response with code 400 BAD_REQUEST and isbn cannot be null/empty error message

  Scenario: Book is not added successfully in the database
    Given Null publication date
    When adding a new Book
    Then We should be getting a response with code 400 BAD_REQUEST and publicationDate cannot be null/empty error message

