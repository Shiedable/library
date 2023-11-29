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

  Scenario Outline: Author is not added successfully in the database
    Given Invalid <firstName> and <lastName>
    When adding a new Author
    Then We should be getting an appropriate response

    Examples:
      | firstName | lastName |
      | empty     | Veliki   |
      | null      | Veliki   |
      | Radka     | empty    |
      | Radka     | null     |

  Scenario Outline: Author is not added successfully in the database
    Given Invalid birthdate - <birthDate>
    When adding a new Author
    Then We should be getting an appropriate response

    Examples:
      | birthDate |
      | empty     |
      | null      |

  Scenario: Author is not added successfully in the database
    Given Invalid birthDate format
    When adding a new Author
    Then We should be getting an appropriate response
