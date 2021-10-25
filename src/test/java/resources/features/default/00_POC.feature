@regression
Feature: POC

  Background: User navigates to Application URL
    Given The Application has been launched

  #@ignore
  Scenario: POC Test #1
    Given I log in as standard user

  Scenario: POC Test #2
    Given I log in as locked out user

  Scenario: POC Test #3
    Given I log in as problem user