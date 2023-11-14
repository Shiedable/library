Feature: Adding an author to the database

  Scenario Outline: Author is successfully added in the database
    Given Having no authors saved in the database
    When adding a new Author with valid <firstName>, <lastName> and <birthDate>
    Then The database should contain that same Author

    Examples:
      | firstName | lastName  | birthDate  |
      | Muncho    | Veliki    | 1991-12-12 |
      | Radka     | Piratkova | 2014-03-15 |
      | Glupcho   | Vselenski | 2005-05-04 |

  Scenario: Author is not added successfully with empty first name
    Given Having no authors saved in the database
    When adding a new Author with empty first name
    Then An error should occur with response for an empty first name

  Scenario: Author is not added successfully with empty last name
    Given Having no authors saved in the database
    When adding a new Author with empty last name
    Then An error should occur with response for an empty last name

  Scenario: Author is not added successfully with empty birth date
    Given Having no authors saved in the database
    When adding a new Author with empty birth date
    Then An error should occur with response for an empty birth date