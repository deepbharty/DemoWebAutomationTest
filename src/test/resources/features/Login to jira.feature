@JiraLogin

Feature: Jira Login with OLM ID and OTP Authentication

  @Login
  Scenario: Successful login to Jira using OLM ID and OTP
    Given User is on the Jira login page
    When User Select account to login
    When User enters valid OLM ID
    And User enters valid password
    And User clicks on the Login button
    Then User should be prompted to enter OTP
    When User enters valid OTP
    And User submits the OTP
    Then User should be redirected to the Jira dashboard


