Feature: Getting Author information

  Scenario: Getting information for certain Author
    Given The database has 1 Author
    When Attempting to get the information for that Author
    Then We should get all the data for that Author

  Scenario: Getting information for all Authors in the database
    Given The database has 3 Authors
    When Attempting to get the information for all Authors in the database
    Then We should get all the data for all the Authors in the database

    Scenario: Getting information for an Author that does not exist
      Given Having no authors saved in the database
      When Attempting to get the information for an Author
      Then An error should occur
