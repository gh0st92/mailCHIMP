

@mailchimp

Feature: Register account
  I want to register an account


  Scenario Outline: Register
  Given I have entered Mailchimp site
    And I have typed in <email>
    And I have also typed in <username>
    And I have as well typed in <password>
    Then I press sign up and verify <status> of account

    Examples: 
      | email | username | password   | status |
      | "TrueEmail" | "TrueUsername" | "Ghost1992!" | "Check your email" |
      | "NoEmail" | "TrueUsername" | "Ghost1992!" | "Please enter a value" |
      | "TrueEmail" | "100+Username" | "Ghost1992!" | "Enter a value less than 100 characters long" |
      | "TrueEmail" | "UserAlreadyExist" | "Ghost1992!" | "Another user with this username already exists. Maybe it's your evil twin. Spooky." |