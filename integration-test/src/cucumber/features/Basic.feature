Feature: Basic Page features

Scenario: Basic page scenario
  When I go to the home page
  Then I am at the home page

Scenario: Instant redirect
  When I go via the instant redirect page
  Then I am at the second page

Scenario: Delayed redirect
  When I go to the delayed redirect page
  Then I am at the delayed redirect page
  When I pause for 2 seconds
  Then I am at the second page
