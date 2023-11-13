Feature: Updating the information saved for a certain Author

  Scenario: Updating the first name of an Author
    Given The database has 1 Author
    When Attempting to update his first name to "Chocho"
    Then The Author should have his first name as "Chocho"

  Scenario: Updating the last name of an Author
    Given The database has 1 Author
    When Attempting to update his last name to "Chochkovich"
    Then The author should have hist last name as "Chochkovich"

  Scenario: Updating the birth date of an Author
    Given The database has 1 Author
    When Attempting to update his birth date to "01-01-2000"
    Then The author should have his birth date changed to "01-01-2000"