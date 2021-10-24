@regression
Feature: Login Test

  Background: User navigates to Application URL
    Given The Application has been launched

  Scenario Outline: DOCit_MOB_001 - Login Active user with a valid Credential
    Given The Application has been launched
    And I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    When I tap on Login button
    And Wait for 5 seconds
    Then I should see the text on label "<name>" displayed

    Examples:
      | username      | password     | name |
      | standard_user | secret_sauce | AS   |

  Scenario Outline: DOCit_MOB_002 - Login with inactive user credintials
    Given The Application has been launched
    And I enter '<username>' in Username text box
    And I enter '<password>' in Password text box
    When I tap on Login button
    And Wait for 5 seconds
    Then I should see the text on label "<error_message>" displayed

    Examples:
      | username        | password | error_message                   |
      | locked_out_user | secret_sauce   | Incorrect username or password. |

  @ignore
  Scenario: I should be able to login as any type of user
    Then I log in as 'problem_user' user