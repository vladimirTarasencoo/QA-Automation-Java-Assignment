Feature: Create Employee

  Scenario: WITH ALL REQUIRED FIELDS IS SUCCESSFUL

    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 100 | Ion       | Popescu  | 1990-01-01  | 2018-01-01 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    When user saves the new employee 'WITH ALL REQUIRED FIELDS'
    Then the save 'IS SUCCESSFUL'