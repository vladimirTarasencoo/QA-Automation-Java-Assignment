Feature: Create Employee

  Scenario Outline: [Negative] create employee with [<CONDITION>] field

    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 101 | Ion       | Popescu  | 1990-01-01  | 2018-01-01 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    When user saves the new employee '<CONDITION>'
    Then the response 'FAILS'

    Examples:
      | CONDITION                |
      | WITH EMPTY ID            |
      | WITH EMPTY FIRST_NAME    |
      | WITH EMPTY LAST_NAME     |
      | WITH EMPTY DATE_OF_BIRTH |
      | WITH EMPTY START_DATE    |
      | WITH EMPTY EMPLOYEE_TYPE |
      | WITH EMPTY EMAIL         |
      | WITH EMPTY PHONES        |

  Scenario: [Negative] create employee with future date of birth

    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 101 | Ion       | Popescu  | 2021-11-11  | 2018-01-01 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    When user saves the new employee 'WITH ALL REQUIRED FIELDS'
    Then the response 'FAILS'

  Scenario: [Negative] create employee with future startDate

    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 102 | Ion       | Popescu  | 2001-11-11  | 2021-11-11 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    When user saves the new employee 'WITH ALL REQUIRED FIELDS'
    Then the response 'FAILS'

    #fixMe Bug
  Scenario: [Negative] create employee with existing id

    Given user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 102 | Ion       | Popescu  | 2001-11-11  | 2021-11-11 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    And user saves the new employee 'WITH ALL REQUIRED FIELDS'
    And the response 'IS SUCCESSFUL'

    When user wants to create an employee with the following attributes
      | id  | firstName | lastName | dateOfBirth | startDate  | employmentType | email                     |
      | 103 | Ion       | Popescu  | 2001-11-11  | 2021-11-11 | Permanent      | ion.popescu@stefanini.com |

    And with the following phone numbers
      | id  | type   | isdCode | phoneNumber | extension |
      | 102 | Mobile | +373    | 777777      |           |
      | 103 | Office | +373    | 555555      | 22        |

    And user saves the new employee 'WITH EXISTING ID'
    Then the response 'FAILS'
