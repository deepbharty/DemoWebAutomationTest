@OrangeHRMLogin

Feature: OrangeHRM Admin Portal Login

  As an Admin user
  I want to login to the OrangeHRM portal
  So that I can access the admin dashboard

  Background:
    Given User is on the OrangeHRM login page

  Scenario: Successful login with valid credentials
    When User enters username
    And User enters password
    And User clicks on the Login button
    Then User should be redirected to the Dashboard page
    And Page title should be "OrangeHRM"



  Scenario: Unsuccessful login with invalid credentials
    When User enters wrong username
    And User enters wrong password
    And User clicks on the Login button
    Then An error message "Invalid credentials" should be displayed

 Scenario: Login attempt with empty username and password
  When User clicks on the Login button
  Then An error message "Username cannot be empty" should be displayed


