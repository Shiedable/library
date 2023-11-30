Feature: Adding an author to the database

  Scenario Outline: Author is successfully added in the database
    Given Valid <firstName>, <lastName> and <birthDate>
    When adding a new Author
    Then The database should contain that same Author with id 1

    Examples:
      | id | firstName | lastName  | birthDate  |
      | 1  | Muncho    | Veliki    | 1991-12-12 |
      | 2  | Radka     | Piratkova | 2014-03-15 |
      | 3  | Glupcho   | Vselenski | 2005-05-04 |

  Scenario: Author is not added successfully in the database
    Given Empty first name
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given No first name
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given Empty last name
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given No last name
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given Empty birthdate
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given No birthdate
    When adding a new Author
    Then We should be getting an appropriate response

  Scenario: Author is not added successfully in the database
    Given Invalid birthDate format
    When adding a new Author
    Then We should be getting an appropriate response
