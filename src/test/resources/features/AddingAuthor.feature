Feature: Adding an author to the database

  Scenario Outline: Author is successfully added in the database
    Given First Name <firstName> Last Name <lastName> and Birth Date <birthDate>
    When adding a new Author
    Then The database should contain that same Author with id <id>

    Examples:
      | id | firstName | lastName  | birthDate  |
      | 1  | Muncho    | Veliki    | 1991-12-12 |
      | 1  | Radka     | Piratkova | 2014-03-15 |
      | 1  | Glupcho   | Vselenski | 2005-05-04 |

  Scenario Outline: Author is not successfully added in the database
    Given First Name <firstName> Last Name <lastName> and Birth Date <birthDate>
    When adding a new Author
    Then We should be getting a response with code <errorCode> and <message> error message

    Examples:
      | firstName | lastName | birthDate  | errorCode       | message                                                                                        |
      |           | testov   | 2000-01-01 | 400 BAD_REQUEST | Author first name cannot be empty                                                              |
      | test      |          | 2000-01-01 | 400 BAD_REQUEST | Author last name cannot be empty                                                               |
      | test      | testov   |            | 400 BAD_REQUEST | Author birth date cannot be empty                                                              |
      | test      | testov   | 02-02-2000 | 400 BAD_REQUEST | Could not parse Birth date: 02-02-2000 reason: Text '02-02-2000' could not be parsed at index 0 |

  Scenario: Author is not added successfully in the database
    Given Null First Name
    When adding a new Author
    Then We should be getting a response with code 400 BAD_REQUEST and firstName cannot be null/empty error message

  Scenario: Author is not added successfully in the database
    Given Null Last Name
    When adding a new Author
    Then We should be getting a response with code 400 BAD_REQUEST and lastName cannot be null/empty error message

  Scenario: Author is not added successfully in the database
    Given Null Birth Date
    When adding a new Author
    Then We should be getting a response with code 400 BAD_REQUEST and birthDate cannot be null/empty error message