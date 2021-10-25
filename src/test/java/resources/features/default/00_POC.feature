@regression
Feature: POC

  Background: User navigates to Application URL
    Given The Application has been launched

  @ignore
  Scenario: DOCit_MOB_006 - Loading residents to the home screen with selected workers type.
    Given I log in as standard user
    When I tap on "Tap to Select Shift Assignments" button
    And I tap on "PSW" button
    And I tap on "Day" button
    And I tap on "Cherry Orchard" button
    And I tap on "PSW B-Day" button
    And I tap on "Close" button
    Then I should see the text in record "Cook, Jose (A4303)" displayed
    And I should see the text in record "Reed, Cynthia (A0028)" displayed
    When I tap on "Cherry Orchard - PSW B-Day" button
    And I tap on "PSW B-Day" button
    And I tap on "Close" button