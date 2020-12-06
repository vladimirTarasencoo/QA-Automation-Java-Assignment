Feature: Get Employees

  Background:
    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 100 | Ion       | Popescu  | 1990-01-01  | 2018-01-01 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    And user saves the new employee 'WITH ALL REQUIRED FIELDS'
    And the response 'IS SUCCESSFUL'

  Scenario: Get all existing employees

    When get all existing employees
    And the response 'IS SUCCESSFUL'
    Then validate received employee