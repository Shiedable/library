Feature: Adding an author to the database

  Scenario Outline: Author is successfully added with valid fields
    Given Having no authors saved in the database
    When adding a new Author with valid <firstName>, <lastName> and <birthDate>
    Then The database should contain that same Author

    Examples:
    |firstName|lastName |birthDate |
    |Muncho   |Veliki   |25.12.1991|
    |Radka    |Piratkova|14.03.1984|
    |Glupcho  |Vselenski|03.05.2000|