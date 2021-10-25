@regression
Feature: Login Test

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: Login Active user with a valid Credential
    And I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    When I tap on Login button
    And Wait for 5 seconds
    Then I should see the text on label "<name>" displayed

    Examples:
      | username      | password     | name                |
      | standard_user | secret_sauce | test-Cart drop zone |

  Scenario Outline: Login with invalid user Credential
    And I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    When I tap on Login button
    And Wait for 5 seconds
    Then System should display "<error_message>" Error Message

    Examples:
      | username        | password     | error_message                         |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out. |

  @ignore
  Scenario: I should be able to login as any type of user
    Then I log in as 'problem_user' user